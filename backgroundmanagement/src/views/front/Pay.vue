<template>
  <div class="seat-main">
    <el-steps style="padding-bottom: 50px" :active="3" align-center>
      <el-step title="步骤1" description="选择电影场次"></el-step>
      <el-step title="步骤2" description="选择座位号"></el-step>
      <el-step title="步骤3" description="结算付款"></el-step>
    </el-steps>
    <el-card class="box-card">
      <div class="text item">
        <el-descriptions title="订单确认" direction="vertical" :column="1" border>
          <el-descriptions-item label="用户名:">{{cart.name}}</el-descriptions-item>
          <el-descriptions-item label="手机号">{{cart.phone}}</el-descriptions-item>
          <el-descriptions-item label="观看电影" :span="2">{{cart.filmname}}</el-descriptions-item>
          <el-descriptions-item label="观看影厅">{{cart.housename}}</el-descriptions-item>
          <el-descriptions-item label="观看方式">
            <el-tag size="small">2D</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="购买位置">{{cart.seats}}</el-descriptions-item>
          <el-descriptions-item label="需要付款">￥{{cart.price}}</el-descriptions-item>
        </el-descriptions>
        <div style="text-align: right;margin-top: 10px">
          <el-button type="success" size="medium" @click="pay" plain>确认付款</el-button>
          <el-button type="warning" size="medium" @click="quit" plain>取消付款</el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Pay",
  data(){
    return{
      cart:JSON.parse(sessionStorage.getItem("cart"))
    }
  },
  methods:{
    quit(){
      this.$confirm('此操作将取消订单并返回主页, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '取消订单，即将返回主页!'
        })
        this.$router.push("/")
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消返回'
        });
      });
    },
    pay(){
      let order={}
      order.userId=this.cart.userid
      order.price=this.cart.price
      order.aid=this.cart.aid
      order.phone=this.cart.phone
      order.seat=this.cart.seats
      order.filmid=this.cart.filmid
      request.post("/Orders",order).then(res=>{
        if (res.data.code==200){
          this.$message.success("付款下单成功")
          this.$router.push("/")
        }else{
          this.$message.error("下单失败")
        }
      })
    }
  }
}
</script>

<style scoped>
.seat-main {
  padding: 80px;
}
.box-card {
  width: 80%;
  margin: 0px auto;
}
.text {
  font-size: 14px;
  text-align: center;
}

.item {
  padding: 18px 0;
}
</style>