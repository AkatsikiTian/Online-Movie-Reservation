<template>
<div>
  <div>
    <div class="showImg">
      <template>
        <el-carousel indicator-position="outside" height="500px">
          <el-carousel-item v-for="(item) in imgArr" :key="item.id">
                  <img :src="item.pict"/>
          </el-carousel-item>
        </el-carousel>
      </template>
    </div>
    <div class="showfilm">
      <span style="font-weight: bold;padding: 10px 0">热门影片</span>
      <el-row :gutter="10">
        <el-col v-for="item in hotfilm" :key="item.id" :span="6">
          <a @click="view(item.id)" style="text-align: center">
            <img :src="item.image" style="width: 250px;height: 400px"/>
            <div style="padding: 10px 0">
              <span>{{item.name}}</span>
            </div>
          </a>
        </el-col>
      </el-row>
      <span style="font-weight: bold;padding: 10px 0">最新上线</span>
      <el-row :gutter="10">
        <el-col v-for="item in newfilm" :key="item.id" :span="6">
          <a @click="view(item.id)" style="text-align: center">
            <img :src="item.image" style="width: 250px;height: 400px"/>
            <div style="padding: 10px 0">
              <span>{{item.name}}</span>
            </div>
          </a>
        </el-col>
      </el-row>
    </div>
  </div>

</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Home",
  data(){
    return {
      imgArr:[],
      hotfilm:[],
      newfilm:[],
      currentPage: 1,
      pageSize: 8
    }
  },
  methods:{
    loadNews(){
      request.get("news/load").then(res=>{
        if (res.data.code==200){
          this.imgArr=res.data.data
        }else{
          console.log("获取失败")
        }
      })
    },
    view(id) {
      this.$router.push({path: `filmdetial/${id}`})
    },
    load(){
      request.get("film/hotfilm",{
        params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(res=>{
        this.hotfilm=res.data.data.data
      })
      request.get("film/getfilm",{
        params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize,
          categoryId: -1
        }
      }).then(res=>{
        this.newfilm=res.data.data.data
      })
    }
  },
  //进入页面后启动定时轮询
  mounted(){
    this.load()
    this.loadNews()
  }
}
</script>

<style scoped>
.showImg{
  position: relative;
  width: 60%;
  height: 500px;
  margin: 20px auto;
  overflow: hidden;
}
.showfilm{
  position: relative;
  width: 60%;
  height: 500px;
  margin: 20px auto;
}
/* 轮播图片 */
.showImg img{
  width: 100%;
  height: 100%;
}

.el-carousel__item img {
  width: 100%;
  height: 100%;
  opacity: 0.85;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
.el-row {
  margin-bottom: 20px;
}
.el-col {
  border-radius: 4px;
}
</style>