<template>
    <div v-title data-title="晓天电影订票后台管理系统-用户管理">
      <div style="padding: 10px 0">
        <el-input placeholder="请输入查询内容" v-model="input" class="input-with-select" style="width: 500px" clearable>
          <el-select v-model="select" slot="prepend" placeholder="请选择" clearable>
            <el-option label="id" value="id"></el-option>
            <el-option label="用户名" value="username"></el-option>
            <el-option label="昵称" value="nickname"></el-option>
            <el-option label="电话号码" value="phone"></el-option>
          </el-select>
          <el-button slot="append" icon="el-icon-search" @click='findbyinput'></el-button>
        </el-input>
      </div>
      <div class="pd-10">
        <el-button type="primary" @click="add()">新增
          <i class="el-icon-circle-plus" style="margin-left: 5px"></i>
        </el-button>
      </div>
      <el-table :data="tableData" border stripe :header-cell-style="{'text-align':'center'}"
                :cell-style="{'text-align':'center'}"  @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="55"></el-table-column>
        <el-table-column prop="id" label="工号" width="140">
        </el-table-column>
        <el-table-column label="用户头像">
          <template slot-scope="scope">
            <el-popover placement="top-start" title="" trigger="hover">
              <el-image :src="scope.row.avatar" alt="" style="width: 200px;height: 200px"></el-image>
              <el-image slot="reference" :src="scope.row.avatar" style="width: 80px;height: 80px"></el-image>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名" width="120">
        </el-table-column>
        <el-table-column prop="nickname" label="姓名">
        </el-table-column>
        <el-table-column prop="sex" label="性别">
        </el-table-column>
        <el-table-column prop="phone" label="电话号码">
        </el-table-column>
        <el-table-column label="账号状态">
          <template slot-scope="scope">
              <div v-if="scope.row.status==1">封禁</div>
              <div v-else>正常</div>
          </template>
        </el-table-column>
        <el-table-column
            label="操作">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="updatebyid(scope.row,true)">解封<i class="el-icon-unlock"></i></el-button>
            <el-button type="danger" size="small" @click="updatebyid(scope.row,false)">封禁<i class="el-icon-lock"></i></el-button>
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
      <el-dialog title="新增用户信息" :visible.sync="dialogvisible" width="30%">
        <el-form :model="form" status-icon ref="user" :rules="rules">
          <el-form-item label="头像" :label-width="formLabelWidth" prop="avatar">
            <el-upload
                class="avatar-uploader"
                action="#"
                :http-request="uploadFile1"
                :show-file-list="false"
                :on-success="handleAvatarSuccess">
              <img v-if="form.avatar" :src="form.avatar" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="用户名" :label-width="formLabelWidth" prop="username">
            <el-input v-model="form.username" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="昵称名" :label-width="formLabelWidth" prop="nickname">
            <el-input v-model="form.nickname" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="性别" :label-width="formLabelWidth" prop="sex">
            <template>
              <el-radio-group v-model="form.sex">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
                <el-radio label="未知">未知</el-radio>
              </el-radio-group>
            </template>
          </el-form-item>
          <el-form-item label="手机号" :label-width="formLabelWidth" prop="phone">
            <el-input v-model="form.phone" type="number" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogvisible = false">取 消</el-button>
          <el-button type="primary" @click="save('user')">确 定</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "User",
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
        username: '',
        password: '123456',
        nickname: '',
        avatar: '',
        sex: '未知',
        phone: ''
      },
      formLabelWidth: '120px',
      checkPass:'',
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        nickname: [
          {required: true,  message: '请输入昵称', trigger: 'blur' }
        ],
        phone: [
          {required: true,  message: '请输入电话号码', trigger: 'blur' },
          { min: 11, max: 11, message: '手机格式不正确,应该为11位', trigger: 'blur' }
        ],
      },
      multipleSelection: []
    }
  },
  methods: {
    load() {
      request.get("/users", {
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
    findbyinput() {
      if (this.select != "" && this.input != "") {
        request.get("/users/find", {
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
      request.post("/users/savePicture",FormDatas).then(res=>{
        if (res.data.code==200){
          that.form.avatar = res.data.data
          that.$message.success("上传成功")
        }else{
          that.$message.error("上传失败")
        }
      })
    },
    handleAvatarSuccess(res, file) {
      this.form.image = URL.createObjectURL(file.raw);
    },
    setinitial(){
      this.currentPage=1
      this.pageSize=5
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post("/users", this.form).then(
              res => {
                if (res.data.code==200){
                  this.$message.success("保存成功");
                  this.dialogvisible = false
                  this.setinitial()
                  this.load()
                }else{
                  this.$message.error("保存失败，有信息重复")
                }
                this.resetForm()
              }
          )
        } else {
          this.$message.warning("尚有未填的部分");
          return false;
        }
      });
    },
    updatebyid(row,lock){
      this.form = JSON.parse(JSON.stringify(row));
      var s='封禁'
      if (!lock){
        this.form.status=1
      }else{
        this.form.status=0
        s='解封'
      }
      request.post("/users", this.form).then(
          res => {
            if (res.data.code==200){
              this.$message.success(s+"成功");
              this.setinitial()
              this.load()
            }else{
              this.$message.error("封禁失败")
            }
            this.resetForm()
          }
      )
    },
    resetForm() {
      this.form={
        username: '',
        password: '123456',
        nickname: '',
        avatar: '',
        sex: '未知',
        phone: ''
      };
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    }
  },
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