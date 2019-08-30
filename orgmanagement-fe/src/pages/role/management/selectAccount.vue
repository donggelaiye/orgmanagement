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
          <el-input v-model="account.empDepartName"></el-input>
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
            <el-option label="正常" value="1"></el-option>
            <el-option label="冻结" value="0"></el-option>
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
      <el-button icon="el-icon-edit-outline" type="primary" @click="confirm" round>确认选择</el-button>
      <el-button icon="el-icon-edit-outline" type="primary" @click="cancel" round>取消</el-button>
    </el-col>
    <!--展示列表-->
    <el-col class="margin-top20">
      <el-table
        ref="multipleTable"
        v-loading="listLoading"
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
    <el-dialog ref="dialog" title="请选择员工所属部门" :visible.sync="departDialogVisible" append-to-body>
      <singleTree :treeData="treeData" v-on:checkInfo="getDepartInfo" @dialogClose="clearDepartInfo"></singleTree>
    </el-dialog>

  </el-row>
</template>

<script>
  import singleTree from '@/components/tree/singleTree'
  import '@/axios/api'

  export default {
    name: "selectAccount",
    props: ["parentVal"],
    components: {
      singleTree
    },
    data() {
      return {
        //从后台获取树状结构数据
        treeData: [],
        dialogVisible: this.parentVal,
        account: {
          accountLogin: '',
          empId: '',
          empName: '',
          empDepart: '',
          dataPermissionId: '',
          status: '',
          modifyTime: '',
          modifyEmp: '',
          isLinkEmp: ''
        },
        tableData: [],
        multipleSelection: [],
        departDialogVisible: false,
        superDepartDialogVisible: false,
        checkId: '', //由子组件树状结构传来的部门id和名称
        checkVal: '',
        page: {
          total: 10,
          currentPage: 1,
          pageSize: 10
        },
        listLoading: false,
      }
    },
    created: function () {
      this.search();
      this.getTreeData(); //初始化获取部门结构数据
    },
    watch: {
      parentVal: function () {
        this.dialogVisible = this.parentVal;
      }
    },
    methods: {
      //从后台获取树状结构数据
      getTreeData() {
        this.$axios.get('/api/orgmanagement/dept/tree')
          .then(response => {
            if (response.data.code === 200) {
              this.treeData = response.data.data;
            } else {
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
      search() {
        if (this.account.empDepartName === '') {
          this.account.empDepart = null;
        }
        this.listLoading = true
        this.$axios.get('/api/orgmanagement/account/management/getValidAccountList',
          {
            params:
              {
                pageNum: this.page.currentPage,
                pageSize: this.page.pageSize,
                account: this.account
              }
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
            if (response.data.code === 200) {
              this.tableData = response.data.data.pageData;
              this.page.total = response.data.data.total;
              this.listLoading = false;
            } else {
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
        if (1 < val.length) {
          //清除所有选择项
          this.$refs.multipleTable.clearSelection();
          //勾选最后一个点击的项目
          this.multipleSelection = val.pop();
          this.$refs.multipleTable.toggleRowSelection(this.multipleSelection)
        } else {
          this.multipleSelection = val
        }
      },
      checkChoiceNum() {
        let msg = '';
        if (this.multipleSelection.length == 0) {
          msg = '请选中一条记录进行操作！';
          //弹窗提示
          this.$alert(msg, '提示', {
            confirmButtonText: '确定',
          });
          return false
        } else if (this.multipleSelection.length > 1) {
          msg = '只能选中一条记录进行操作!';
          this.$alert(msg, '提示', {
            confirmButtonText: '确定',
          });
          return false
        } else {
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
        this.search()
      },
      //  显示第几页数据
      handleCurrentChange(val) {
        this.page.currentPage = val;
        this.search()
      },
      //从子组件中获取到选择的所属部门信息
      getDepartInfo(data) {
        this.account.empDepart = data.split("#")[0];
        this.account.empDepartName = data.split("#")[1];
        this.departDialogVisible = false
      },
      // 将选中的账号数据的信息填入角色申请新建页面
      confirm() {
        if (this.checkChoiceNum()) {
          let fillInInfo = this.multipleSelection[0];
          //判断审批人账号是否被删除
          // 不是修改操作或角色名称已经改变
          this.$axios.get('/api/orgmanagement/role/management/checkApproveAccount', {
            params: {approveAccount:this.multipleSelection[0].accountLogin }
          }).then(response => {
            if (0 === response.data.data) {
              this.$emit("checkInfo", fillInInfo);
              this.cancel();
            } else {
              this.$message('审批人账号已被删除，请重新选择');
            }
          }).catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
        }
      },
    cancel() {
      this.dialogVisible = false
      if (this.dialogVisible === false) {
        this.$emit("dialogClose", false)
      }
    },
    // 清除选中部门
    clearDepartInfo(val) {
      this.account.empDepartName = '';
      this.account.empDepart = '';
      this.departDialogVisible = val;
    },
  }
  }
</script>

<style scoped>

</style>
