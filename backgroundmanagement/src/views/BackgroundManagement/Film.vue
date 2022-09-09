<template>
  <div v-title data-title="晓天电影订票后台管理系统-电影管理">
    <div style="padding: 10px 0">
      <el-input placeholder="请输入查询内容" v-model="input" class="input-with-select" style="width: 500px" clearable>
        <el-select v-model="select" slot="prepend" placeholder="请选择" clearable>
          <el-option label="电影编号" value="id"></el-option>
          <el-option label="电影名" value="name"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click='findbyinput'></el-button>
      </el-input>
    </div>
    <div class="pd-10">
      <el-button type="primary" @click="add()">新增
        <i class="el-icon-circle-plus" style="margin-left: 5px"></i>
      </el-button>
      <el-popconfirm style="margin-left: 5px;margin-right: 5px"
                     confirm-button-text='好的'
                     cancel-button-text='我在想想'
                     icon="el-icon-info"
                     icon-color="red"
                     title="您确定删除吗？"
                     @confirm="delmore">
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove" style="margin-left: 5px"></i></el-button>
      </el-popconfirm>
    </div>
    <el-table :data="tableData" border stripe :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"  @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55"></el-table-column>
      <el-table-column prop="id" label="电影编号" width="140">
      </el-table-column>
      <el-table-column label="电影图片">
        <template slot-scope="scope">
          <el-popover placement="top-start" title="" trigger="hover">
            <el-image :src="scope.row.image" alt="" style="width: 200px;height: 200px"></el-image>
            <el-image slot="reference" :src="scope.row.image" style="width: 80px;height: 80px"></el-image>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="电影名" width="120">
      </el-table-column>
      <el-table-column prop="category" label="电影分类">
      </el-table-column>
      <el-table-column prop="time" label="电影时长（分钟）">
      </el-table-column>
      <el-table-column label="状态">
        <template slot-scope="scope">
          <span v-if="scope.row.status==1">起售</span>
          <span v-if="scope.row.status==0">停售</span>
        </template>
      </el-table-column>
      <el-table-column prop="uptime" label="上映时间">
      </el-table-column>
      <el-table-column
          label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="updatebyid(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='我在想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row)">
            <el-button class="ml-5" type="danger" size="small" slot="reference">删除<i class="el-icon-delete"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[1, 2, 5, 10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="新增或修改电影信息(修改后请单击确定保存)" :visible.sync="dialogvisible" width="50%">
      <el-form :model="form" status-icon  ref="form" :rules="rules">
        <el-form-item label="电影名" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电影时长" :label-width="formLabelWidth" prop="time">
          <el-input-number v-model="form.time" autocomplete="off" :max="250" :min="25"></el-input-number>分钟
        </el-form-item>
        <el-form-item label="电影分类" :label-width="formLabelWidth" prop="categoryId">
          <template>
            <el-select v-model="form.categoryId" placeholder="请选择">
              <el-option
                  v-for="item in cat"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id.toString()">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="电影缩略图" :label-width="formLabelWidth" prop="image">
          <el-upload
              class="avatar-uploader"
              action="#"
              :http-request="uploadFile1"
              :show-file-list="false"
              :on-success="handleAvatarSuccess">
            <img v-if="form.image" :src="form.image" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="上映时间" :label-width="formLabelWidth" prop="uptime">
          <div class="block">
            <el-date-picker
                v-model="form.uptime"
                align="right"
                type="datetime"
                placeholder="选择日期"
                value-format="yyyy-MM-ddTHH:mm:ss"
                :picker-options="pickerOptions">
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="导演" :label-width="formLabelWidth" prop="director">
          <el-input v-model="form.director" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="主演" :label-width="formLabelWidth" prop="actors">
          <el-input v-model="form.actors" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="语言" :label-width="formLabelWidth" prop="language">
          <template>
            <el-select v-model="form.language" placeholder="请选择">
              <el-option
                  v-for="item in lang"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="电影描述" :label-width="formLabelWidth" prop="description">
          <el-input type="textarea" v-model="form.description" :rows="5"></el-input>
        </el-form-item>
        <el-form-item label="销售状态" :label-width="formLabelWidth">
          <el-switch
              style="display: block"
              v-model="form.status"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="起售"
              inactive-text="下架"
              :active-value="1"
              :inactive-value="0">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogvisible = false">取 消</el-button>
        <el-button type="primary" @click="save('form')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Film",
  data() {
    return {
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }]
      },
      tableData: null,
      currentPage: 1,
      total: 0,
      pageSize: 5,
      input: "",
      select: "",
      dialogvisible: false,
      form: {
        id: '',
        name: '',
        category: '',
        categoryId: '',
        image: '',
        description: '',
        status: 1,
        updateTime:'',
        count:0,
        time:0,
        uptime:'',
        director: '',
        actors: '',
        language: ''
      },
      cat:[],
      formLabelWidth: '120px',
      checkPass:'',
      multipleSelection: [],
      rules: {
        name: [
          { required: true, message: '请输入电影名', trigger: 'blur'}
        ],
        description: [
          {required: true,  message: '请输入简介', trigger: 'blur' }
        ],
        image: [
          {required: true,  message: '请上传图片', trigger: 'blur' }
        ],
        categoryId: [
          {required: true,  message: '请选择分类', trigger: 'blur' }
        ],
        time: [
          {required: true,  message: '请填写时长', trigger: 'blur' }
        ],
        uptime: [
          {required: true,  message: '请选择上映时间', trigger: 'blur' }
        ],
        director: [
          {required: true,  message: '请填写导演', trigger: 'blur' }
        ],
        actors: [
          {required: true,  message: '请填写主演', trigger: 'blur' }
        ],
        language: [
          {required: true,  message: '请选择语言', trigger: 'blur' }
        ],
      },
      lang:[
        {
          value: '中文',
          label: '国语'
        }, {
          value: '英语',
          label: '英语'
        }, {
          value: '日语',
          label: '日语'
        }, {
          value: '韩语',
          label: '韩语'
        }, {
          value: '阿拉伯语',
          label: '阿拉伯语'
        }
      ]
    }
  },
  methods: {
    load() {
      request.get("/film", {
        params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(
          res => {
            this.tableData = res.data.data.data
            this.total = res.data.data.total
          }
      )
      request.get("/category/category0").then(
          res => {
            this.cat=res.data.data
          }
      )

    },
    uploadFile1(item){
      //上传文件的需要form-data类型;所以要转
      const file = item.file,
          fileType = file.type,
          isImage = fileType.indexOf("image") != -1,
          isLt2M = file.size / 1024 / 1024 < 2;
      // 这里常规检验，看项目需求而定
      if (!isImage) {
        this.$message.error("只能上传图片格式png、jpg、gif!");
        return;
      }
      if (!isLt2M) {
        this.$message.error("只能上传图片大小小于2M");
        return;
      }
      let FormDatas = new FormData()
      let that = this;
      FormDatas.append('file',file)

      request.post("/upload/savefilmPicture",FormDatas).then(function (res) {
        that.form.image = res.data.data
        that.$message.success("上传成功")
      }).catch(function (error){
        that.$message.error("上传失败"+error.data.msg)
      })

    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
      this.load()
    },
    add(){
      this.resetForm()
      this.dialogvisible = true
    },
    resetForm() {
      this.form={
        id: '',
        name: '',
        category: '',
        categoryId: '',
        image: '',
        price: '',
        description: '',
        status: 1,
        updateTime:'',
        count:0,
        time:0,
        uptime: '',
        director: '',
        actors: '',
        language: ''
      };
    },
    findbyinput() {
      if (this.select != "" && this.input != "") {
        request.get("/film/find", {
          params: {
            currentPage: this.currentPage,
            pageSize: this.pageSize,
            select: this.select,
            input: this.input
          }
        }).then(
            res => {
              this.tableData = res.data.data.data
              this.total = res.data.data.total
            }
        )
      } else {
        this.load()
      }
    },
    setinitial(){
      this.currentPage=1
      this.pageSize=5
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post("/film",this.form).then(res=>{
            if (res.data.code==200){
              this.$message.success("更新成功");
              this.dialogvisible = false
              this.setinitial()
              this.load()
            }else{
              this.$message.error(res.data.msg)
            }
          })
        } else {
          this.$message.warning("尚有未填的部分");
          return false;
        }
      });

    },
    updatebyid(row){
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogvisible = true
    },
    del(film){
      if (film.status==1){
        this.$message.warning("还在销售，不可删除")
      }else{
        request.delete("/film/"+film.id).then(
            res => {
              if (res.data.code==200){
                this.$message.success("删除成功");
                this.setinitial()
                this.load()
              }else{
                this.$message.error("删除失败")
              }
            }
        )
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    delmore(){
      if (this.multipleSelection.find(a=>a.status==1)){
        this.$message.warning("有电影还在上映，不可删除")
      }else{
        let ids = this.multipleSelection.map(v=>v.id)//将对象数组改成类似[1,2,3]
        request.post("/film/deletemore",ids).then(
            res => {
              if (res.data.code==200){
                this.$message.success("批量删除成功");
                this.setinitial()
                this.load()
              }else{
                this.$message.error("批量删除失败")
              }
            }
        )
      }
    },
    handleAvatarSuccess(res, file) {
      this.form.image = URL.createObjectURL(file.raw);
    }
  }
  ,
  created() {
    this.load()
  }
}

</script>

<style>
.el-select .el-input {
  width: 130px;
}

.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>