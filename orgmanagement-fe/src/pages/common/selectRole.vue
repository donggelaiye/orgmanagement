<template>
  <el-row class="list-container">
    <!--搜索条件-->
    <el-col>
      <el-form :inline="true" :model="role" label-width="80px">
        <el-form-item label="角色名称">
          <el-input v-model="role.roleName" clearable></el-input>
        </el-form-item>
      </el-form>
    </el-col>
    <!--操作按钮-->
    <el-col :offset="19" :span="5">
      <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
    </el-col>
    <el-col :offset="1" :span="23" class="margin-top20">
      <el-button icon="el-icon-edit-outline" @click="confirm" round>确认选择</el-button>
      <el-button icon="el-icon-edit-outline" @click="cancel" round>取消</el-button>
    </el-col>
    <!--展示列表-->
    <el-col class="margin-top20">
      <el-table
        ref="multipleTable"
        :data="tableData"
        :highlight-current-row="true"
        @selection-change="handleSelectionChange"
        class="table—style"
        height="250"
        border>
        <el-table-column
          type="selection"
          prop="id"
          width="55">
        </el-table-column>
        <el-table-column
          prop="roleId"
          label="角色ID"
          width="120">
        </el-table-column>
        <el-table-column
          prop="roleName"
          label="角色名称"
          width="120">
        </el-table-column>
        <el-table-column
          prop="approveAccount"
          label="审批人账号">
        </el-table-column>
        <el-table-column
          prop="approveId"
          label="审批人员工编号"
          width="120">
          <template slot-scope="scope">
            <span v-if="scope.row.empSex === 0">男</span>
            <span v-else="scope.row.empSex === 1">女</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="approveName"
          label="审批人姓名">
        </el-table-column>
        <el-table-column
          prop="approveDepartment"
          label="审批人所属部门"
          width="120">
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态">
        </el-table-column>
        <el-table-column
          prop="description"
          label="描述">
        </el-table-column>
      </el-table>
    </el-col>
    <el-col :span="20" class="margin-top20 margin-left20">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page=page.currentPage
        :page-sizes="[10, 20, 30, 40]"
        :page-size=page.pageSize
        layout="total, sizes, prev, pager, next, jumper"
        :total=page.total>
      </el-pagination>
    </el-col>

  </el-row>
</template>

<script>
  import '../../axios/api';

  export default {
    name: "selectRole",
    //从父组件获取的值
    props: ["parentVal"],
    data(){
      return{
        dialogVisible: this.parentVal,
        role:{
          roleId:'',
          roleName:'',
          approveAccount:'',
          approveId:'',
          approveName:'',
          approveDept:'',
          status:'',
          remark:''
        },
        tableData: [],
        multipleSelection: [],
        departDialogVisible: false,
        superDepartDialogVisible: false,
        checkId:'', //由子组件树状结构传来的部门id和名称
        checkVal:'',
        page:{
          total:10,
          currentPage:1,
          pageSize:10
        }
      }
    },
    created:function(){
      this.search();
    },
    methods: {
      search(type) {
        if (type !== 'page') {
          this.page.currentPage = 1;
        }
        this.$axios.get('/api/orgmanagement/role/management/getRoleList',
          {params:
              {
                pageNum:this.page.currentPage,
                pageSize:this.page.pageSize,
                role:this.role
              }
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response=>{
            if (response.data.code === 200) {
              this.tableData = response.data.data.pageData;
              this.page.total = response.data.data.total;

            }else {
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error=>{
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      //限制只能选中一条进行操作
      checkChoiceNum() {
        let msg = '';
        if (this.multipleSelection.length === 0){
          msg = '请选中一条记录进行操作！'

        }else if (this.multipleSelection.length > 1) {
          msg = '只能选中一条记录进行操作!'

        }else {
          return true
        }
        //弹窗提示
        this.$alert(msg, '提示', {
          confirmButtonText: '确定',
        });
      },
      //修改分页显示条数
      handleSizeChange(val) {
        this.page.pageSize = val;
        this.search('page');
      },
      //  显示第几页数据
      handleCurrentChange(val) {
        this.page.currentPage = val;
        this.search('page');
      },
      // 将选中的角色数据的信息填入角色申请新建页面
      confirm() {
        let fillInInfo = this.multipleSelection;
        this.$emit("checkInfo", fillInInfo)
      },
      // 取消方法
      cancel() {
        this.dialogVisible = false
        if (this.dialogVisible === false) {
          this.$emit("dialogClose", false)
        }
      }
    },
    watch: {
      parentVal: function () {
        this.dialogVisible = this.parentVal;
      }
    }
  }
</script>

<style scoped>

</style>
