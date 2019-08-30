<template>
  <el-row class="list-container">
    <!--搜索条件-->
    <el-col>
      <el-form :inline="true" :model="roleAccount" label-width="120px">
        <el-form-item label="登陆账号">
          <el-input v-model="roleAccount.accountLogin" clearable></el-input>
          <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--操作按钮-->
    <el-col :offset="1" :span="23" class="margin-top20">
      <el-button type="primary" round icon="el-icon-edit-outline" @click="submitRemove" :disabled="isEnabled">移除
      </el-button>
      <el-button type="primary" round icon="el-icon-edit-outline" @click="accountDialogVisible = true">添加</el-button>
      <el-button type="primary" round size="normal" @click="cancel">取消</el-button>
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
            <span v-if="scope.row.dataPermissionId === 0">全部</span>
            <span v-if="scope.row.dataPermissionId === 1">递归</span>
            <span v-if="scope.row.dataPermissionId === 2">本部门</span>
            <span v-if="scope.row.dataPermissionId === 3">本人</span>
            <span v-if="scope.row.dataPermissionId === 4">手动选择</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="status"
          label="账号状态">
          <template slot-scope="scope">
            <span v-if="scope.row.status === 2">冻结</span>
            <span v-if="scope.row.status === 1">正常</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="modifyTime"
          label="操作时间">
        </el-table-column>
        <el-table-column
          prop="modifyEmpName"
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
      <!--<div slot="footer" class="dialog-footer">-->
      <!--<el-button @click="departDialogVisible = false">取 消</el-button>-->
      <!--<el-button type="primary" @click="departDialogVisible = false">确 定</el-button>-->
      <!--</div>-->
    </el-dialog>
    <el-dialog ref="dialog" title="请添加账号" :visible.sync="accountDialogVisible" width="80%">
      <addAccount v-on:checkInfo="accountComfirm" @dialogClose="childClose"
                  :parentVal="accountDialogVisible"></addAccount>
    </el-dialog>

    <el-dialog
      title="提示"
      :visible.sync="deletedialogVisible"
      width="30%"
    >
      <span>{{showMsg1}}</span><br>
      <span>{{showMsg}}</span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="cancelDelete">取 消</el-button>
    <el-button type="primary" @click="ensure">确 定</el-button>
  </span>
    </el-dialog>

  </el-row>
</template>

<script>
  import singleTree from '@/components/tree/singleTree'
  import '@/axios/api'
  import addAccount from '@/pages/role/apply/addAccount'

  export default {
    name: "addRoleAccount",
     //props: ["parentVal"],
    components: {
      singleTree,
      addAccount
    },
    data() {
      return {
        //从后台获取树状结构数据
        treeData: [],
        //dialogVisible: this.parentVal,
        roleAccount: {
          accountLogin: '',
          roleId: '',
          accountLoginList: [],
        },
        accountData: [],
        account: {
          accountLogin: '',
          empId: '',
          empName: '',
          empDepart: '',
          deptName: '',
          dataPermissionId: '',
          status: '',
          createEmp: '',
          createTime: '',
          modifyEmpName: '',
          createEmpName: '',
          modifyTime: '',
          modifyEmp: '',
          isLinkEmp: '',
          roleId: ''
        },
        tableData: [{
          accountLogin: '',
          empId: '',
          empName: '',
          empDepart: '',
          deptName: '',
          dataPermissionId: '',
          empEmail: '',
          accountEmail: '',
          status: '',
          createEmp: '',
          createTime: '',
          modifyTime: '',
          modifyEmp: '',
          isLinkEmp: '',
        }],
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
        accountDialogVisible: false,
        isEnabled: '',
        showMsg: '',
        deletedialogVisible: false,
        showMsg1:'',
      }
    },
    created: function () {
      this.roleAccount.roleId = this.$route.query.roleId;
      this.roleAccount.roleName = this.$route.query.roleName;
      console.log(this.account.roleName);
      this.search();
      this.getTreeData(); //初始化获取部门结构数据
    },
    watch: {
      parentVal: function () {
        this.dialogVisible = this.parentVal;
      }
    },
    methods: {
      accountComfirm: function (accountRow) {
          //对选择后的list<>集合进行操作，可以判断
          console.log(accountRow)
          let temp = [];
          for (let i = 0; i < accountRow.length; i++) {
            temp[i] = accountRow[i].accountLogin;
            this.accountData.push(temp[i]);
          }
          //accountRow = null;
          // this.accountData = temp
          console.log("accountData");
          console.log(this.accountData);
          this.$axios.post('/api/orgmanagement/role/management/addRoleAccountList',
            {
              //accountLoginList: this.accountData,
              roleId: JSON.stringify(this.roleAccount.roleId),
              accounts: JSON.stringify(accountRow),
            })
          //then获取成功；response成功后的返回值（对象）
            .then(response => {
              if (response.data.code == 200) {
              //调用删除员工接口返回消息
              this.$message({
                type: 'success',
                message: '添加账号成功!'
              });
              //this.$router.push('/employee/list');
              this.search()
            }else if(response.data.code === 702){
                this.$message({
                  type: 'error',
                  message: response.data.msg
                });
              }else if(response.data.code === 703){
                this.$message({
                  type: 'error',
                  message: response.data.msg
                });
                this.$router.push('/role/management');
              } else {
                this.$message('稍后重试');
              }
            }).catch(() => {
            this.$message('网络错误，不能访问，请联系管理员');
          });
          this.accountDialogVisible = false;

      },
      childClose: function (val) {
        this.accountDialogVisible = val
      },

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
      //获取当前角色的账号  ，记得加上账号和角色ID的条件
      search() {
        console.log(this.roleAccount);
        this.$axios.post('/api/orgmanagement/role/management/getRoleAccountList',
          {
            pageNum: this.page.currentPage,
            pageSize: this.page.pageSize,
            roleAccount: this.roleAccount
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
            if (response.data.code === 200) {
              this.tableData = response.data.data.pageData;
              console.log(this.tableData);
              this.page.total = response.data.data.total;

            } else {
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error => {
            this.$message('，请联系管理员');
          })
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
        if (this.multipleSelection.length === 0) {
          this.isEnabled = true;
        } else {
          this.isEnabled = false;
        }
      },
      checkChoiceNum() {
        let msg = '';
        if (this.multipleSelection.length === 0) {
          msg = '请选中一条记录进行操作！';
          //弹窗提示
          this.$alert(msg, '提示', {
            confirmButtonText: '确定',
          });
          return false
        } else {
          return true
        }
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
      cancel() {
        this.$router.push('/role/management');
      },
      //点击确定
      ensure() {
        this.doSubmitRemove();
        this.deletedialogVisible = false;
      },
      //取消删除
      cancelDelete() {
        this.deletedialogVisible = false;
        this.roleAccount.accountLoginList = [];
      },
      //删除角色的账号
      doSubmitRemove() {
        this.$axios.post('/api/orgmanagement/role/management/deleteRoleAccountList',
          {
            accountLoginList: this.roleAccount.accountLoginList,
            roleId: this.roleAccount.roleId
            /*roleId: JSON.stringify(this.roleAccount.roleId),
            accounts: JSON.stringify(this.multipleSelection),*/
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
              if (response.data.code == 200) {
                this.$message({
                  type: 'success',
                  message: '删除成功，请刷新页面!'
                });
                this.roleAccount.accountLoginList = [];
                this.search();
              } else {
                this.$message({
                  type: 'error',
                  message: response.data.msg
                });
                this.$router.push('/role/management');
              }
            },
          )
          //获取失败
          .catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
      // 提交移除
      submitRemove() {
        this.deletedialogVisible = true;
        let accounts = '';
        for (let i = 0; i < this.multipleSelection.length; i++) {
          accounts = accounts + `<el-col :span="20" class="margin-left20 font-title">` + this.multipleSelection[i].accountLogin + `</el-col>`
          this.roleAccount.accountLoginList.push(this.multipleSelection[i].accountLogin);
        }
        this.showMsg1 = '确定要将下列账号从角色【' + this.roleAccount.roleName + '】角色中移除?'
        this.showMsg = this.roleAccount.accountLoginList ;
      },
    },

  }

</script>

<style scoped>
  th.el-table-column--selection div.cell {
    display: none;
  }

  .el-message-box {
    width: 700px;
  }
</style>
