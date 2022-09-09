<template>
    <div v-title data-title="晓天电影订票后台管理系统-分类管理">
      <div class="pd-10" style="margin-top: 40px">
        <el-button type="primary" @click="add()">新增
          <i class="el-icon-circle-plus" style="margin-left: 5px"></i>
        </el-button>
      </div>
      <el-table :data="tableData" border stripe :header-cell-style="{'text-align':'center'}"
                :cell-style="{'text-align':'center'}">
        <el-table-column prop="id" label="uid" width="140">
        </el-table-column>
        <el-table-column label="分类类型">
          <template slot-scope="scope">
              <span v-if="scope.row.type==0">电影分类</span>
              <span v-if="scope.row.type!=0">其他分类</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="分类名称">
        </el-table-column>
        <el-table-column prop="updateTime" label="操作时间">
        </el-table-column>
        <el-table-column
            label="操作">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="updatebyid(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
            <el-popconfirm
                confirm-button-text='好的'
                cancel-button-text='我在想想'
                icon="el-icon-info"
                icon-color="red"
                title="您确定删除吗？"
                @confirm="del(scope.row.id)">
              <el-button class="ml-5" type="danger" size="small" slot="reference">删除<i class="el-icon-delete"></i></el-button>
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
      <el-dialog title="新增或修改分类(请单击确定保存)" :visible.sync="dialogvisible" width="30%">
        <el-form :model="form" status-icon  ref="form" :rules="rules">
          <el-form-item label="分类名称" :label-width="formLabelWidth" prop="name">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="分类类型" :label-width="formLabelWidth" prop="type">
            <el-radio-group v-model="form.type">
              <el-radio :label="0">电影分类</el-radio>
            </el-radio-group>
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
  name: "Category",
  data() {
    return {
      tableData: null,
      currentPage: 1,
      total: 0,
      pageSize: 5,
      input: "",
      select: "",
      dialogvisible: false,
      form: {
        id: '',
        name: '',
        type: 0,
        updateTime: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入分类名', trigger: 'blur'}
        ]
      },
      formLabelWidth: '120px'
    }
  },
  methods: {
    load() {
      request.get("/category", {
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
        request.get("/category/find", {
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
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post("/category", this.form).then(
              res => {
                if (res.data.code==200){
                  this.$message.success("保存成功");
                  this.dialogvisible = false
                  this.setinitial()
                  this.load()
                }else{
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
    updatebyid(row){
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogvisible = true
    },
    add(){
      this.resetForm()
      this.dialogvisible = true
    },
    resetForm() {
      this.form={
        name: '',
        type: 0
      };
    },
    del(ID){
      request.delete("/category/"+ID).then(
          res => {
            if (res.data.code==200){
              this.$message.success("删除成功");
              this.setinitial()
              this.load()
            }else{
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