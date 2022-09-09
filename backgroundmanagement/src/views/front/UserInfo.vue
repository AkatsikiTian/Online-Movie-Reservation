<template>
  <div>
    <el-descriptions title="简介" :column="2" :size="' '" border>
      <template slot="extra">
        <el-button type="primary" size="small" @click="dialogvisible=true">操作</el-button>
      </template>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-picture"></i>
          头像
        </template>
        <div class="demo-image">
          <div class="block">
            <el-image
                style="width: 100px; height: 100px"
                :src="user.avatar"
                fit="fill"></el-image>
          </div>
        </div>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          用户名
        </template>
        {{ user.username }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-male"/><i class="el-icon-female"/>
          性别
        </template>
        <el-tag size="small">{{ user.sex }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user-solid"></i>
          昵称
        </template>
        {{ user.nickname }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          手机号
        </template>
        {{ user.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          注册日期
        </template>
        {{ user.createTime }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          最近一次修改日期
        </template>
        {{ user.updateTime }}
      </el-descriptions-item>
    </el-descriptions>
    <el-dialog title="修改我的信息(修改后请单击确定保存)" :visible.sync="dialogvisible" width="40%">
      <el-form :model="user" status-icon ref="user" :rules="rules">
        <el-form-item label="头像" :label-width="formLabelWidth" prop="avatar">
          <el-upload
              class="avatar-uploader"
              action="#"
              :http-request="uploadFile1"
              :show-file-list="false"
              :on-success="handleAvatarSuccess">
            <img v-if="user.avatar" :src="user.avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="用户名" :label-width="formLabelWidth" prop="username">
          <el-input v-model="user.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称名" :label-width="formLabelWidth" prop="nickname">
          <el-input v-model="user.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth" prop="sex">
          <template>
            <el-radio-group v-model="user.sex">
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
              <el-radio label="未知">未知</el-radio>
            </el-radio-group>
          </template>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth" prop="phone">
          <el-input v-model="user.phone" type="number" autocomplete="off"></el-input>
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
  name: "UserInfo",

  data() {
    return {
      user: {avatar: ''},
      dialogvisible: false,
      formLabelWidth: '120px',
      id: localStorage.getItem("id"),
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
        avatar: [
          {required: true,  message: '请上传头像', trigger: 'blur' }
        ]
      }
    }
  },

  methods: {
    load() {
      request.get('/users/' + this.id).then(
          res => {
            if (res.data.code == 200) {
              this.user = res.data.data
            } else {
              this.$message.warning("未登录")
              this.$router.push("/")
            }
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
      request.post("/users/savePicture",FormDatas).then(function (res) {
        that.user.avatar = res.data.data
        that.$message.success("上传成功")
      }).catch(function (error){
        that.$message.error("上传失败"+error.data.msg)
      })

    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post("/users",this.user).then(res=>{
            if (res.data.code==200){
              const token = res.data.data
              this.$store.commit("SET_TOKEN",token)
              this.$message.success("更新成功");
              this.dialogvisible = false
              this.$emit('userinfo')
              this.load()
            }else{
              this.$message.error(res.data.msg)
            }
          })
        } else {
          this.$message.warning("尚有未填的部分");
          return false;
        }
      })
    },
    handleAvatarSuccess(res, file) {
      this.form.image = URL.createObjectURL(file.raw);
    }
  },
  created() {
    this.load()
  }
}
</script>

<style scoped>
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
