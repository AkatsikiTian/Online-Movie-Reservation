<template>
    <div v-title data-title="晓天电影订票后台管理系统-员工管理">
      <div style="padding: 10px 0">
        <el-input placeholder="请输入查询内容" v-model="input" class="input-with-select" style="width: 500px" clearable>
          <el-select v-model="select" slot="prepend" placeholder="请选择" clearable>
            <el-option label="工号" value="id"></el-option>
            <el-option label="用户名" value="username"></el-option>
            <el-option label="姓名" value="name"></el-option>
            <el-option label="电话号码" value="phone"></el-option>
            <el-option label="身份证号码" value="idNumber"></el-option>
            <el-option label="性别" value="sex"></el-option>
          </el-select>
          <el-button slot="append" icon="el-icon-search" @click='findbyinput'></el-button>
        </el-input>
      </div>
      <div class="pd-10">
        <el-button type="primary" @click="add()">新增
          <i class="el-icon-circle-plus" style="margin-left: 5px"></i>
        </el-button>
        <el-popconfirm style="margin-left: 5px;margin-right: 5px"
                       confirm-button-text='好的'
                       cancel-button-text='我在想想'
                       icon="el-icon-info"
                       icon-color="red"
                       title="您确定删除吗？"
                       @confirm="delmore">
          <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove" style="margin-left: 5px"></i></el-button>
        </el-popconfirm>
      </div>
      <el-table :data="tableData" border stripe :header-cell-style="{'text-align':'center'}"
                :cell-style="{'text-align':'center'}"  @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="55"></el-table-column>
        <el-table-column prop="id" label="工号" width="140">
        </el-table-column>
        <el-table-column prop="username" label="用户名" width="120">
        </el-table-column>
        <el-table-column prop="name" label="昵称">
        </el-table-column>
        <el-table-column prop="sex" label="性别">
        </el-table-column>
        <el-table-column prop="phone" label="手机号码">
        </el-table-column>
        <el-table-column prop="idNumber" label="身份证号码">
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
      <el-dialog title="新增或修改用户(修改后请单击确定保存)" :visible.sync="dialogvisible" width="30%">
        <el-form :model="form" status-icon  ref="form" :rules="rules">
          <el-form-item label="用户名" :label-width="formLabelWidth" prop="username">
            <el-input v-model="form.username" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="姓名" :label-width="formLabelWidth" prop="name">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="性别" :label-width="formLabelWidth" prop="sex">
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="手机号" :label-width="formLabelWidth" prop="phone">
            <el-input v-model="form.phone" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="身份证号" :label-width="formLabelWidth" prop="idNumber">
            <el-input v-model="form.idNumber" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="封禁状态" :label-width="formLabelWidth">
            <el-switch
                style="display: block"
                v-model="form.status"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="是"
                inactive-text="否"
                :active-value="1"
                :inactive-value="0">
            </el-switch>
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
  name: "Employee",
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
        username: '',
        name: '',
        sex: '',
        phone: '',
        idNumber: '',
        status: 0
      },
      formLabelWidth: '120px',
      checkPass:'',
      multipleSelection: [],
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        name: [
          {required: true,  message: '请输入姓名', trigger: 'blur' }
        ],
        phone: [
          {required: true,  message: '请输入手机号', trigger: 'blur' }
        ],
        idNumber: [
          {required: true,  message: '请输入身份证号', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    load() {
      request.get("/employees", {
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
    add(){
      this.resetForm()
      this.dialogvisible = true
    },
    resetForm() {
      this.form={
        username: '',
        password: '123456',
        name: '',
        sex: '男',
        idNumber: '',
        phone: '',
        status: 0
      };
    },
    findbyinput() {
      if (this.select != "" && this.input != "") {
        request.get("/employees/find", {
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
                request.post("/employees/save",this.form,{headers : {'Authorization' : localStorage.getItem("token")}}).then(res=>{
                    if (res.data.code==200){
                      this.$message.success("更新成功");
                      this.dialogvisible = false
                      this.setinitial()
                      this.load()
                    }else{
                      this.$message.error(res.data.msg)
                    }
                })
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
    del(ID){
      if (ID==1){
        this.$message.warning("管理员不可以删除")
      }else{
        request.delete("/employees/"+ID,{headers : {'Authorization' : localStorage.getItem("token")}}).then(
            res => {
              if (res.data.code==200){
                this.$message.success("删除成功");
                this.setinitial()
                this.load()
              }else{
                this.$message.error("删除失败")
              }
            }
        )
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    delmore(){
      let ids = this.multipleSelection.map(v=>v.id)//将对象数组改成类似[1,2,3]
      if(ids.indexOf("1")>-1){
        this.$message.warning("管理员不可删除")
      }else{
        request.post("/employees/deletemore",ids,{headers : {'Authorization' : localStorage.getItem("token")}}).then(
            res => {
              if (res.data.code==200){
                this.$message.success("批量删除成功");
                this.setinitial()
                this.load()
              }else{
                this.$message.error("批量删除失败,原因："+res.data.msg)
              }
            }
        )
      }
    }
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