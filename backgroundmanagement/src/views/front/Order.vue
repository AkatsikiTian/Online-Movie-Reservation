<template>
  <div>
    <el-table
        :data="tableData"
        style="width: 100%"
        max-height="450">
      <el-table-column
          fixed
          prop="number"
          label="订单编号"
          width="150">
      </el-table-column>
      <el-table-column
          prop="orderTime"
          label="下单时间"
          width="120">
      </el-table-column>
      <el-table-column
          prop="phone"
          label="下单电话"
          width="120">
      </el-table-column>
      <el-table-column
          prop="arrangementVo.filmName"
          label="电影名"
          width="120">
      </el-table-column>
      <el-table-column
          prop="seat"
          label="座位号"
          width="120">
      </el-table-column>
      <el-table-column
          prop="arrangementVo.uptime"
          label="观影时间"
          width="120">
      </el-table-column>
      <el-table-column
          prop="arrangementVo.housename"
          label="观影影厅"
          width="120">
      </el-table-column>
      <el-table-column
          prop="price"
          label="已付金额"
          width="120">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="120">
        <template slot-scope="scope">
          <template v-if="scope.row.status==1">
            <el-button
                @click.native.prevent="deleteRow(scope.$index, tableData)"
                type="text"
                size="small">
              退款
            </el-button>
          </template>
          <template v-if="scope.row.status==2">
            等待管理员审核
          </template>
          <template v-if="scope.row.status==3">
            已退款
          </template>
          <template v-if="scope.row.status==4">
            管理员拒绝退款
          </template>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Order",
  data () {
    return {
      currentPage:1,
      pagesize:3,
      tableData:[],
      form: {
        id: '',
        number: '',
        user: {},
        aid: '',
        orderTime: '',
        status: 1,
        filmid:'',
        seat:0,
        phone: '',
        arrangementVo: {},
        price: 0
      },
    }
  },
  methods: {
    load () {
      request.get("/Orders/user/"+localStorage.getItem("id")).then(res=>{
        this.tableData = res.data.data
      })
    },
    deleteRow(index,tableData){
      let form = JSON.parse(JSON.stringify(tableData[index]));
      this.form.id=form.id
      this.form.filmid=form.filmid
      this.form.aid=form.aid
      this.form.price=form.price
      this.form.number=form.number
      this.form.orderTime=form.orderTime
      this.form.userId=form.user.id
      this.form.seat=form.seat
      this.form.phone=form.phone
      this.form.status=2
      request.post("/Orders",this.form).then(res=>{
        if (res.data.code==200){
          this.$message.success("退款申请提交成功,等待管理员审核")
          this.load()
        }else{
          this.$message.error("退款申请提交失败")
        }

      })
    }
    },
  mounted() {
    this.load()
  }
}
</script>

<style scoped>

</style>