<template>
  <el-row class="list-container" style="margin-top: 0px">
    <!--搜索条件-->
    <el-col>
      <el-form :inline="true" :model="empSearchForm" label-width="90px">
        <el-form-item label="员工编号">
          <el-input v-model="empSearchForm.empId" clearable></el-input>
        </el-form-item>
        <el-form-item label="员工姓名">
          <el-input v-model="empSearchForm.empName" clearable></el-input>
        </el-form-item>
        <!--<el-form-item label="是否离职">-->
          <!--<el-select v-model="empSearchForm.isDimission" placeholder="" class="select-width">-->
            <!--<el-option label="全部" value=""></el-option>-->
            <!--<el-option label="离职" value="1"></el-option>-->
            <!--<el-option label="在职" value="0"></el-option>-->
          <!--</el-select>-->
        <!--</el-form-item>-->
        <el-form-item label="所属部门" style="">
          <el-input v-model="empSearchForm.empDepartName"></el-input>
          <el-button @click="departDialogVisible = true">选择</el-button>
        </el-form-item>
        <el-form-item label="上级部门">
          <el-input v-model="empSearchForm.superDepartName"></el-input>
          <el-button @click="superDepartDialogVisible = true">选择</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--操作按钮-->
    <el-col :offset="1" >
      <el-button icon="el-icon-edit-outline" @click="confirm" type="primary" round>确认选择</el-button>
      <el-button icon="el-icon-edit-outline" @click="cancel" type="primary" round>取消</el-button>
      <el-button type="primary" icon="el-icon-search" @click="search" style="margin-left: 500px">查询</el-button>
    </el-col>
    <!--展示列表-->
    <el-col style="margin-top: 10px" >
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
          width="45">
        </el-table-column>
        <el-table-column
          prop="empId"
          label="员工编号"
          width="90">
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
          label="员工手机"
          width="110">
        </el-table-column>
        <el-table-column
          prop="empEmail"
          label="员工邮箱"
        width="150">
        </el-table-column>
        <el-table-column
          prop="deptName"
          label="所属部门"
        width="150">
        </el-table-column>
        <el-table-column
          prop="superDepartName"
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
    <el-dialog ref="dialog" title="请选择所属部门" :visible.sync="departDialogVisible" append-to-body>
      <singleTree :treeData="treeData" v-on:checkInfo="getDepartInfo" @dialogClose="clearDepartInfo"></singleTree>
    </el-dialog>

    <!--分配上级部门弹窗内容-->
    <el-dialog ref="dialog" title="请选择上级部门" :visible.sync="superDepartDialogVisible" append-to-body>
      <singleTree :treeData="treeData" v-on:checkInfo="getSuperDepartInfo" @dialogClose="clearSuperDepartInfo"></singleTree>
    </el-dialog>


  </el-row>
</template>

<script>
  import singleTree from '../../components/tree/singleTree';
  import '../../axios/api';

  export default {
    name: "selectEmployee1",
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
          superDepartName:'',
          date: new Date().getTime(),
        },
        empSearchForm:{
          empId:'',
          empName:'',
          accountLogin:'',
          isDimission:'',
          empDepart:'',
          empDepartName:'',
          superDepart:'',
          superDepartName:'',
          date: new Date().getTime(),
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
      // 从子组件中获取到选择的所属部门信息
      getDepartInfo(data) {
        this.empSearchForm.empDepart= data.split("#")[0];
        this.empSearchForm.empDepartName = data.split("#")[1];
        this.departDialogVisible = false
      },
      // 从子组件中获取到选择的上级部门信息
      getSuperDepartInfo(data) {
        this.empSearchForm.superDepart = data.split("#")[0];
        this.empSearchForm.superDepartName = data.split("#")[1];
        this.superDepartDialogVisible = false
      },
      // 清除选中部门
      clearDepartInfo(val){
        this.empSearchForm.empDepart = '';
        this.empSearchForm.empDepartName = '';
        this.departDialogVisible = val;
      },
      // 清除选择上级部门
      clearSuperDepartInfo(val){
        this.empSearchForm.superDepart = '';
        this.empSearchForm.superDepartName = '';
        this.superDepartDialogVisible = val;
      },
      search(type) {
        if (type !== 'page') {
          this.page.currentPage = 1;
        }
        this.$axios.get('/api/orgmanagement/employee/management/getEmployeeListOnly',
          {params:
              {
                pageNum:this.page.currentPage,
                pageSize:this.page.pageSize,
                empSearchForm:this.empSearchForm
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
      handleSelectionChange(row) {
        if (0 === row.length ) return;
        if (1 < row.length ) {
          //清除所有选择项
          this.$refs.multipleTable.clearSelection();
          //勾选最后一个点击的项目
          this.multipleSelection = row.pop();
          this.$refs.multipleTable.toggleRowSelection(this.multipleSelection)
        } else {
          this.multipleSelection = row
          if (this.multipleSelection[0]) {
            this.checkId = this.multipleSelection[0].empName;
          }
        }
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
      // 将选中的员工数据的信息填入账号编辑页面
      confirm() {
        let fillInInfo = this.multipleSelection;
        this.$emit("checkInfo", fillInInfo)
      },
      // 取消方法
      cancel() {
        this.dialogVisible = false
        if (this.dialogVisible === false) {
          this.$emit("checkInfo", false)
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
