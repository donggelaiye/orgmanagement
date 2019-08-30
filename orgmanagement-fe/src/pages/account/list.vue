<template>
  <el-row class="list-container">
    <!--搜索条件-->
    <el-col >
      <el-form :inline="true" :model="account" class="lab">
        <el-form-item label="登录账号">
          <el-input  v-model="account.accountLogin" clearable></el-input>
        </el-form-item>
        <el-form-item label="员工编号">
          <el-input v-model="account.empId" clearable ></el-input>
        </el-form-item>
        <el-form-item label="员工姓名">
          <el-input v-model="account.empName" clearable></el-input>
        </el-form-item>
        <el-form-item label="数据权限类型" label-width="110px">
          <el-select  placeholder="" class="select-width" v-model="account.dataPermissionId">
            <el-option label="全部" value="0"></el-option>
            <el-option label="递归" value="1"></el-option>
            <el-option label="本部门" value="2"></el-option>
            <el-option label="本人" value="3"></el-option>
            <el-option label="手动选择" value="4"></el-option>
            <el-option label="全选" value=""></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="员工所属部门">
          <el-input v-model="account.deptName" clearable></el-input>
          <el-button @click="departDialogVisible = true">选择</el-button>
        </el-form-item>
        <el-form-item label="是否关联员工">
          <el-select placeholder="" class="select-width" v-model="account.isLinkEmp">
            <el-option label="不关联" value="0"></el-option>
            <el-option label="关联" value="1"></el-option>
            <el-option label="全选" value=""></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="账号状态">
          <el-select placeholder="" class="select-width" v-model="account.status">
            <el-option label="正常" value="1"></el-option>
            <el-option label="冻结" value="0"></el-option>
            <el-option label="无效" value="2"></el-option>
            <el-option label="全选" value=""></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </el-col>
    <!--操作按钮-->
    <el-col :offset="19" :span="5">
      <el-button v-if="buttonPermission.indexOf('getList') > -1" type="primary" icon="el-icon-search" @click="search">查询</el-button>
      <el-button v-if="buttonPermission.indexOf('exportAccount') > -1" type="primary" icon="el-icon-document" @click="exportAcc" >导出</el-button>
    </el-col>
    <el-col :offset="1" :span="23" class="margin-top20">

      <el-button v-if="buttonPermission.indexOf('addAccount') > -1" type="primary" icon="el-icon-edit-outline" @click="createAcc" :disabled="isBtnCreate" round>新增</el-button>
      <el-button v-if="buttonPermission.indexOf('modifyAccount') > -1" icon="el-icon-edit-outline" type="primary" @click="updateAcc" :disabled="isBtnUpdate" round>修改</el-button>
      <el-button v-if="buttonPermission.indexOf('deleteAccount') > -1" icon="el-icon-edit-outline" type="primary" @click="deleteAcc" :disabled="isBtnDelete" round>删除</el-button>
      <el-button v-if="buttonPermission.indexOf('freezeAccount') > -1" icon="el-icon-edit-outline" type="primary" @click="freezeAcc" :disabled="isBtnFreeze" round>冻结</el-button>
      <el-button v-if="buttonPermission.indexOf('unfreezeAccount') > -1" icon="el-icon-edit-outline" type="primary" @click="unfreezeAcc" :disabled="isBtnUnfreeze" round>解冻</el-button>
      <el-button v-if="buttonPermission.indexOf('sendResetPwdMail') > -1" icon="el-icon-edit-outline" type="primary" @click="resetPassword" :disabled="isBtnPassword" round>密码重置</el-button>
      <el-button v-if="buttonPermission.indexOf('grantPermission') > -1" icon="el-icon-edit-outline" type="primary" @click="distributeAuth":disabled="isBtnDistribute" round>分配权限</el-button>
      <el-button v-if="buttonPermission.indexOf('selectAllHistory') > -1" icon="el-icon-edit-outline" type="primary" @click="AccHistory"  round>历史记录</el-button>
    </el-col>

    <el-col class="margin-top20">
      <el-table
        v-loading="isLoading"
        ref="multipleTable"
        :data="tableData"
        :highlight-current-row="true"
        @selection-change="handleSelectionChange"
        @cell-click = "handleCellClick"
        class="table—style" border>
        <el-table-column
          type="selection"
          prop="id"
          width="55">
        </el-table-column>
        <el-table-column
          prop="accountLogin"
          label="登录账号"
          width="120">
          <template slot-scope="scope">
            <el-link type="primary">{{scope.row.accountLogin}}</el-link>
          </template>
        </el-table-column>
        <el-table-column
          prop="empId"
          label="员工编号"
          width="100">
        </el-table-column>
        <el-table-column
          prop="empName"
          label="员工姓名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="deptName"
          label="员工所属部门"
          width="120">
        </el-table-column>
        <el-table-column
          prop="dataPermissionId"
          label="数据权限类型"
          width="120">
          <template slot-scope="scope">
            <span v-if="scope.row.dataPermissionId === 0">全部</span>
            <span v-else-if="scope.row.dataPermissionId === 1">递归</span>
            <span v-else-if="scope.row.dataPermissionId === 2">本部门</span>
            <span v-else-if="scope.row.dataPermissionId === 3">本人</span>
            <span v-else="scope.row.dataPermissionId === 4">手动选择</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="status"
          label="账号状态"
          width="100">
          <template slot-scope="scope">
            <span v-if="scope.row.status === 0">冻结</span>
            <span v-else-if="scope.row.status === 1">正常</span>
            <span v-else="scope.row.status===2">无效</span>
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
    <el-dialog ref="dialog" title="请选择所属部门" :visible.sync="departDialogVisible">
      <singleTree :treeData="treeData" v-on:checkInfo="getDepartInfo" @dialogClose="clearDepartInfo"></singleTree>
    </el-dialog>

    <!--账号详细信息-->
    <el-dialog ref="dialog" title="账号详细信息" :visible.sync="detailDialogVisible">
      <el-form ref="account" status-icon  label-width="120px" size="mini">
        <el-row>
          <el-col :span="20" class="margin-left20 font-title">
            <h3>账号信息</h3>
          </el-col>
          <el-col :span="24">
            <el-row>
              <el-col :span="12">
                <el-form-item label="登录账号：">
                  <el-input v-model="Data.accountLogin" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="关联员工编号：" >
                  <el-input v-model="Data.empId" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="员工姓名：">
                  <el-input v-model="Data.empName" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="员工所属部门：">
                  <el-input v-model="Data.deptName" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="数据权限类型：" >
                    <template  v-model="Data.dataPermissionId" class="select-width">
                      <span v-if="Data.dataPermissionId === 0">全部</span>
                      <span v-else-if="Data.dataPermissionId === 1">递归</span>
                      <span v-else-if="Data.dataPermissionId === 2">本部门</span>
                      <span v-else-if="Data.dataPermissionId === 3">本人</span>
                      <span v-else-if="Data.dataPermissionId === 4">手动选择</span>
                    </template>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="密保邮箱：" >
                  <el-input v-model="Data.accountEmail" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20" class="margin-left20 font-title">
            <h3>其他信息</h3>
          </el-col>
          <el-col :span="24">
            <el-row>
              <el-col :span="10">
                <el-form-item label="新建时间：">
                  <el-input v-model="Data.createTime" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="新建人：" >
                  <el-input v-model="Data.createEmpName" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="修改时间：" >
                  <el-input v-model="Data.modifyTime" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="修改人：">
                  <el-input v-model="Data.modifyEmpName" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="20">
                <el-form-item label="备注：" >
                  <el-input type="textarea" rows="4" v-model="Data.remark" maxlength="200" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>

    <!--导出对话框-->
    <el-dialog ref="exportDialog" title="请选择导出条件" :visible.sync="exportDialogVisible" @close="closeExportDialog">
      <exportFile v-on:checkInfo = "getCheckList" :checkOptions = "roleOptions" :sonChecked="sonChecked"></exportFile>
      <el-row>
        <el-col :offset="10" :span="14" class="margin-top20">
          <el-button icon="el-icon-edit-outline" @click="selectAll" round>
            <span v-if="isAll">全选</span>
            <span v-else>取消全选</span>
          </el-button>
          <el-button icon="el-icon-edit-outline" @click="confirm" round>确定导出</el-button>
          <el-button icon="el-icon-edit-outline" @click="dialogClose" round>取消</el-button>
        </el-col>
      </el-row>
    </el-dialog>

  </el-row>


</template>

<script>

  import singleTree from '../../components/tree/singleTree';
  import '@/axios/api';
  import exportFile from '@/pages/common/exportFile';

    export default {

        name: "account_list",
        components:{
          singleTree,
          exportFile
        },

      data(){
          return{
            account:{
              accountLogin:'',
              empId:'',
              empName:'',
              empDepart:'',
              deptName:'',
              dataPermissionId:'',
              status:'',
              createEmp: '',
              createTime: '',
              modifyEmpName:'',
              createEmpName: '',
              modifyTime:'',
              modifyEmp:'',
              isLinkEmp:'',
              date: new Date().getTime(),
            },

            tableData: [{
              accountLogin:'',
              empId:'',
              empName:'',
              empDepart:'',
              deptName:'',
              dataPermissionId:'',
              empEmail:'',
              accountEmail:'',
              status:'',
              createEmp: '',
              createTime: '',
              modifyTime:'',
              modifyEmpName:'',
              createEmpName: '',
              modifyEmp:'',
              isLinkEmp:'',
              date: new Date().getTime(),
              },
          ],
            //账号详细信息
            Data: {
              accountLogin:'',
              empId:'',
              empName:'',
              empDepart:'',
              deptName:'',
              dataPermissionId:'',
              empEmail:'',
              accountEmail:'',
              status:'',
              createEmp: '',
              createTime: '',
              modifyEmpName:'',
              createEmpName: '',
              modifyTime:'',
              modifyEmp:'',
              isLinkEmp:'',
              date: new Date().getTime(),
            },
            multipleSelection: [],
            departDialogVisible: false,
            detailDialogVisible:false,
            superDepartDialogVisible: false,
            checkId:'', //获取选中的账号id
            checkVal:'',
            page:{
              total:10,
              currentPage:1,
              pageSize:10
            },
            treeData:[],
            exportDialogVisible:false,
            roleOptions:['登录账号','员工编号','员工姓名','员工所属部门','数据权限类型','账号状态','操作时间','操作人'],
            checkedList:[],
            sonChecked:[],
            exportData: [],
            isAll:true,
            buttonPermission: [], //控制按钮权限
            // 按钮控制判断变量
            isBtnCreate: false,
            isBtnUpdate: true,
            isBtnDelete: true,
            isBtnFreeze: true,
            isBtnUnfreeze: true,
            isBtnDistribute: true,
            isBtnPassword:true,
            isLoading: false
        }
      },

      created:function(){
        this.search();
        this.getTreeData();
        this.buttonPermission = JSON.parse(sessionStorage.getItem("buttonList")).account;
      },

      methods: {
          //关闭导出
        closeExportDialog()
        {
          this.isAll = true;
        },

         // 从后台获取部门树状结构数据
         getTreeData() {
           if (sessionStorage.getItem('deptTree')) {
             this.treeData = JSON.parse(sessionStorage.getItem('deptTree'));
           }
         },

        //清除选中部门
        clearDepartInfo(val){
          this.account.empDepart = '';
          this.account.deptName = '';
          this.departDialogVisible = val;
        },

         // 从子组件中获取到选择的所属部门信息
         getDepartInfo(data) {
           console.log("树的参数"+data);
           this.account.empDepart= data.split("#")[0];
           this.account.deptName = data.split("#")[1];
           this.departDialogVisible = false;
         },

        //查询
        search(type) {
          if (type !== 'page') {
            this.page.currentPage = 1;
          }
          this.isLoading = true;
          this.$axios.get('/api/orgmanagement/account/management/getAccountList',
            { params:
                {
                  pageNum:this.page.currentPage,
                  pageSize:this.page.pageSize,
                  account:this.account,
                  date: new Date().getTime()
                }
            })
          //then获取成功；response成功后的返回值（对象）
            .then(response=>{
            if (response.data.code == 200) {
              this.tableData = response.data.data.pageData;
              this.page.total = response.data.data.total;
              this.isLoading = false;
            }else {
              this.isLoading = false;
              this.$message(response.data.msg);
            }
          })
            //获取失败
            .catch(error=>{
              this.$message('网络错误，不能访问，请联系管理员');
            })
        },
        // 修改选中的数据
        handleSelectionChange(row) {
          this.checkBtnAuthority(row);
          if (0 === row.length ) return;
          if (1 < row.length ) {
            //清除所有选择项
            this.$refs.multipleTable.clearSelection();
            //勾选最后一个点击的项目
            this.multipleSelection = row.pop();
            this.$refs.multipleTable.toggleRowSelection(this.multipleSelection)
          } else {
            this.multipleSelection = row;
            if (this.multipleSelection[0]) {
              this.checkId = this.multipleSelection[0].accountLogin;
            }
          }
        },

        // 处理按钮权限
        checkBtnAuthority(row){
          if (0 === row.length ) {
            this.isBtnUpdate = true;
            this.isBtnDelete = true;
            this.isBtnFreeze = true;
            this.isBtnUnfreeze = true;
            this.isBtnDistribute =true;
            this.isBtnPassword = true;
          }else {
            this.isBtnDelete = false;
          }
          if(row[0].status ===0) {
            this.isBtnFreeze = true;
            this.isBtnUnfreeze = false;
            this.isBtnUpdate = false;
            this.isBtnDelete = false;
            this.isBtnDistribute =false;
            this.isBtnPassword = false;
          }
          else if(row[0].status ===1){
            this.isBtnFreeze = false;
            this.isBtnUnfreeze = true;
            this.isBtnUpdate = false;
            this.isBtnDelete = false;
            this.isBtnDistribute =false;
            this.isBtnPassword = false;
          }
          else {
            this.isBtnFreeze = true;
            this.isBtnUnfreeze = true;
            this.isBtnUpdate = true;
            this.isBtnDelete = true;
            this.isBtnDistribute =true;
            this.isBtnPassword = true;
          }
        },

        // 处理单元格点击事件,点击员工ID显示员工详情
        handleCellClick(row,column){
          console.log(row);
          if("登录账号" === column.label){
            this.detailDialogVisible = true ;
            this.$axios.get('/api/orgmanagement/account/management/getAccountSelectByLogin', {
              params: { accountLogin: row.accountLogin,date: new Date().getTime() }
            })
              .then(response=>{
                if (response.data.code == 200) {
                  console.log("data传值");
                  console.log(response.data.data);
                  this.Data = response.data.data;

                }else {
                  this.$message(response.data.msg);
                }
              })
          }
        },

        //  新增
        createAcc() {
          this.$router.push('/account/detail')
        },

        //导出
        exportAcc(){
          this.exportDialogVisible = true;
          this.sonChecked = []; //控制第二次弹窗时清除数据
        },

        //删除
        deleteAcc(){
          this.$confirm('确定删除账号'+this.checkId+'？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            //type: 'warning'
          }).then(() => {
            this.$axios.get('/api/orgmanagement/account/management/deleteAccount',
                { params:
                    {
                      accountLogin:this.multipleSelection[0].accountLogin,
                      date: new Date().getTime()
                    }
                }).then(response =>{
              if (response.data.code === 200){
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                });
                this.search();
              } else {
                this.$message.error(response.data.msg);
              }
            })
              //获取失败
              .catch(error=>{
                this.$message('网络错误，不能访问，请联系管理员');
              })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        },

        //冻结
        freezeAcc(){
          this.$confirm('确定将账号'+this.checkId+'冻结？', '提示', {
            cancelButtonText: '取消',
            confirmButtonText: '确定',
            //type: 'warning'
          }).then(() => {
            this.$axios.get('/api/orgmanagement/account/management/FreezeAccount',
              { params:
                  {
                    accountLogin:this.multipleSelection[0].accountLogin,
                    date: new Date().getTime()
                  }
              }).then(response =>{
              if (response.data.code === 200){
                this.$message({
                  type: 'success',
                  message: '冻结成功!'
                });
                this.search();
              } else {
                this.$message.error(response.data.msg);
              }
              })
            //获取失败
              .catch(error=>{
                this.$message('网络错误，不能访问，请联系管理员');
              })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消冻结'
            });
          });
        },

        //解冻
        unfreezeAcc(){
          this.$confirm('确定将账号'+ this.checkId +'解冻？', '提示', {
            cancelButtonText: '取消',
            confirmButtonText: '确定',
            //type: 'warning'
          }).then(() => {
            this.$axios.get('/api/orgmanagement/account/management/UnfreezeAccount',
              { params:
                  {
                    accountLogin:this.multipleSelection[0].accountLogin,
                    date: new Date().getTime()
                  }
              }).then(response=>{
                if (response.data.code === 200){
                  this.$message({
                    type: 'success',
                    message: '解冻成功!'
                  });
                  this.search();
                } else {
                  this.$message.error(response.data.msg);
                }
            }).catch(error=>{
                this.$message('网络错误，不能访问，请联系管理员');
              })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消解冻'
            });
          });
        },

        //历史记录
        AccHistory(){
          this.$router.push('/account/history')
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

        //修改
        updateAcc(){
          if (this.checkChoiceNum() ) {
            //跳转
            this.$router.push({
              path:'/account/modification',
              query: {
                accountLogin:this.multipleSelection[0].accountLogin
              }
            })
          }
        },

        //密码重置
        resetPassword(){
          this.$confirm('是否向账号'+ this.multipleSelection[0].accountLogin +'发送密码重置邮件？', '提示', {
            cancelButtonText: '取消',
            confirmButtonText: '确定',
          }).then(() => {
            this.$axios.get('/api/orgmanagement/personal/management/sendResetPwdMail',
              { params:
                  {
                    accountLogin:this.multipleSelection[0].accountLogin
                  }
              }).then(response => {
                if (response.data.code === 200){
                  this.$message.success('发送成功');
                } else {
                  this.$message.error(response.data.msg)
                }
            })
            //获取失败
              .catch(error=>{
                this.$message('网络错误，不能访问，请联系管理员');
              })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消发送'
            });
          });

        },

        //分配权限
        distributeAuth(){
          if (this.checkChoiceNum() ) {
            //跳转
            this.$router.push({
              path:'/account/permission',
              query: {
                accountLogin:this.multipleSelection[0].accountLogin}
            })
          }
        },

        handleSizeChange(val) {
          this.page.pageSize = val;
          this.search('page')
        },

        //  显示第几页数据
        handleCurrentChange(val) {
          this.page.currentPage = val;
          this.search('page')
        },

        //导出弹出窗口
        fileExport(){
          this.exportDialogVisible = true;
          this.sonChecked = []; //控制第二次弹窗时清除数据
        },

        //获取导出数据
        generate(){

        },

        //从子窗口获取选中的字段
        getCheckList(val){
          this.checkedList = val;
        },

        //确认导出
        confirm(){
          this.$axios.get('/api/orgmanagement/account/management/exportAccountList',
            {params:
                {
                  account:this.account
                }
            })
          //then获取成功；response成功后的返回值（对象）
            .then(response=>{
              if (response.data.code === 200) {
                this.exportData= response.data.data;
                this.writeFile();

              }else {
                this.$message(response.data.msg);
              }
            })
            //获取失败
            .catch(error=>{
              this.$message('网络错误，不能访问，请联系管理员');
            })
        },
        formatJson(filterVal,jsonData){
          return jsonData.map(v => filterVal.map(j => v[j]))
        },

        //全选
        selectAll(){
          if (this.isAll) {
            this.sonChecked = this.roleOptions;
            this.isAll = false;
          }else {
            this.sonChecked = [];
            this.isAll = true;
          }
        },

        //导出窗口关闭
        dialogClose(){
          this.exportDialogVisible = false;
          this.isAll = true;
        },

        //确认导出
        writeFile() {
            this.exportDialogVisible = false;
          let checked = this.checkedList;
          let array = new Array();
          //选中字段则导出，否则提示选择
          if (checked.length > 0){
            for(let i = 0; i < checked.length; i++){
              switch(checked[i]){
                case "登录账号": array[i] = "accountLogin";break;
                case "员工编号": array[i] = "empId";break;
                case "员工姓名": array[i] = "empName";break;
                case "员工所属部门": array[i] = "deptName";break;
                case "数据权限类型": array[i] = "dataPermissionId";break;
                case "账号状态": array[i] = "status";break;
                case "操作时间": array[i] = "createTime";break;
                case "操作人": array[i] = "createEmpName";break;
              }
            }
            //excel数据导出
            require.ensure([], () => {
              const {
                export_json_to_excel
              } = require('../../excel/Export2Excel');
              const tHeader = checked;
              const filterVal = array;
              const list = this.generate(this.exportData);
              const data = this.formatJson(filterVal, list);

              const {
                exportDate1
              } = require('../../constant/exportDate');
              let excelName = checked.toString()+exportDate1();

              //let excelName = checked.toString() + new Date().toLocaleDateString();
              export_json_to_excel(tHeader, data, excelName);
              this.$message('导出成功，请关注下载文件！');
              console.log("导出文件");
              console.log(list);
            })
          }else {
            this.$message('请选择要导出的字段！');
          }
        },

        //整理导出字段（数值赋值给文字）
        //获取导出数据
        generate(data){
            if(data.length>0)
            {
              for(let i =0;i<data.length;i++)
              {
                if(data[i].status === 0)
                {
                  data[i].status ="冻结";
                }
                else if(data[i].status ===1)
                {
                  data[i].status = "正常";
                }
                else data[i].status = "无效";
                if(data[i].dataPermissionId === 0)
                {
                  data[i].dataPermissionId = "全部权限";
                }
               else if(data[i].dataPermissionId === 1)
                {
                  data[i].dataPermissionId = "递归权限";
                }
                else if(data[i].dataPermissionId === 2)
                {
                  data[i].dataPermissionId = "本部门权限";
                }
                else if(data[i].dataPermissionId === 3)
                {
                  data[i].dataPermissionId = "本人权限";
                }
                else if(data[i].dataPermissionId === 4)
                {
                  data[i].dataPermissionId = "手动选择权限";
                }
              }
            }
            return data;
        },
      }
    }

</script>

<style scoped>
  .lab {
    margin-left: 7px;
  }


</style>
