<template>
  <div style="font-size: 12px; line-height: 60px;display: flex;">
    <div style="flex: 1;font-size: 20px">
      <span :class="collapseButtonIcon" style="cursor: pointer" @click="collapse"></span>
    </div>
    <el-dropdown style="width:70px; cursor: pointer">
      <span>{{userInfo.nickname}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item  @click.native="change">修改密码</el-dropdown-item>
        <el-dropdown-item  @click.native="logout">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Header",
  props: {
    collapseButtonIcon: String,
    collapse: Function
  },
  data(){
    return{
      userInfo:{}
    }
  },
  methods:{
    change(){
      this.$router.push("/changepassword1")
    },
    logout(){
      this.request.get("/logout",{
        headers:{
          "Authorization": localStorage.getItem("token")
        }
      }).then(res=>{
        this.$store.commit("REMOVE_INFO")
        this.$router.push("/login")
      })
    }
  },
  mounted() {
    if (localStorage.getItem("token") !== null) {
      request.get('/employees/currentUser',{
        headers: {
          'Authorization':  localStorage.getItem("token")
        }
      }).then(
          res => {
            if (res.data.code==200){
              const name = res.data.data.nickname
              this.$store.commit("SET_ACCOUNT",name)
              this.userInfo = res.data.data
              this.isLogin=true
            }else{
              this.$message.error(res.data.msg)
              this.$message.warning("登录失效，请重新登录")
              this.$router.push("/login")
              this.isLogin=false
            }
          }
      )
    }
  }
}
</script>

<style scoped>

</style>