<template>
  <div v-title data-title="晓天电影订票后台管理系统-热门资讯">
    <div style="padding: 10px 0">
      <el-input placeholder="请输入查询内容" v-model="input" class="input-with-select" style="width: 500px" clearable>
        <el-select v-model="select" slot="prepend" placeholder="请选择" clearable>
          <el-option label="海报编号" value="id"></el-option>
          <el-option label="海报名称" value="title"></el-option>
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
      <el-table-column prop="id" label="海报编号" width="140">
      </el-table-column>
      <el-table-column label="海报图片">
        <template slot-scope="scope">
          <el-popover placement="top-start" title="" trigger="hover">
            <el-image :src="scope.row.pict" alt="" style="width: 200px;height: 200px"></el-image>
            <el-image slot="reference" :src="scope.row.pict" style="width: 80px;height: 80px"></el-image>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="海报名" width="120">
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
        <el-form-item label="海报名称" :label-width="formLabelWidth" prop="title">
          <el-input v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="海报缩略图" :label-width="formLabelWidth" prop="pict">
          <el-upload
              class="avatar-uploader"
              action="#"
              :http-request="uploadFile1"
              :show-file-list="false"
              :on-success="handleAvatarSuccess">
            <img v-if="form.pict" :src="form.pict" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
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
  name: "News",
  data() {
    return {
      tableData: null,
      currentPage: 1,
      total: 0,
      pageSize: 5,
      input: "",
      select: "",
      dialogvisible: false,
      form: {
        id: '',
        title: '',
        pict: '',
        createTime: ''
      },
      cat:[],
      formLabelWidth: '120px',
      checkPass:'',
      multipleSelection: [],
      rules: {
        title: [
          { required: true, message: '请输入海报名', trigger: 'blur'}
        ],
        pict: [
          {required: true,  message: '请上传图片', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    load() {
      request.get("/news", {
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

      request.post("/upload/savenewsPicture",FormDatas).then(function (res) {
        that.form.pict = res.data.data
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
        title: '',
        pict: '',
        createTime: ''
      };
    },
    findbyinput() {
      if (this.select != "" && this.input != "") {
        request.get("/news/find", {
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
          request.post("/news",this.form).then(res=>{
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
        request.delete("/news/"+film.id).then(
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
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    delmore(){
        let ids = this.multipleSelection.map(v=>v.id)//将对象数组改成类似[1,2,3]
        request.post("/news/deletemore",ids).then(
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