<template>
  <div class="wrapper" v-title data-title="晓天电影订票系统-个人中心">
    <div style="margin: 30px auto 20px; background-color: #fff; width: 60%; padding: 20px; border-radius: 10px">
      <el-row :gutter="20">
        <el-col :span="3">
          <div class="demo-image">
            <div class="block">
              <el-image
                  style="width: 100px; height: 100px"
                  :src="user.avatar"
                  fit="fill"></el-image>
            </div>
          </div>
        </el-col>
        <el-col :span="5">
          <div>
            <span>用户昵称： {{ user.nickname }}</span><br>
            <span style="margin-top:15px;color: #8c939d">用户名： {{ user.username }}</span><br>
            <span style="margin-top:15px;color: #8c939d">手机号： {{ user.phone }}</span>
          </div>
        </el-col>
      </el-row>
    </div>
    <div style="margin: 0px auto; width: 60%;height: 900px; padding: 20px; border-radius: 10px">
      <el-container>
        <el-aside width="300px" style="margin-right: 30px">
            <span class="person">个人中心</span><hr>
          <el-menu :default-openeds="['1', '3']" :default-active="$route.path" router>
                <el-menu-item index="/person" style="margin: 50px"><i class="el-icon-user"/> 个人信息</el-menu-item>
                <el-menu-item index="/person/changepassword" style="margin: 50px"><i class="el-icon-key"/>修改密码</el-menu-item>
                <el-menu-item index="/person/order" style="margin: 50px"><i class="el-icon-s-order"/>我的订单</el-menu-item>
          </el-menu>


        </el-aside>
        <el-main>
          <router-view @userinfo="refreshUser"/>
        </el-main>
      </el-container>
    </div>
  </div>
</template>

<script>


import request from "@/utils/request";

export default {
  name: "Login",
  data() {
    return {
      user: {avatar: ''},
      id: localStorage.getItem("id")
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
    refreshUser(){
      this.load()
      this.$emit('userinfo')
    }
  },
  created() {
    this.load()
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  background-color: #dadada;
  overflow: hidden;
}

.el-aside {
  overflow: hidden;
  background-color: #fff;
  color: #333;
  text-align: center;
  line-height: 80px;
}

.el-main {
  overflow: hidden;
  background-color: #fff;
  color: #333;
  text-align: center;
  line-height: 40px;
}
.person{
  font-size: 22px;
  background-image: -webkit-linear-gradient(
      left,
      rgb(42, 134, 141),
      #e9e625dc 20%,
      #3498db 40%,
      #e74c3c 60%,
      #09ff009a 80%,
      rgba(82, 196, 204, 0.281) 100%
  );
  -webkit-text-fill-color: transparent;
  -webkit-background-clip: text;
  -webkit-background-size: 200% 100%;
  -webkit-animation: masked-animation 4s linear infinite;
}
</style>