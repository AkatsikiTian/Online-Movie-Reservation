<template>
  <div class="wrapper" v-title data-title="晓天电影订票后台管理系统-登录">
    <div style="margin: 200px auto; background-color: #fff; width: 350px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>晓天电影订票系统管理端登录</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item  prop="username">
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username" clearable></el-input>
        </el-form-item>
        <el-form-item  prop="password">
      <el-input size="medium" style="margin: 10px 0" show-password prefix-icon="el-icon-lock" v-model="user.password" clearable></el-input>
        </el-form-item>
      <div style="margin: 10px 0; text-align: right">
        <el-form-item>
        <el-button type="primary" size="small" autocomplete="off" @click="login()" :loading="isloading">登录</el-button>
        </el-form-item>
      </div>
      </el-form>
    </div>
  </div>
</template>

<script>


import request from "@/utils/request";

export default {
  name: "Login",
  data(){
    return{
      user:{},
      isloading:false,
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods:{
    login() {
      const that = this;
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          this.isloading=true
          request.post('/login',this.user).then(
              res => {
                if (res.data.code==200){
                    const token = res.data.data
                    that.$store.commit("SET_TOKEN",token)
                    that.$message.success("登录成功")
                    this.$router.push("/manger")
                }else{
                  this.$message.error(res.data.msg)
                  this.isloading=false
                }
              }
          )
        } else {
          return false;
        }
      });
    }
  }
}
</script>

<style>
  .wrapper{
    height: 100vh;
    background-image: linear-gradient(to bottom right, #ea4add, #41e0bb);
    overflow: hidden;
  }
</style>