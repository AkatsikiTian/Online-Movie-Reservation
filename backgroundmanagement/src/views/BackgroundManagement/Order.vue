<template>
  <div v-title data-title="晓天电影订票后台管理系统-电影管理">
    <div style="padding: 10px 0">
      <el-input placeholder="请输入查询内容" v-model="input" class="input-with-select" style="width: 500px" clearable>
        <el-select v-model="select" slot="prepend" placeholder="请选择" clearable>
          <el-option label="订单编号" value="number"></el-option>
          <el-option label="电影id" value="filmid"></el-option>
          <el-option label="用户id" value="user_id"></el-option>
          <el-option label="下单时间" value="order_time"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click='findbyinput'></el-button>
      </el-input>
    </div>
    <div class="pd-10">
    </div>
    <el-table
        :data="tableData" border
        style="width: 100%">
      <el-table-column
          fixed
          prop="number"
          label="订单编号"
          width="150">
      </el-table-column>
      <el-table-column
          prop="orderTime"
          label="下单时间"
          width="200">
      </el-table-column>
      <el-table-column
          prop="phone"
          label="下单电话">
      </el-table-column>
      <el-table-column
          prop="arrangementVo.filmName"
          label="电影名">
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
          prop="user.nickname"
          label="用户信息"
          width="120">
      </el-table-column>
      <el-table-column
          prop="user.nickname"
          label="手机号码"
          width="120">
      </el-table-column>
      <el-table-column
          label="操作">
        <template slot-scope="scope">
          <template v-if="scope.row.status==1">
            已付款
          </template>
          <template v-if="scope.row.status==2">
            <el-popconfirm
                confirm-button-text='好的'
                cancel-button-text='我在想想'
                icon="el-icon-info"
                icon-color="red"
                title="您确定拒绝退款吗？"
                @confirm="updatebyid(scope.row)">
            <el-button type="primary" size="small" slot="reference">拒绝</el-button>
            </el-popconfirm>
            <el-popconfirm
                confirm-button-text='好的'
                cancel-button-text='我在想想'
                icon="el-icon-info"
                icon-color="red"
                title="您确定同意退款吗？"
                @confirm="del(scope.row)">
              <el-button class="ml-5" type="danger" size="small" slot="reference">同意</el-button>
            </el-popconfirm>
          </template>
          <template v-if="scope.row.status==3">
            已退款
          </template>
          <template v-if="scope.row.status==4">
            已拒绝退款
          </template>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[1, 2, 5, 10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Order",
  data() {
    return {
      tableData: null,
      currentPage: 1,
      total: 0,
      pageSize: 5,
      input: "",
      select: "",
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
      formLabelWidth: '120px',

    }
  },
  methods: {
    load() {
      request.get("/Orders", {
        params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(
          res => {
            this.tableData = res.data.data.data
            this.total = res.data.data.total
          }
      )
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
      this.load()
    },
    findbyinput() {
      if (this.select != "" && this.input != "") {
        request.get("/Orders/find", {
          params: {
            currentPage: this.currentPage,
            pageSize: this.pageSize,
            select: this.select,
            input: this.input
          }
        }).then(
            res => {
              this.tableData = res.data.data.data
              this.total = res.data.data.total
            }
        )
      } else {
        this.load()
      }
    },
    setinitial(){
      this.currentPage=1
      this.pageSize=5
    },
    updatebyid(row){
      let form = JSON.parse(JSON.stringify(row));
      this.form.id=form.id
      this.form.filmid=form.filmid
      this.form.aid=form.aid
      this.form.price=form.price
      this.form.number=form.number
      this.form.orderTime=form.orderTime
      this.form.userId=form.user.id
      this.form.seat=form.seat
      this.form.phone=form.phone
      this.form.status=4
      request.post("/Orders",this.form).then(res=>{
        if (res.data.code==200){
          this.$message.success("更新成功")
          this.load()
        }else{
          this.$message.error("更新失败")
        }

      })
    },
    del(row){
      let form = JSON.parse(JSON.stringify(row));
      this.form.id=form.id
      this.form.filmid=form.filmid
      this.form.aid=form.aid
      this.form.price=form.price
      this.form.number=form.number
      this.form.orderTime=form.orderTime
      this.form.userId=form.user.id
      this.form.seat=form.seat
      this.form.phone=form.phone
      this.form.status=3
      request.post("/Orders",this.form).then(res=>{
        if (res.data.code==200){
          this.$message.success("更新成功")
          this.load()
        }else{
          this.$message.error("更新失败")
        }

      })
    }
  }
  ,
  created() {
    this.load()
  }
}

</script>

<style>
.el-select .el-input {
  width: 130px;
}

.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>