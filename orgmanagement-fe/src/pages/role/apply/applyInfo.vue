<template>
  <div>
    <el-form ref="pageInfo" status-icon :model="pageInfo" label-width="150px" size="mini">
      <!--      角色信息-->
      <el-row>
        <el-col :span="20" class="margin-left20 font-title" style="margin-top: 10px">
          <h3 style="float: left; margin-right: 10px">角色信息</h3>
          <el-button style="margin-top: 10px;" type="primary" @click="save">保存</el-button>
          <el-button style="margin-top: 10px;" type="primary" @click="saveAndSubmit">保存并提交</el-button>
          <el-button style="margin-top: 10px;" type="primary" @click="cancle">取消</el-button>
        </el-col>
        <el-col :span="24">
          <el-row>
            <el-col :span="8">
              <el-form-item label="角色申请编号：">
                <el-input v-model="pageInfo.applyCode" clearable readonly placeholder="保存后自动生成"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="申请角色：" prop="roleName">
                <el-input readonly clearable v-model="pageInfo.roleName"></el-input>
                <el-button type="text" @click="selectRole">选择</el-button>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="角色审批人：">
                <el-input v-model="pageInfo.roleApproveName" readonly placeholder="请先选择申请角色"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="角色支持业务线：">
                <el-input v-model="pageInfo.bussinessLine" readonly placeholder="请先选择申请角色"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <!--      申请账号明细-->
      <el-row>
        <el-col :span="8" class="margin-left20 font-title">
          <h3 style="float: left;">申请账号明细</h3>
          <el-button style="margin-top: 10px; margin-left: 10px;" type="primary" @click="accountDialogVisible = true">
            添加
          </el-button>
        </el-col>
        <el-table
          ref="multipleTable"
          :data="accountData"
          :highlight-current-row="true"
          class="table—style" border>
          <el-table-column
            prop="accountLogin"
            label="申请账号"
            width="140">
          </el-table-column>
          <el-table-column
            prop="empName"
            label="关联员工姓名"
            width="140">
          </el-table-column>
          <el-table-column
            width="140"
            prop="empId"
            label="关联员工编号">
          </el-table-column>
          <el-table-column
            width="140"
            prop="deptName"
            label="关联员工所属部门">
          </el-table-column>
          <el-table-column
            width="190"
            prop="operateType"
            label="申请操作">
            <template slot-scope="scope">
              <el-select v-model="scope.row.operateType" @change="handleChange(scope.row,scope.$index,$event)"
                         placeholder="">
                <el-option label="添加" :value="1"></el-option>
                <el-option label="移除" :value="0"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column
            width="140"
            prop="operation"
            label="操作">
            <template slot-scope="scope">
              <!--              <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>-->
              <el-button
                @click.native.prevent="deleteRow(scope.$index, scope.row)"
                type="text"
                size="small">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>

      </el-row>
      <!--      其他信息-->
      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>其他信息</h3>
        </el-col>
        <el-col :span="24">
          <el-row>
            <el-col :span="8">
              <el-form-item label="申请时间：">
                <div class="block">
                  <el-date-picker readonly
                                  v-model="pageInfo.createTime"
                                  type="datetime"
                                  placeholder="暂无">
                  </el-date-picker>
                </div>

              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="申请人：">
                <el-input v-model="applyAccountName" readonly placeholder="暂无"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="修改时间：">
                <div class="block">
                  <el-date-picker readonly
                                  v-model="pageInfo.modifyTime"
                                  type="datetime"
                                  placeholder="暂无">
                  </el-date-picker>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="修改人：">
                <el-input v-model="pageInfo.modifyAccountName" readonly placeholder="暂无"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="审批时间：">
                <div class="block">
                  <el-date-picker readonly
                                  v-model="pageInfo.approveTime"
                                  type="datetime"
                                  placeholder="暂无">
                  </el-date-picker>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="审批人：">
                <el-input v-model="pageInfo.approveAccountName" readonly placeholder="暂无"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="15">
              <el-form-item label="状态：">
                <span style="margin-left: 16px" rows="4"
                      readonly>{{['已新建', '待审批', '审批通过','审批拒绝'][pageInfo.status]}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>

      <!--      角色选择弹窗-->
      <el-dialog ref="dialog" title="请选择申请角色" :visible.sync="roleDialogVisible" width="80%">
        <el-row class="list-container">
          <!--搜索条件-->
          <el-col>
            <el-form :inline="true" :model="role" label-width="120px">
              <el-form-item label="角色名称" style="display: block;">
                <el-input v-model="role.roleName" clearable></el-input>
                <!--操作按钮-->
                <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
              </el-form-item>
              <el-form-item style="margin-left: 6px">
                <el-button round type="primary" icon="el-icon-search"
                           @click="comfirm(roleSelection)">
                  确认选择
                </el-button>
                <el-button round type="primary" icon="el-icon-document" @click="roleDialogVisible = false">取消
                </el-button>
              </el-form-item>
            </el-form>
          </el-col>
          <!--角色弹窗展示列表-->
          <el-col class="margin-top20">
            <el-table
              ref="multipleTable"
              :data="tableData"
              :highlight-current-row="true"
              @row-click="rowClick"
              @selection-change="roleSelectionChange"
              class="table—style single-selection"  border>
              <el-table-column
                type="selection"
                label="操作"
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
                label="角色名称">
              </el-table-column>
              <el-table-column
                prop="approveAccount"
                label="审批人账号">
              </el-table-column>
              <el-table-column
                prop="approveId"
                label="审批人员工编号">
              </el-table-column>
              <el-table-column
                prop="approveName"
                label="审批人姓名">
              </el-table-column>
              <el-table-column
                prop="approveDepartment"
                label="审批人所属部门">
              </el-table-column>
              <el-table-column
                label="状态">
                <template slot-scope="scope">
                  <span v-if="scope.row.status === 1">有效</span>
                  <span v-else="scope.row.status === 0">无效</span>
                </template>
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
      </el-dialog>

      <!--      账号添加弹窗-->
      <el-dialog ref="dialog" title="请添加账号" :visible.sync="accountDialogVisible" width="80%">
        <addAccount v-on:checkInfo="accountComfirm" @dialogClose="childClose"
                    :parentVal="accountDialogVisible">
        </addAccount>
      </el-dialog>

      <!--      账号添加弹窗-->
      <el-dialog ref="dialog" title="账号错误" :visible.sync="accountMsgVisible" width="80%" append-to-body="">
          <span>账号错误</span>
      </el-dialog>
    </el-form>
  </div>


</template>

<script>
  import addAccount from '@/pages/role/apply/addAccount'
  // import addAccount from '@/pages/common/addAccount'
  import '@/axios/api'

  export default {
    name: "role_apply_applyInfo",
    components: {
      addAccount
    },
    data() {
      return {
        role: {
          roleId: '',
          roleName: '',
          approveAccount: '',
          approveId: '',
          approveName: '',
          approveDepart: '',
          status: '',
          description: ''
        },
        accountMsgVisible:false,
        pageInfo: {
          applyCode: '',
          roleId: '',
          roleName: '',
          roleApproveName: '',
          bussinessLine: '',
          createTime: '',
          applyEmpName: '',
          modifyTime: '',
          modifyEmp: '',
          modifyAccountName: '',
          approveTime: '',
          approveEmp: '',
          approveAccountName: '',
          operateEmp: '',
          operateTime: '',
          status: '',
          createEmp: '',
          applyAccountLogin: '',
          applyEmpId: ''
        },
        dateTimeNow: '',
        accountData: [
          // {
          // applyAccount: '',
          // empName: '',
          // empId: '',
          // empDept: '',
          // operateType: ''
          // }
        ],
        applyAccountName: '',
        tableData: [],
        page: {
          total: 10,
          currentPage: 1,
          pageSize: 10,
        },
        roleSelection: [{
          roleId: '',
          roleName: '',
          approveAccount: '',
          approveId: '',
          approveName: '',
          approveDepart: '',
          status: '',
          description: '',
          // businessLineName: '',
        }],
        currentUserInfo: {
          // accountLogin: '',
          // deptName: '',
          // empDepart: '',
          // empId: '',
          // empName: ''
        },
        accountSet: new Set(),
        accountDeleteSet: new Set(),
        operateType: '',
        // businessLineName: '',
        selectVal: [],
        treeData: [],
        accountDialogVisible: false,
        roleDialogVisible: false,
        isSave: false,
        // roleRules: {
        //   roleName: [
        //     {required: true, message: '请选择申请角色', trigger: 'blur'},
        //   ],
        // }
      }
    },
    created: function () {
      this.operateType = this.$route.query.type;
      let account = sessionStorage.getItem("userName");
      // 想要通过异步请求获取数据，但是created会继续执行，得到的数据是未定义的
      // this.getCurrentInfo(account);
      if ("update" === this.operateType) {
        this.pageInfo.applyCode = this.$route.query.applyCode;
        this.showApplyDetail(this.pageInfo.applyCode);
        // this.getModifyUserInfo(account);
      }

    },
    computed: {
      // 想要去掉无效的数据，但是分页中每页的数据就变少了，所以这个方法行不通，只能去后台处理
      // validRoleList: function () {
      //   return this.tableData.filter(item=> item.status === 1)
      // }
    },
    // 组件内导航钩子，处理未保存退出的情况
    beforeRouteLeave: function (to, from, next) {
      if (this.checkBeforeLeave()) {
        next();
      } else {
        next(false);
        this.$confirm('您还有信息未保存，确定离开当前页面吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 选择确定
          next()
        })
      }
    },
    methods: {
      save() {
        if (!this.checkBeforeSave()) {
          return;
        }
        this.isSave = true;
        if ("update" === this.operateType) {
          this.doUpdateApply();
        } else {
          this.doSaveApply();
        }
      },
      saveAndSubmit() {
        if (!this.checkBeforeSave()) {
          return;
        }
        this.pageInfo.status = 1;
        this.isSave = true;
        if (this.operateType === "update") {
          this.doUpdateApply()
        } else {
          this.doSaveApply();
        }
      },
      cancle() {
        this.$router.push('/role/apply')
      },
      deleteRow: function (index, row) {
        this.$confirm('确认删除该记录吗?', '提示', {
          type: 'warning'
        }).then(() => {
          this.accountDeleteSet.add(row.accountLogin);
          this.accountSet.delete(row.accountLogin)
          this.accountData.splice(index, 1)
          // this.showApplyDetail(this.pageInfo.applyCode)
        }).catch(() => {

        });
      },
      doSaveApply() {
        // this.pageInfo.createTime = new Date();
        this.$axios.post('/api/orgmanagement/role/apply/saveApply',
          {
            roleApply: JSON.stringify(this.pageInfo),
            roleApplyAccount: JSON.stringify(this.accountData)
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
            if (response.data.code === 200) {
              if (this.pageInfo.status === 1) {
                this.$message("保存并提交成功")
              } else {
                this.$message("保存成功")
              }
              this.$router.push('/role/apply')
            } else if (response.data.code === 701 || response.data.code === 702){
              this.$message({
                type: 'error',
                message: response.data.msg
              });
            }else {
              this.$message('保存失败');
            }
          })
          //获取失败
          .catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
      doUpdateApply() {
        this.pageInfo.modifyTime = new Date();
        console.log(this.accountData)
        this.$axios.post('/api/orgmanagement/role/apply/updateApply',
          {
            roleApply: JSON.stringify(this.pageInfo),
            roleApplyAccount: JSON.stringify(this.accountData),
            deleteAccount: JSON.stringify(this.accountDeleteSet)
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
            if (response.data.code === 200) {
              if (this.pageInfo.status === 1) {
                this.$message("保存并提交成功")
              } else {
                this.$message("保存成功")
              }
              this.$router.push('/role/apply')
            }else if(response.data.code === 701 || response.data.code === 702){
              this.$message({
                type: 'error',
                message: response.data.msg
              });
            } else {
              this.$message(response.data.msg);
              this.$router.go(-1);
            }
          })
          //获取失败
          .catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
      showApplyDetail(applyCode) {
        this.$axios.get('/api/orgmanagement/role/apply/showApplyDetail', {
          params: {
            applyCode: applyCode,
            date: new Date()
          }
        }).then(response => {
          if (response.data.code === 200) {
            let data = JSON.parse(response.data.data)
            console.log("accountData")
            console.log(data.accountData)
            this.pageInfo = data.pageInfo
            this.accountData = data.accountData
            this.applyAccountName = this.pageInfo.applyAccountLogin + '(' + this.pageInfo.applyEmpName + ')'
            for (let i = 0; i < this.accountData.length; i++) {
              this.accountSet.add(this.accountData[i].accountLogin)
            }
          } else {
            this.$message({
              type: 'error',
              message: response.data.msg
            });
          }
        }).catch(error => {
          this.$message('获取详情不能访问，请联系管理员');
        })
      },
      checkBeforeSave() {
        if (!this.pageInfo.roleName) {
          console.log("没有选择角色");
          this.$message({
            type: 'error',
            message: '请选择角色!'
          });
          return false;
        }
        if (this.accountData.length === 0) {
          console.log("没有添加账号");
          this.$message({
            type: 'error',
            message: '请添加账号!'
          });
          return false;
        }

        for (let i = 0; i < this.accountData.length; i++) {
          console.log(this.accountData[i].operateType)
          if (this.accountData[i].operateType == null) {
            this.$message({
              type: 'error',
              message: '请为添加的账号选择申请操作!'
            });
            return false;
          }
        }
        return true;
      },
      checkBeforeLeave() {
        if (!this.pageInfo.roleName && this.accountData.length === 0 || this.isSave) {
          return true;
        }
        return false;
      },
      handleChange(row, index, event) {
        // 修改row的数据后，accountData的数据可以相应改变
        row.operateType = event
        // 但是发现下拉框没有更新，所以定义一个中间变量赋值给accountData,让表格更新
        var temp = [];
        for (let i = 0; i < this.accountData.length; i++) {
          temp[i] = this.accountData[i]
        }
        temp[index] = row
        this.accountData = temp
      },
      childClose: function (val) {
        this.accountDialogVisible = val
      },
      accountComfirm: function (accountRow) {
        /*
         如果直接让this.accountData = accountRow,则这两个变量会指向同个对象
         当accountData删除一个数据之后，accountRow的数据也会变少，再次选择同样的数据后，会发现显示的数据数量减少
         */
        console.log(accountRow)
        this.$axios.post('/api/orgmanagement/role/apply/getAccountsInfo',
          {

            accounts: JSON.stringify(accountRow),
          }).then(response => {
          if (response.data.code == 200) {
            var temp = [];
            let accounts = response.data.data;
            console.log("确认获取的数据：")
            console.log(accounts)
            for (var i = 0; i < accounts.length; i++) {
              temp[i] = accounts[i];
              // temp[i].operateType = 1;
              if (this.accountSet.has(temp[i].accountLogin)) {
                this.$message(temp[i].accountLogin + "账号已经存在，不可重复添加")
                this.accountMsgVisible = true;
              } else {
                this.accountData.push(temp[i])
                this.accountSet.add(temp[i].accountLogin)
              }
            }
          } else if (response.data.code === 702) {
            this.$message({
              type: 'error',
              message: response.data.msg
            });
            // this.accountMsgVisible = true;

          } else {
            this.$message('稍后重试');
          }
        }).catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })

        accountRow = null;
      },
      comfirm: function (roleRow) {
        console.log(roleRow)
        this.$axios.post('/api/orgmanagement/role/apply/getRoleInfo',
          {
            role: JSON.stringify(roleRow[0]),
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
            if (response.data.code == 200) {
              this.pageInfo.roleId = response.data.data.role.roleId
              this.pageInfo.roleName = response.data.data.role.roleName
              this.pageInfo.roleApproveName = response.data.data.role.approveName

              this.pageInfo.bussinessLine = response.data.data.businessLineName
            } else if (response.data.code === 701) {
              this.$message({
                type: 'error',
                message: response.data.msg
              });
            } else {
              this.$message('稍后重试');
            }
          })
          //获取失败
          .catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })

        this.roleDialogVisible = false
      },
      addAccount: function (data) {
        this.account.empDepart = data.split("#")[0];
        this.account.empDepartName = data.split("#")[1];
      },
      deleteAccount(applyCode, account) {
        console.log("applyCode")
        console.log(applyCode)
        this.$axios.get('/api/orgmanagement/role/apply/deleteAccount',
          {
            params:
              {
                applyCode: applyCode,
                account: account,
                date: new Date(),
              }
          })
        //获取失败
          .catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
        console.log("进去删除方法")
      },
      selectRole() {
        this.roleDialogVisible = true
        this.search()
      },
      search() {
        this.$axios.get('/api/orgmanagement/role/apply/getValidRoleList',
          {
            params:
              {
                pageNum: this.page.currentPage,
                pageSize: this.page.pageSize,
                roleName: this.role.roleName,
                date: new Date(),
              }
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
            if (response.data.code == 200) {
              this.tableData = response.data.data.pageData;
              this.page.total = response.data.data.total;
            } else {
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
      getApplyCode() {
        this.$axios.get('/api/orgmanagement/role/apply/generateApplyCode',{
          params:{
            date: new Date(),
          }
        })
          .then(response => {
            if (response.data.code == 200) {
              this.pageInfo.applyCode = response.data.data
            } else {
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })

      },
      handleSizeChange(val) {
        this.page.pageSize = val;
        this.search()
      }
      ,
      //  显示第几页数据
      handleCurrentChange(val) {
        this.page.currentPage = val;
        this.search()
      }
      ,
      rowClick(row) {
        this.$refs.multipleTable.toggleRowSelection(row)
      }
      ,
      roleSelectionChange(row) {
        if (row.length === 0) return
        if (row.length > 1) {
          //清除所有选择项
          this.$refs.multipleTable.clearSelection();
          //勾选最后一个点击的项目
          this.roleSelection = row.pop();
          this.$refs.multipleTable.toggleRowSelection(this.roleSelection)
        } else {
          this.roleSelection = row
        }
      },

    }
  }
</script>

<style>
  div.single-selection th.el-table-column--selection div.cell {
    display: none;
  }
</style>
