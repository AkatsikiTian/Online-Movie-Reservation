<template>
  <div v-title data-title="晓天电影订票后台管理系统-首页">
    <el-row :gutter="10" style="margin-bottom: 40px">
      <el-col :span="6">
        <el-card>
          <div style="color: #409EFF">电影总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">{{ totalfilm }}部</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="color: #67C23A">用户总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">{{ totaluser }}人</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="color: #F56C6C">订单总额</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">{{ totalprice }}元</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="color: #E6A23C">现在放映数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">{{ totalarrange }}部</div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="10" style="margin-top: 120px">
      <el-col :span="6">
        <div id="main" style="width: 800px;height:400px;margin-top: 40px"></div>
      </el-col>
      <el-col :span="6">
        <div id="main1" style="width: 800px;height:400px;margin-top: 40px;margin-left: 455px"></div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import * as echarts from 'echarts'
import request from "@/utils/request";

export default {
  name: "Home",
  data() {
    return {
      user: {},
      totalfilm: 0,
      totaluser: 0,
      totalprice: 0,
      totalarrange: 0,
      xA: [],
      data:[],
      xA1: [],
      data1:[],
    }
  },
  methods: {
    load() {
      request.get("/film", {
        params: {
          currentPage: 1,
          pageSize: 2
        }
      }).then(
          res => {
            this.totalfilm = res.data.data.total
          }
      )
      request.get("/users", {
        params: {
          currentPage: 1,
          pageSize: 2
        }
      }).then(
          res => {
            this.totaluser = res.data.data.total
          }
      )
      request.get("/Orders/sum").then(
          res => {
            this.totalprice = res.data.data
          }
      )
      request.get("/arrangement", {
        params: {
          currentPage: 1,
          pageSize: 2
        }
      }).then(
          res => {
            this.totalarrange = res.data.data.total
          }
      )
      request.get("/film/hotfilm5").then(
          res => {
            console.log(res.data.data.xAxis)
            this.xA=res.data.data.xAxis
            this.data=res.data.data.data
            this.loadecharts()
          }
      )
      request.get("/Orders/fivedayorders").then(
          res => {
            console.log(res.data.data.xAxis)
            this.xA1=res.data.data.xAxis
            this.data1=res.data.data.data
            this.loadecharts1()
          }
      )
    },
    loadecharts() {
      var chartDom = document.getElementById('main');
      var myChart = echarts.init(chartDom);
      var option;
      option = {
        title:{
          text:"最热门的5个影片",
          subtext:"柱状图",
          left:"center"
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data:this.xA,
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '热度',
            type: 'bar',
            barWidth: '60%',
            data: this.data
          }
        ]
      };

      option && myChart.setOption(option);
    },
    loadecharts1() {
      var chartDom = document.getElementById('main1');
      var myChart = echarts.init(chartDom);
      var option;
      option = {
        title:{
          text:"最近5天订单数量",
          subtext:"比例图",
          left:"center"
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data:this.xA1,
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '订单数',
            type: 'bar',
            barWidth: '60%',
            data: this.data1
          },
          {
            type: 'line',
            barWidth: '60%',
            data: this.data1
          }
        ]
      };

      option && myChart.setOption(option);
    }

  },
  mounted() {
    this.load()
  }
}
</script>

<style>

</style>