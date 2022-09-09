<template>
  <div v-title data-title="晓天电影订票后台管理系统-分类管理">
    <div class="pd-10" style="margin-top: 40px">
      <el-button type="primary" @click="add()">新增
        <i class="el-icon-circle-plus" style="margin-left: 5px"></i>
      </el-button>
    </div>
    <el-table :data="tableData" border stripe :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}">
      <el-table-column prop="id" label="排班id" width="140">
      </el-table-column>
      <el-table-column label="电影名" prop="filmName">
      </el-table-column>
      <el-table-column prop="housename" label="放映影厅">
      </el-table-column>
      <el-table-column prop="seatsize" label="容量(人)">
      </el-table-column>
      <el-table-column prop="uptime" label="放映时间">
      </el-table-column>
      <el-table-column prop="price" label="票价(元/人)">
      </el-table-column>
      <el-table-column prop="updateTime" label="操作时间">
      </el-table-column>
      <el-table-column
          label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="updatebyid(scope.row)">编辑<i class="el-icon-edit"></i>
          </el-button>
          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='我在想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)">
            <el-button class="ml-5" type="danger" size="small" slot="reference">删除<i class="el-icon-delete"></i>
            </el-button>
          </el-popconfirm>
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
    <el-dialog title="新增或修改电影排班(请单击确定保存)" :visible.sync="dialogvisible" width="30%">
      <el-form :model="form" status-icon ref="form" :rules="rules">
        <el-form-item label="放映电影" :label-width="formLabelWidth" prop="filmname">
          <template>
            <el-select v-model="form.filmid" placeholder="请选择">
              <el-option
                  v-for="item in film"
                  :key="item.id"
                  :label="item.name"
                  :value="parseInt(item.id)">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="放映影厅" :label-width="formLabelWidth" prop="houseid">
          <template>
            <el-select v-model="index" placeholder="请选择放映影厅">
              <el-option
                  v-for="(item,index) in house"
                  :key="item.id"
                  :label="item.name"
                  :value="index">
                <span style="float: left">{{ item.name }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.size }}</span>
              </el-option>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="票价" :label-width="formLabelWidth" prop="price">
          <el-input-number v-model="form.price" :precision="2" :step="0.1" :max="200" :min="5"></el-input-number>
        </el-form-item>
        <el-form-item label="放映日期" :label-width="formLabelWidth" prop="uptime">
            <el-date-picker
                v-model="form.uptime"
                align="right"
                type="datetime"
                value-format="yyyy-MM-ddTHH:mm:ss"
                placeholder="选择日期"
                :picker-options="pickerOptions">
            </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogvisible = false">取 消</el-button>
        <el-button type="primary" @click="save('form')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Arrangement",
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }]
      },
      tableData: null,
      currentPage: 1,
      total: 0,
      pageSize: 5,
      input: "",
      select: "",
      index: 0,
      dialogvisible: false,
      house:[],
      film:[],
      form: {
        id: '',
        houseid: 1,
        housename:'',
        filmName:'',
        filmid: '',
        price:'',
        uptime: '',
        updateTime: ''
      },
      rules: {
        filmName: [
          {required: true, message: '请选择电影', trigger: 'blur'}
        ],
        houseid: [
          {required: true, message: '请选择放映厅', trigger: 'blur'}
        ],
        price: [
          {required: true, message: '请输入票价', trigger: 'blur'}
        ],
        uptime: [
          {required: true, message: '请选择放映时间', trigger: 'blur'}
        ]
      },
      formLabelWidth: '120px'
    }
  },
  methods: {
    load() {
      request.get("/arrangement", {
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
      request.get("/house/all").then(
          res => {
            this.house = res.data.data
          }
      )
      request.get("/film/getall").then(
          res => {
            this.film = res.data.data
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
    setinitial() {
      this.currentPage = 1
      this.pageSize = 5
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form.houseid=this.house[this.index].id
          this.form.seatsize=this.house[this.index].size
          request.post("/arrangement", this.form).then(
              res => {
                if (res.data.code == 200) {
                  this.$message.success("保存成功");
                  this.dialogvisible = false
                  this.setinitial()
                  this.load()
                } else {
                  this.$message.error("保存失败，有信息重复")
                }
                this.resetForm()
              }
          )
        } else {
          this.$message.warning("尚有未填的部分");
          return false;
        }
      });
    },
    updatebyid(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogvisible = true
    },
    add() {
      this.resetForm()
      this.dialogvisible = true
    },
    resetForm() {
      this.form = {
        id: '',
        houseid: 1,
        housename:'',
        filmName:'',
        filmid: '',
        uptime: '',
        price:'',
        updateTime: '',
        index: 0
      };
    },
    del(ID) {
      request.delete("/arrangement/" + ID).then(
          res => {
            if (res.data.code == 200) {
              this.$message.success("删除成功");
              this.setinitial()
              this.load()
            } else {
              this.$message.error(res.data.msg)
            }
          }
      )
    },
  },
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
</style>