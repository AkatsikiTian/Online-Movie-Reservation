<template>
  <div>
    <div>
      <div class="showImg">
        <span style="padding: 10px 0;font-size: 35px;font-weight: bold">选电影</span><br>
        <div style="font-size: 25px">
          <el-button type="text" size="medium" @click="getfilm(-1,0,true)">最新</el-button>
          <el-button type="text" size="medium"  @click="getfilm(-2,0,true)">最热</el-button>
          <el-button v-for="(item,index) in category" :key="item.id" type="text" size="medium"  @click="getfilm(item.id,index,true)">{{item.name}}</el-button>
        </div>

      </div>
      <div class="showfilm">
        <span style="font-weight: bold;padding: 10px 0">{{title}}</span>
        <el-row :gutter="10">
          <el-col v-for="item in film" :key="item.id" :span="6">
            <a @click="view(item.id)" style="text-align: center">
              <img :src="item.image" style="width: 250px;height: 400px"/>
              <div style="padding: 10px 0">
                <span>{{item.name}}</span>
              </div>
            </a>
          </el-col>
        </el-row>
        <div style="margin: 35px auto">
          <span class="demonstration">显示总数</span>
          <el-pagination
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              :page-size="pageSize"
              layout="total, prev, pager, next"
              :total="total">
          </el-pagination>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Category",
  data(){
    return {
      category:[],
      film:[],
      title:'最新上线',
      currentPage: 1,
      pageSize: 8,
      total: 0,
      index: 0,
      type: 0
    }
  },
  methods:{
    load(){
      request.get("category/category0").then(res=>{
        this.category=res.data.data
      })
      request.get("film/getfilm",{
        params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize,
          categoryId: -1
        }
      }).then(res=>{
        this.film=res.data.data.data
        this.total=res.data.data.total
        this.type=-1
      })
    },
    view(id) {
      this.$router.push({path: `filmdetial/${id}`})
    },
    getfilm(type,index,reset){
      if (reset){
        this.currentPage=1
        this.type=type
        this.index=index
      }
      if (type==-1){
        request.get("film/getfilm",{
          params: {
            currentPage: this.currentPage,
            pageSize: this.pageSize,
            categoryId: -1
          }
        }).then(res=>{
          this.title='最新上线'
          this.film=res.data.data.data
          this.total=res.data.data.total
        })
      }else if (type==-2){
        request.get("film/hotfilm",{
          params: {
            currentPage: this.currentPage,
            pageSize: this.pageSize,
          }
        }).then(res=>{
          this.title='最热影片'
          this.film=res.data.data.data
          this.total=res.data.data.total
          this.type=-2
        })
      }else if (type>=0){
        request.get("film/getfilm",{
          params: {
            currentPage: this.currentPage,
            pageSize: this.pageSize,
            categoryId: type
          }
        }).then(res=>{
          this.title=this.category[index].name
          this.film=res.data.data.data
          this.total=res.data.data.total
          this.type=type
          this.index=index
        })
      }
    },
    handleCurrentChange(val) {
      this.currentPage=val
      this.getfilm(this.type,this.index,false)
    }
  },
  //进入页面后启动定时轮询
  mounted(){
    this.load()
  }
}
</script>

<style scoped>
.showImg{
  position: relative;
  width: 60%;
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