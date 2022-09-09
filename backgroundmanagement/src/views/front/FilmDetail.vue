<template>

<div class="whole">
  <el-row>
    <el-col :span="24">
          <div class="half">
            <div class="imglocation">
              <img :src="film.image" class="img">
            </div>
            <div class="information">
              <span style="font-size: 30px;color: #ebeff3;font-weight: bold">{{film.name}}</span>
              <i class="el-icon-view">{{film.count}}</i>
              <br>
              <span style="font-size: 20px;color: #ebeff3;font-weight: bold;margin-top: 10px">电影类型：{{film.category}}</span><br>
              <span style="font-size: 20px;color: #ebeff3;font-weight: bold;margin-top: 10px">导演：{{film.director}}</span><br>
              <span style="font-size: 20px;color: #ebeff3;font-weight: bold;margin-top: 10px">主演：{{film.actors}}</span><br>
              <span style="font-size: 20px;color: #ebeff3;font-weight: bold;margin-top: 10px">语言：{{film.language}}</span><br>
              <span style="font-size: 20px;color: #ebeff3;font-weight: bold;margin-top: 10px">时长：{{film.time}}分钟</span><br>
              <span style="font-size: 20px;color: #ebeff3;font-weight: bold;margin-top: 10px">上映时间：{{film.uptime}} 上映</span><br>
              <span style="font-size: 20px;color: #ebeff3;font-weight: bold;margin-top: 10px">简介：{{film.description}}</span>
            </div>
          </div>
    </el-col>
  </el-row>
  <el-row>
    <div class="rest">
      <el-col :span="12">
        <el-page-header @back="goBack" content="电影购票">
        </el-page-header>
      </el-col>
    </div>
  </el-row>
  <el-row>
    <div class="rest1">
      <el-col :span="12">
        <span style="font-size: 25px">{{film.name}}</span>
      </el-col>
      <el-col :span="12" style="text-align: right ">
        <span style="margin-right: 35px;font-size: 20px;margin-top: 6px;color: #22af8e">当前场次：{{total}}场</span>
        <el-button type="danger" size="medium" round :disabled="total==0" @click="buy()">选座购票</el-button>
      </el-col>
      <el-col :span="24">
        <el-tag size="large" style="margin-top: 15px">2D放映</el-tag>
      </el-col>
    </div>
  </el-row>
  <el-dialog title="选择场次(请单击确定保存)" :visible.sync="dialogvisible" width="50%">
    <el-table :data="arrangement">
      <el-table-column property="housename" label="展厅" width="150"></el-table-column>
      <el-table-column property="seatsize" label="展厅大小" width="200"></el-table-column>
      <el-table-column property="uptime" label="上映时间"></el-table-column>
      <el-table-column property="price" label="票价(元/人)"></el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="100">
      <template slot-scope="scope">
        <el-button type="primary" size="small" @click="handleClick(scope.row)">选择</el-button>
      </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</div>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "FilmDetail",
  data() {
    return {
      id: this.$route.params.id,
      film: {
        image:'',
        name:'',
        category:'',
        count:'',
        director:'',
        actors:'',
        language:'',
        time:'',
        uptime:'',
        description:''
      },
      arrangement: [],
      total: 0,
      user:{},
      isLogin:false,
      dialogvisible:false,
      formLabelWidth: '120px'
    }
  },
  methods: {
    goBack() {
      this.$router.back()
    },
    load() {
      if (this.id!=null){
        request.get("/film/" + this.id).then(res => {
          if (res.data.code == 200) {
            this.film = res.data.data
          } else {
            // this.$message.error("发生了一些错误呢o(╥﹏╥)o")
          }
        })
        request.get("/arrangement/findbyinput/" + this.id).then(res => {
          if (res.data.code == 200) {
            this.arrangement = res.data.data.data
            this.total=res.data.data.total
          } else {
            // this.$message.error("发生了一些错误呢o(╥﹏╥)o")
          }
        })
      }
    },
    buy(){
      request.get('/users/currentUser',{
        headers: {
          'Authorization':  localStorage.getItem("token")
        }
      }).then(
          res => {
            if (res.data.code==200){
              this.user = res.data.data
              this.isLogin=true
            }else{
              this.$message.warning("未登录，请先登录")
              this.isLogin=false
              this.$router.push({path:"/login1",query:{topath:"/filmdetial/"+this.id}})
            }
            if (this.isLogin){
              this.dialogvisible=true
            }
          })
    },
    handleClick(item){
      this.$router.push({path:"/select",query:{arrangement:JSON.stringify(item),userid:this.user.id}})
    }
  },
  mounted() {
    this.load()
  },
  watch:{
    $route (to, from) { //to:即将要跳转到的页面   from:即将离开的页面
      this.id = this.$route.params.id
      this.load()
    }
  }

}
</script>

<style scoped>
.whole{
  height: 100%;
}
.half {
  width: 100%;
  height: 390px;
  background-color: #7cd2e5;
  margin-bottom: 55px;
}
.imglocation{
  margin-left: 150px;
  width: 250px;
  height: 400px;
}
.img{
  width: 250px;
  height: 400px;
}
.information{
  margin-left: 500px;
  margin-top: -380px;
  padding: 10px 0;
}
.el-icon-view
{
  color: white;
  margin-left: 10px;
}
.rest{
  margin: 20px auto;
  width: 80%;
}
.rest1{
  margin: 35px auto 20px;
  width: 80%;
}
</style>