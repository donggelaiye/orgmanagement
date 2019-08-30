<template>
  <el-row class="list-container">
    <!--搜索条件-->
    <el-col>
      <el-form :inline="true" :model="employee" label-width="80px">
        <el-form-item label="员工编号">
          <el-input v-model="employee.empId" clearable></el-input>
        </el-form-item>
        <el-form-item label="员工姓名">
          <el-input v-model="employee.empName" clearable></el-input>
        </el-form-item>
        <el-form-item label="登录账号">
          <el-input v-model="employee.accountLogin" clearable></el-input>
        </el-form-item>
        <el-form-item label="是否离职">
          <el-select v-model="employee.isDimission" placeholder="" class="select-width">
            <el-option label="全部" value=""></el-option>
            <el-option label="离职" value="1"></el-option>
            <el-option label="在职" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属部门">
          <el-input v-model="employee.empDepartName" clearable></el-input>
          <el-button @click="departDialogVisible = true">选择</el-button>
        </el-form-item>
        <el-form-item label="上级部门">
          <el-input v-model="employee.superDepartName" clearable></el-input>
          <el-button @click="superDepartDialogVisible = true">选择</el-button>
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
    <el-col class="margin-top20">·
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
          prop="empId"
          label="员工编号"
          width="120">
        </el-table-column>
        <el-table-column
          prop="accountLogin"
          label="登录账号"
          width="120">
        </el-table-column>
        <el-table-column
          prop="empName"
          label="员工姓名">
        </el-table-column>
        <el-table-column
          prop="empSex"
          label="性别"
          width="50">
          <template slot-scope="scope">
            <span v-if="scope.row.empSex === 0">男</span>
            <span v-else="scope.row.empSex === 1">女</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="empPhone"
          label="员工手机">
        </el-table-column>
        <el-table-column
          prop="empEmail"
          label="员工邮箱">
        </el-table-column>
        <el-table-column
          prop="deptName"
          label="所属部门">
        </el-table-column>
        <el-table-column
          prop="superDeptName"
          label="上级部门">
        </el-table-column>
        <el-table-column
          prop="isDimission"
          label="是否离职">
          <template slot-scope="scope">
            <span v-if="scope.row.isDimission === 0">在职</span>
            <span v-else="scope.row.isDimission === 1">离职</span>
          </template>
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
    <el-dialog ref="dialog" title="请选择所属部门" :visible.sync="departDialogVisible">
      <singleTree :treeData="treeData" v-on:checkInfo="getDepartInfo"></singleTree>
    </el-dialog>

    <!--分配上级部门弹窗内容-->
    <el-dialog ref="dialog" title="请选择上级部门" :visible.sync="superDepartDialogVisible">
      <singleTree :treeData="treeData" v-on:checkInfo="getSuperDepartInfo"></singleTree>
    </el-dialog>

  </el-row>
</template>

<script>
  import singleTree from '../../components/tree/singleTree';
  import '../../axios/api';

  export default {
    name: "selectEmployee",
    //从父组件获取的值
    props: ["checkInfo"],
    components: {
      singleTree
    },
    data(){
      return{
        //从后台获取树状结构数据
        treeData:[],
        dialogVisible: this.checkInfo,
        employee:{
          empId:'',
          empName:'',
          accountLogin:'',
          isDimission:'',
          empDepart:'',
          empDepartName:'',
          superDepart:'',
          superDepartName:''
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
      search(type) {
        if (type !== 'page') {
          this.page.currentPage = 1;
        }
        this.$axios.get('/api/orgmanagement/employee/management/getEmployeeList',
          {params:
              {
                pageNum:this.page.currentPage,
                pageSize:this.page.pageSize,
                empSearchForm:this.employee
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
        this.search('page')
      },
      //  显示第几页数据
      handleCurrentChange(val) {
        this.page.currentPage = val;
        this.search('page')
      },
      //从子组件中获取到选择的所属部门信息
      getDepartInfo(data) {
        this.employee.empDepart= data.split("#")[0];
        this.employee.empDepartName = data.split("#")[1];
        this.departDialogVisible = false
      },
      //从子组件中获取到选择的上级部门信息
      getSuperDepartInfo(data) {
        this.employee.superDepart = data.split("#")[0];
        this.employee.superDepartName = data.split("#")[1];
        this.superDepartDialogVisible = false
      },
      // 将选中的员工数据的信息填入账号编辑页面
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
      checkInfo: function () {
        this.dialogVisible = this.checkInfo;
      }
    }
  }
</script>

<style scoped>

</style>
