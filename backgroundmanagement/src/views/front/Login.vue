<template>
  <div class="login" v-title data-title="晓天电影订票系统-登录">
    <el-page-header @back="gotoShouYe" content="登录">
    </el-page-header>
    <div class="login-form">
      <div class="login-form-header">

        <img style="width: 75px; height: 75px;float: left;padding-right: 40px;" src="~@/assets/logo.png"
             alt=""/>
        <div class="login-form-text">晓天电影订票系统 - 登录</div>
      </div>
      <div style="color: #91949c;font-weight: bolder">
        <el-form :model="form" :rules="rules" ref="userForm">
          <p>Username</p>
          <el-form-item  prop="username">
        <el-input class="login-form-input" v-model="form.username" placeholder="账 号"></el-input>
          </el-form-item>
        <p>Password</p>
          <el-form-item  prop="password">
        <el-input class="login-form-input" placeholder="密 码" v-model="form.password" show-password></el-input>
          </el-form-item>
<!--          <div style="float: right">-->
<!--            <el-link href="/forget" style="font-weight: bolder;font-size: 14px;color: #91949c;"-->
<!--                     :underline="false">-->
<!--              忘记密码?-->
<!--            </el-link>-->
<!--          </div>-->
        <el-button @click="postLogin" class="login-form-button" type="primary" :loading="isloading">SIGN IN</el-button>
        </el-form>
      </div>
      <div class="login-form-footer">
        <el-link href="/Register" style="font-weight: bolder;font-size: 16px;color: #91949c;"
                 :underline="false">
          还没有账号？去注册
          <i style="font-weight: bolder;font-size: 15px" class="el-icon-right"></i>
        </el-link>
      </div>
    </div>
  </div>
</template>

<script>


import request from "@/utils/request";

export default {
  data() {
    return {
      form:{
      },
      isloading: false,
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

  methods: {
    gotoShouYe(){
      this.$router.go(-1);
    },
    postLogin(){
      const that = this;
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          that.isloading=true
          request.post('/login/login',this.form).then(
              res => {
                if (res.data.code==200){
                  const token = res.data.data
                  that.$store.commit("SET_TOKEN",token)
                  that.$message.success("登录成功")
                  that.isloading=false
                  that.$router.push(this.$route.query.topath || "/")
                }else{
                  that.$message.error(res.data.msg)
                  that.isloading=false
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

<style scoped>

.login {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #0ebde0, #7a3ed3);
  overflow: hidden;
}

.login-form {
  margin: 20px auto; background-color: #fff; width: 350px; padding: 20px; border-radius: 10px;
  width: 500px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -60%);
  letter-spacing: 2px;
}

.login-form-header {
  height: 20px;
  padding-left: 45px;
  padding-bottom: 100px;
}

.login-form-text {
  color: #000000;
  font-weight: bold;
  font-size: 30px;
  padding-top: 15px;
}

.login-form-input {
  margin-bottom: 10px;
}

.login-form-button {
  border-radius: 3px;
  width: 100%;
  font-weight: 600;
  font-size: 15px;
  letter-spacing: 2px;
  height: 60px;
  background: #5a84fd;
  box-shadow: 0 5px 30px rgb(0 66 8.5%);
  margin-top: 35px;
}

.login-form-footer {
  font-weight: bolder;
  color: #91949c;
  padding-top: 40px;
  text-align: center;
}

>>> .el-input__inner {
  height: 48px;
}

.el-checkbox {
  color: #91949c;
  font-weight: bolder;
  font-size: 15px;
}

</style>
