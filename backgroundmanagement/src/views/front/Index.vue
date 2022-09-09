<template>
  <div>
    <div style="display: flex;height: 60px;line-height: 60px;border-bottom: 1px solid #ccc" v-title data-title="晓天电影订票系统">
      <div style="width: 200px;text-align: center">晓天电影订票系统</div>
      <div>
        <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect" router>
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/category">电影分类</el-menu-item>
          <el-menu-item index="/person/order" :disabled="!isLogin">订单管理</el-menu-item>
        </el-menu>
      </div>
      <div style="margin-left: 700px;flex: 1">
        <span style="color: #8c939d;font-size: small;margin-right: 5px">搜索电影:</span>
        <el-autocomplete
            v-model="search"
            :fetch-suggestions="querySearchAsync"
            placeholder="请输入内容"
            @select="handleSelect"
        ></el-autocomplete>
      </div>
      <div v-if="!isLogin" style="width: 200px">
        <a style="color: #41e0bb;margin-right: 20px" href="/Login1">登录</a>
        <a style="color: #41e0bb" href="/Register">注册</a>
      </div>
      <div v-if="isLogin" style="width: 200px">
        <el-avatar :size="40" :src="user.avatar" style="vertical-align: middle;margin-right: 5px"></el-avatar>
        <el-dropdown>
        <span class="el-dropdown-link">{{user.nickname}}<i class="el-icon-arrow-down el-icon--right"></i></span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="personinfo">个人信息</el-dropdown-item>
          <el-dropdown-item @click.native="changepassword">修改密码</el-dropdown-item>
          <el-dropdown-item @click.native="logout">退出账号</el-dropdown-item>
        </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <router-view @userinfo="refreshUser"/>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Index",
  data(){
    return{
      isLogin:false,
      user:{},
      search:''
    }
  },
  mounted() {
    if (localStorage.getItem("token") !== null) {
      request.get('/users/currentUser',{
        headers: {
          'Authorization':  localStorage.getItem("token")
        }
      }).then(
          res => {
            if (res.data.code==200){
              const name = res.data.data.nickname
              const id = res.data.data.id
              this.$store.commit("SET_ID",id)
              this.$store.commit("SET_ACCOUNT",name)
              this.user = res.data.data
              this.isLogin=true
            }else{
              this.$message.warning("登录失效，请重新登录")
              this.isLogin=false
            }
          }
      )
    }
  },
methods: {
  handleSelect(key, keyPath) {
    this.$router.push({path: `/filmdetial/${key.id}`})
  },
  personinfo(){
    this.$router.push("/person")
  },
  changepassword(){
    this.$router.push("/person/changepassword")
  },
  refreshUser() {
    request.get('/users/' + localStorage.getItem("id")).then(
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
  logout(){
    this.request.get("/logout",{
      headers:{
        "Authorization": localStorage.getItem("token")
      }
    }).then(res=>{
      this.$store.commit("REMOVE_INFO")
      this.$router.push("/login1")
    })
  },
  querySearchAsync(queryString,cb){
    var search=this.search;
    if (this.search==''){
       search="**all"
    }
      request.post("/film/search",search).then(res=>{
        var result=[]
        for (const item of res.data.data){
          result.push({
            id:item.id,
            value:item.name
          });
        }
        cb(result)
      })
  }
}
}
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
  color: #171718;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>