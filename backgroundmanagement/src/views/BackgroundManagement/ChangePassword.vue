<template>
  <el-main>
    <el-row>
      <el-col :span="3">
        <div @click="gotoShouYe"><i class="iconfont5 icon-fanhui" style="font-size: 25px; "></i></div>
      </el-col>
      <el-col :span="8" :offset="8">
        <div style="width:80px;margin:20px auto;font-family:STHupo" >修改密码</div>
      </el-col>

    </el-row>
    <div style="width:350px;margin:10px auto;">
      <el-form label-width="80px"  ref="ruleForm" size="small" label-position="right" :rules="rules" :model="passwordParams" >
        <el-form-item  prop="oldPassword">
                   <span slot="label" style="width:150px">
                             <span class="span-box">
                              密码
                             </span>
                   </span>
          <el-input  auto-complete="off" v-model="passwordParams.oldPassword" show-password></el-input>
        </el-form-item>
        <el-form-item  prop="newPassword">
                   <span slot="label" style="width:150px">
                             <span class="span-box">
                              新密码
                             </span>
                   </span>
          <el-input  auto-complete="off" v-model="passwordParams.newPassword" show-password></el-input>
        </el-form-item>
      </el-form>
      <el-row>
        <el-col :span="3" :offset="3"><el-button v-on:click="confirmPassword">修改密码</el-button></el-col>
        <el-col :span="3" :offset="9"><el-button v-on:click="refreshPassword">重置密码</el-button></el-col>
      </el-row>
    </div>

  </el-main>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "ChangePassword",

  data(){
    return {
      editable:false,
      password:"",
      passwordParams:{
        oldPassword:"",
        newPassword:"",
      },
      rules: {
        newPassword: [
          {required: true, message: '不能为空', trigger: 'blur'},
          {min: 6, max: 9, message: '长度在 6 到 9 个字符', trigger: 'blur'}
        ],
        oldPassword:[
          {required: true, message: '不能为空', trigger: 'blur'},
          {min: 6, max: 9, message: '长度在 6 到 9 个字符', trigger: 'blur'}
        ],

      },
    }
  },

  methods:{
    gotoShouYe(){
      this.$router.push({path:"/"});
    },
    logout(){
      console.log("执行了")
      this.request.get("/logout",{
        headers:{
          "Authorization": localStorage.getItem("token")
        }
      }).then(res=>{
        this.$store.commit("REMOVE_INFO")
        this.$router.push("/login")
      })
    },refreshPassword(){
      this.passwordParams.oldPassword=''
      this.passwordParams.newPassword=''
      this.password=''
    },
    confirmPassword(){
      this.$refs.ruleForm.validate((valid)=>{
        if(!valid){
          this.$message.error("密码填写有误")
          return false
        }
        let that = this
        //提交表单的
        request.post("/employees/changepassword",that.passwordParams,{
          headers:{
            'Authorization': localStorage.getItem("token")
          }
        }).then(res=>
        {
          if(res.data.code == 200) {
            this.$message.success("修改密码成功")
            this.logout()
          }
          else
            this.$message.error("密码不正确")

        }).catch(error=>{


        })

      })
    }


  }
}
</script>

<style scoped>

</style>
