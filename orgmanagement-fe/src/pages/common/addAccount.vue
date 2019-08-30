<template>
  <el-row class="list-container">
    <!--搜索条件-->
    <el-col>
      <el-form :inline="true" :model="account" label-width="120px">
        <el-form-item label="登陆账号">
          <el-input v-model="account.accountLogin" clearable></el-input>
        </el-form-item>
        <el-form-item label="员工编号">
          <el-input v-model="account.empId" clearable></el-input>
        </el-form-item>
        <el-form-item label="员工姓名">
          <el-input v-model="account.empName" clearable></el-input>
        </el-form-item>
        <el-form-item label="数据权限类型">
          <el-select v-model="account.dataPermissionId" placeholder="" class="select-width">
            <el-option label="全选" value=""></el-option>
            <el-option label="全部" value="0"></el-option>
            <el-option label="递归" value="1"></el-option>
            <el-option label="本部门" value="2"></el-option>
            <el-option label="本人" value="3"></el-option>
            <el-option label="手动选择" value="4"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="员工所属部门">
          <el-input v-model="account.empDepartName" clearable></el-input>
          <el-button @click="departDialogVisible = true">选择</el-button>
        </el-form-item>
        <el-form-item label="是否关联员工">
          <el-select v-model="account.isLinkEmp" placeholder="" class="select-width">
            <el-option label="全部" value=""></el-option>
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="账号状态">
          <el-select v-model="account.status" placeholder="" class="select-width">
            <el-option label="冻结" value="0"></el-option>
            <el-option label="正常" value="1"></el-option>
            <el-option label="全部" value=""></el-option>
          </el-select>
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
          prop="accountLogin"
          label="登陆账号"
          width="120">
        </el-table-column>
        <el-table-column
          prop="empId"
          label="员工编号"
          width="120">
        </el-table-column>
        <el-table-column
          prop="empName"
          label="员工姓名">
        </el-table-column>
        <el-table-column
          prop="deptName"
          label="员工所属部门"
          width="120">
        </el-table-column>
        <el-table-column
          prop="dataPermissionId"
          label="数据权限类型">
          <template slot-scope="scope">
            <span v-if="scope.row.dataPermissionId === 4">手动选择</span>
            <span v-if="scope.row.dataPermissionId === 3">本人</span>
            <span v-if="scope.row.dataPermissionId === 2">本部门</span>
            <span v-if="scope.row.dataPermissionId === 1">递归</span>
            <span v-if="scope.row.dataPermissionId === 0">全部</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="status"
          label="账号状态">
          <template slot-scope="scope">
            <span v-if="scope.row.status === 0">冻结</span>
            <span v-if="scope.row.status === 1">正常</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="modifyTime"
          label="操作时间">
        </el-table-column>
        <el-table-column
          prop="modifyEmp"
          label="操作人">
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

    <!--分配所属部门弹窗内容-->
    <el-dialog ref="dialog" title="请选择员工所属部门" :visible.sync="departDialogVisible">
      <singleTree :treeData="treeData" v-on:checkInfo="getDepartInfo"></singleTree>
    </el-dialog>

  </el-row>
</template>

<script>
  import singleTree from '../../components/tree/singleTree';
  import '../../axios/api';

  export default {
    name: "addAccount",
    //从父组件获取的值
    props: ["parentVal"],
    components: {
      singleTree
    },
    data(){
      return{
        //从后台获取树状结构数据
        treeData:[],
        dialogVisible: this.parentVal,
        account:{
          accountLogin:'',
          empId:'',
          empName:'',
          empDepartName:'',
          dataPermissionId:'',
          status:'',
          modifyTime:'',
          modifyEmp:'',
          isLinkEmp:''
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
      this.getTreeData(); //初始化获取部门结构数据
    },
    methods: {
      //从后台获取树状结构数据
      getTreeData() {
        if (sessionStorage.getItem('deptTree')) {
          this.treeData = JSON.parse(sessionStorage.getItem('deptTree'));
        }
      },
      search() {
        if (this.account.empDepartName === '') {
          this.account.empDepart = null;
        }
        this.$axios.get('/api/orgmanagement/account/management/getValidAccountList',
          {params:
              {
                pageNum:this.page.currentPage,
                pageSize:this.page.pageSize,
                account:this.account
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
      //修改分页显示条数
      handleSizeChange(val) {
        this.page.pageSize = val;
        this.search()
      },
      //  显示第几页数据
      handleCurrentChange(val) {
        this.page.currentPage = val;
        this.search()
      },
      //从子组件中获取到选择的所属部门信息
      getDepartInfo(data) {
        this.account.empDepart= data.split("#")[0];
        this.account.empDepartName = data.split("#")[1];
        this.departDialogVisible = false
      },
      // 将选中的账号数据的信息填入角色申请新建页面
      confirm() {
        let fillInInfo = this.multipleSelection;
        this.$emit("checkInfo", fillInInfo);
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
