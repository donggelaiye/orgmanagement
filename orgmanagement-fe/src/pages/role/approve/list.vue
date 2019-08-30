<template>
  <el-row class="list-container">
    <!--搜索条件-->
    <el-col>
      <el-form :inline="true" :model="roleApproveForm" label-width="120px">
        <el-form-item label="角色申请编号:">
          <el-input v-model="roleApproveForm.applyCode" clearable></el-input>
        </el-form-item>
        <el-form-item label="申请角色ID:">
          <el-input v-model="roleApproveForm.roleId" clearable></el-input>
        </el-form-item>
        <el-form-item label="申请角色名称:">
          <el-input v-model="roleApproveForm.roleName" clearable></el-input>
        </el-form-item>
        <el-form-item label="申请人登录账号:">
          <el-input v-model="roleApproveForm.applyAccountLogin" clearable></el-input>
        </el-form-item>
        <el-form-item label="申请人员工编号:">
          <el-input v-model="roleApproveForm.applyEmpId" clearable></el-input>
        </el-form-item>
        <el-form-item label="申请人员工姓名:">
          <el-input v-model="roleApproveForm.applyEmpName" clearable></el-input>
        </el-form-item>
        <el-form-item label="申请人所属部门:">
          <el-input v-model="roleApproveForm.applyDept" ></el-input>
          <el-button @click="departDialogVisible = true">选择</el-button>
        </el-form-item>
        <!--        申请时间 开始到结束-->
        <el-form-item label="申请时间:" label-width="100px">
          <div class="block">
            <el-date-picker
              v-model="applyTimePicker"
              type="datetimerange"
              :picker-options="pickerOptions"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              align="right">
            </el-date-picker>
          </div>
        </el-form-item>
        <!--        操作时间 开始到结束-->
        <el-form-item label="操作时间:" label-width="100px">
          <div class="block">
            <el-date-picker
              v-model="operateTimePicker"
              type="datetimerange"
              :picker-options="pickerOptions"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              align="right">
            </el-date-picker>
          </div>
        </el-form-item>
      </el-form>
    </el-col>
    <!--操作按钮-->
    <el-col :offset="21" :span="3">
      <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
    </el-col>
    <el-col :offset="1" :span="23" class="margin-top20">
      <el-button v-if="(buttonPermission.indexOf('getApprove') > -1)" type="primary" icon="el-icon-edit-outline" @click="approve" round>审核</el-button>
    </el-col>
    <!--展示列表-->
    <el-col class="margin-top20">
      <el-table
        ref="multipleTable"
        :data="tableData"
        :highlight-current-row="true"
        @selection-change="handleSelectionChange"
        @cell-click="handleCellClick"
        class="table—style" border>
        <el-table-column
          type="selection"
          prop="applyCode"
          width="55">
        </el-table-column>
        <el-table-column
          prop="applyCode"
          label="角色申请编号"
          width="143">
          <template slot-scope="scope">
            <el-link type="primary">{{scope.row.applyCode}}</el-link>
          </template>
        </el-table-column>
        <el-table-column
          prop="roleId"
          label="申请角色ID"
          width="120">
          <template slot-scope="scope">
            <el-link type="primary">{{scope.row.roleId}}</el-link>
          </template>
        </el-table-column>
        <el-table-column
          prop="roleName"
          label="申请角色名称"
          width="120">
        </el-table-column>
        <el-table-column
          prop="roleApproveName"
          label="审批负责人"
          width="120">
        </el-table-column>
        <el-table-column
          prop="bussinessLine"
          label="角色支持业务线"
          width="198">
        </el-table-column>
        <el-table-column
          prop="applyAccountLogin"
          label="申请人登录账号"
          width="120">
        </el-table-column>
        <el-table-column
          prop="applyEmpId"
          label="申请人员工编号"
          width="90">
        </el-table-column>
        <el-table-column
          prop="applyEmpName"
          label="申请人员工姓名"
          width="120">
        </el-table-column>
        <el-table-column
          prop="applyDept"
          label="申请人所属部门"
          width="120">
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="申请时间"
          width="152">
        </el-table-column>
        <el-table-column
          width="152"
          prop="status"
          label="状态">
          <template slot-scope="scope">
            <span v-if="scope.row.status === 0" v-model="scope.row.status">已新建</span>
            <span v-if="scope.row.status === 1" v-model="scope.row.status">待审批</span>
            <span v-if="scope.row.status === 2" v-model="scope.row.status">审批通过</span>
            <span v-if="scope.row.status === 3" v-model="scope.row.status">审批拒绝</span>
          </template>
        </el-table-column>
        <el-table-column
          width="124"
          prop="operateEmp"
          label="操作人">
        </el-table-column>
        <el-table-column
          width="152"
          prop="operateTime"
          label="操作时间">
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
      <singleTree :treeData="treeData" v-on:checkInfo="getDepartInfo" @dialogClose="clearDepartInfo"></singleTree>
    </el-dialog>

  </el-row>
</template>

<script>
  import singleTree from '../../../components/tree/singleTree'
  import '../../../axios/api';

  export default {
    name: "role_approve_list",
    components:{
      singleTree,
    },
    data(){
      return{
        //从后台获取树状结构数据
        treeData:[],
        roleApprove:{
          applyCode:'',
          roleId:'',
          roleName:'',
          applyAccountLogin:'',
          applyEmpId:'',
          applyEmpName:'',
          applyDept:'',
          applyDeptName:'',
          empDepart:'',
          empDepartName:'',
          createTime: '',
          operateTime: ''
        },
        roleApproveForm:{
          applyCode:'',
          roleId:'',
          roleName:'',
          applyAccountLogin:'',
          applyEmpId:'',
          applyEmpName:'',
          applyDept:'',
          empDepart:'',
          empDepartName:'',
          applyTime: '',
          operateTime: '',
          applyTimeStart: '',
          applyTimeEnd: '',
          operateTimeStart: '',
          operateTimeEnd: ''
        },
        tableData: [],
        // 时间格式是这样的
        // applyTimePicker:[new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)]
        applyTimePicker: '',
        operateTimePicker: '',
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        multipleSelection: [],
        departDialogVisible: false,
        superDepartDialogVisible: false,
        checkId:'', //由子组件树状结构传来的部门id和名称
        checkVal:'',
        page:{
          total:10,
          currentPage:1,
          pageSize:10
        },
        buttonPermission:{}

      }
    },
    created:function(){
      this.search();
      this.getTreeData(); //初始化获取部门结构数据
      this.buttonPermission = JSON.parse(sessionStorage.getItem("buttonList")).roleApprove;
    },
    methods: {
      //从后台获取树状结构数据
      getTreeData() {
        //使用axios请求数据，将返回值赋给treeData，此处demo默认固定数据
        this.treeData=[{
          id: 1,
          label: '一级 1',
          children: [{
            id: 4,
            label: '二级 1-1',
            children: [{
              id: 9,
              label: '三级 1-1-1'
            }, {
              id: 10,
              label: '三级 1-1-2'
            }]
          }]
        }];
        this.$axios.get('/api/orgmanagement/dept/tree')
          .then(response=>{
            if (response.data.code === 200) {
              this.treeData = response.data.data;
            }else {
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error=>{
            this.$message('网络错误，不能访问，请联系管理员');
          })

      },
      search(type) {
        if (type !== 'page') {
          this.page.currentPage = 1;
        }
        if (this.applyTimePicker) {
          this.roleApproveForm.applyTimeStart = this.applyTimePicker[0] ? this.applyTimePicker[0] : null
          this.roleApproveForm.applyTimeEnd = this.applyTimePicker[1] ? this.applyTimePicker[1] : null
        } else {
          this.roleApproveForm.applyTimeStart = null
          this.roleApproveForm.applyTimeEnd = null
        }
        if (this.operateTimePicker) {
          this.roleApproveForm.operateTimeStart = this.operateTimePicker[0] ? this.operateTimePicker[0] : null;
          this.roleApproveForm.operateTimeEnd = this.operateTimePicker[1] ? this.operateTimePicker[1] : null
        } else {
          this.roleApproveForm.operateTimeStart = null;
          this.roleApproveForm.operateTimeEnd = null;
        }
        this.$axios.get('/api/orgmanagement/role/approve/getApproveList',
          {params:
              {
                pageNum:this.page.currentPage,
                pageSize:this.page.pageSize,
                roleApproveForm:this.roleApproveForm
              }
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response=>{
            if (response.data.code == 200) {
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
      // 清楚部门选择数据
      clearDepartInfo(val) {
        this.roleApproveForm.applyDept = '';
        // this.empSearchForm.empDepartName = '';
        this.departDialogVisible = val;
      },
      // 修改选中数据的集合
      handleSelectionChange(val) {
        this.multipleSelection = val;
        if (val.length === 0) return
        if (val.length > 1) {
          //清除所有选择项
          this.$refs.multipleTable.clearSelection();
          //勾选最后一个点击的项目
          this.multipleSelection = val.pop();
          this.$refs.multipleTable.toggleRowSelection(this.multipleSelection)
        } else {
          this.multipleSelection = val
        }
      },
      //  审核
      approve() {
        if (this.checkChoiceNum() ) {
            this.$router.push({path: '/role/approve/detail',query:{selectApplyCode:this.multipleSelection[0].applyCode , type:"update"}})
        }
      },
      //检查选中记录，修改、删除、离职、恢复都限制只能选中一条进行操作
      checkChoiceNum() {
        let msg = '';
        if (this.multipleSelection.length == 0){
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
      //从子组件中获取到选择的所属部门信息
      getDepartInfo(data) {
        // this.roleApproveForm.applyDept= data.split("#")[0];
        this.roleApproveForm.applyDept = data.split("#")[1];
        this.departDialogVisible = false
      },
      // 处理单元格点击事件,点击角色ID显示员工详情
      handleCellClick(row,column){
        if("角色申请编号" === column.label){
          this.$router.push(
            {
              path:'/role/apply/detail',
              query:{applyCode:row.applyCode}
            });
        }else if("申请角色ID" === column.label){
          this.$router.push(
            {path:'/role/management/roleDetail',
              query:{roleId:row.roleId}
            });
        }
      }
    }
  }
</script>

<style scoped>
  .el-input{
    width: 130px;
  }
  a {
    text-decoration:none;
  }
</style>
