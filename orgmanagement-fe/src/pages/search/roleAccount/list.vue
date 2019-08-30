<template>
  <el-row class="list-container">
    <!--搜索条件-->
    <el-col>
        <el-form :inline="true" :model="roleAccountDetail" label-width="100px">
          <el-form-item label="角色名称">
            <el-input v-model="roleAccountDetail.roleName" clearable></el-input>
          </el-form-item>
          <el-form-item label="支持业务线">
            <el-select v-model="roleAccountDetail.bussinessLineName" placeholder="" class="select-width" clearable>
              <el-option label="全部" value=""></el-option>
              <el-option label="买买车" value="买买车"></el-option>
              <el-option label="闪贷" value="闪贷"></el-option>
              <el-option label="租车" value="租车"></el-option>
              <el-option label="专车" value="专车"></el-option>
              <el-option label="保险" value="保险"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="登录账号">
            <el-input v-model="roleAccountDetail.accountLogin" clearable></el-input>
          </el-form-item>
          <el-form-item label="员工编号">
            <el-input v-model="roleAccountDetail.empId" clearable></el-input>
          </el-form-item>
          <el-form-item label="员工姓名">
              <el-input v-model="roleAccountDetail.empName" clearable></el-input>
          </el-form-item>
          <el-form-item label="员工所属部门">
            <el-input v-model="roleAccountDetail.departName" ></el-input>
            <el-button @click="departDialogVisible = true">选择</el-button>
          </el-form-item>
          <el-form-item label="角色状态">
            <el-select v-model="roleAccountDetail.roleStatus" placeholder="" class="select-width" clearable>
              <el-option label="全部" value=""></el-option>
              <el-option label="有效" value="1"></el-option>
              <el-option label="无效" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="账号状态">
            <el-select v-model="roleAccountDetail.accountStatus" placeholder="" class="select-width" clearable>
              <el-option label="全部" value=""></el-option>
              <el-option label="正常" value="1"></el-option>
              <el-option label="冻结" value="2"></el-option>
              <el-option label="无效" value="3"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
    </el-col>
    <!--操作按钮-->
    <el-col :offset="19" :span="5">
      <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
        <el-button v-if="(buttonPermission.indexOf('export') > -1)" type="primary" icon="el-icon-document" @click="fileExport" >导出</el-button>
    </el-col>
    <!--展示列表-->
    <el-col class="margin-top20">
      <el-table
        v-loading="isLoading"
        ref="multipleTable"
        :data="tableData"
        :highlight-current-row="true"
        @cell-click = "handleCellClick"
        :cell-style = "cellStyle"
        class="table—style" border>
        <el-table-column
          prop="roleId"
          label="角色ID"
          width="120">
          <template slot-scope="scope">
            <el-link type="primary">{{scope.row.roleId}}</el-link>
          </template>
        </el-table-column>
        <el-table-column
          prop="roleName"
          label="角色名称"
          width="120">
        </el-table-column>
        <el-table-column
          prop="bussinessLineName"
          label="支持业务线">
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
          label="员工编号">
        </el-table-column>
        <el-table-column
          prop="empName"
          label="员工姓名">
        </el-table-column>
        <el-table-column
          prop="departName"
          label="员工所属部门">
        </el-table-column>
        <el-table-column
          prop="roleStatus"
          label="角色状态">
          <template slot-scope="scope">
            <span v-if="scope.row.roleStatus === 0">无效</span>
            <span v-if="scope.row.roleStatus === 1">有效</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="accountStatus"
          label="账号状态">
          <template slot-scope="scope">
            <span v-if="scope.row.accountStatus == 1">正常</span>
            <span v-if="scope.row.accountStatus === 2">冻结</span>
            <span v-if="scope.row.accountStatus == 3">无效</span>
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
      <singleTree :treeData="treeData" v-on:checkInfo="getDepartInfo" @dialogClose="clearDepartInfo"></singleTree>
    </el-dialog>

    <!--导出弹窗内容-->
    <el-dialog ref="exportDialog" title="请选择导出条件" :visible.sync="exportDialogVisible" @close="closeExportDialog">
      <exportFile v-on:checkInfo="getCheckList" :checkOptions="roleOptions" :sonChecked="sonChecked"></exportFile>
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
    <!--账号详细信息弹窗-->
    <el-dialog ref="dialog" title="账号详细信息" :visible.sync="detailDialogVisible" >
      <el-form ref="account" status-icon  label-width="120px" size="mini">
        <el-row>
          <el-col :span="20" class="margin-left20 font-title">
            <h3>账号信息</h3>
          </el-col>
          <el-col :span="24">
            <el-row>
              <el-col :span="12">
                <el-form-item label="登录账号：">
                  <el-input v-model="accountDetail.accountLogin" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="关联员工编号：" >
                  <el-input v-model="accountDetail.empId" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="员工姓名：">
                  <el-input v-model="accountDetail.empName" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="员工所属部门：">
                  <el-input v-model="accountDetail.deptName" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="数据权限类型：" >
                  <template  v-model="accountDetail.dataPermissionId" class="select-width">
                    <span v-if="accountDetail.dataPermissionId === 0">全部</span>
                    <span v-else-if="accountDetail.dataPermissionId === 1">递归</span>
                    <span v-else-if="accountDetail.dataPermissionId === 2">本部门</span>
                    <span v-else-if="accountDetail.dataPermissionId === 3">本人</span>
                    <span v-else-if="accountDetail.dataPermissionId === 4">手动选择</span>
                  </template>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="密保邮箱：" >
                  <el-input v-model="accountDetail.accountEmail" readonly></el-input>
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
                  <el-input v-model="accountDetail.createTime" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="新建人：" >
                  <el-input v-model="accountDetail.createEmp" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="修改时间：" >
                  <el-input v-model="accountDetail.modifyTime" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="修改人：">
                  <el-input v-model="accountDetail.modifyEmp" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="20">
                <el-form-item label="备注：" >
                  <el-input type="textarea" rows="4" v-model="accountDetail.remark" maxlength="200" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </el-row>

</template>

<script>
  import singleTree from '../../../components/tree/singleTree'
  import '@/axios/api'
  import exportFile from '@/pages/common/exportFile'
    export default {
        name: "role_account_list",
        components:{
          singleTree,
          exportFile
        },
        data(){
          return{
            //从后台获取树状结构数据
            treeData:[],
            roleAccountDetail:{
              roleName:'',
              bussinessLineId:'',
              bussinessLineName:'',
              accountLogin:'',
              empId:'',
              empName:'',
              empDepart:'',
              departName:'',
              roleStatus:'',
              accountStatus:''
            },
            accountDetail: {
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
              modifyEmp:'',
              isLinkEmp:'',
            },
            tableData: [{
              roleId:'0000',
              roleName:'111',
              bussinessLineName:'11',
              accountLogin:'22',
              empId:'11',
              empName:'1',
              empDepart:'11',
              roleStatus:'2',
              accountStatus:'0'
            }],
            page:{
              total:10,
              currentPage:1,
              pageSize:10
            },
            departDialogVisible: false,
            checkId:'', //由子组件树状结构传来的部门id和名称
            checkVal:'',
            exportDialogVisible:false, //导出弹窗
            detailDialogVisible:false,
            roleOptions: ['角色ID', '角色名称', '支持业务线', '登陆账号','员工编号', '员工姓名',
              '员工所属部门', '角色状态', '账户状态'], //传显示导出字段的数据到子窗口
            checkedList: [], //获取子窗口选中的字段信息
            sonChecked: [],
            isAll: true, //设置导出弹窗字段全选或取消全选
            isLoading: false,
            exportData: [],
            buttonPermission:{}
          }
        },
        created:function(){
          this.search();
          this.getTreeData(); //初始化获取部门结构数据
          this.buttonPermission = JSON.parse(sessionStorage.getItem("buttonList")).searchAccountDetail;
        },
        methods: {
          //关闭导出
          closeExportDialog()
          {
            this.isAll = true;
          },
          //从后台获取部门树状结构数据
          getTreeData() {
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
            this.isLoading = true;
            this.$axios.get('/api/orgmanagement/search/role/getRoleAccountList',
              {params:
                  {
                    pageNum:this.page.currentPage,
                    pageSize:this.page.pageSize,
                    roleAccountDetail:this.roleAccountDetail
                  }
              })
            //then获取成功；response成功后的返回值（对象）
              .then(response=>{
                if (response.data.code == 200) {
                  console.log(response.data.data.pageData)
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
          // 处理单元格点击事件,点击员工ID显示员工详情
          handleCellClick(row,column){
            if("登录账号" === column.label){
              this.detailDialogVisible = true ;
              this.$axios.get('/api/orgmanagement/account/management/getAccountSelectByLogin', {
                params: { accountLogin: row.accountLogin }
              })
                .then(response=>{
                  if (response.data.code == 200) {
                    console.log("data传值");
                    console.log(response.data.data);
                    this.accountDetail = response.data.data;
                  }else {
                    this.$message(response.data.msg);
                  }
                })
            }else if("角色ID" === column.label){
              this.$router.push(
                {path:'/role/management/roleDetail',
                  query:{roleId:row.roleId}
                });
            }
          },
          //登录账号数据颜色
          cellStyle(row){
            if(row.column.label==="登陆账号"){
              return 'color:blue'
            }

          },
          // 导出
          fileExport() {
            this.exportDialogVisible = true;
            this.sonChecked = []; //控制第二次弹窗时清除数据
          },
          //获取导出数据并写入文件
          confirm() {
            debugger
            this.$axios.get('/api/orgmanagement/search/role/exportRoleAccountList',
              {params:
                  {
                    roleAccountDetail:this.roleAccountDetail
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
          //从子窗口获取选中的字段
          getCheckList(val){
            this.checkedList = val;
          },
          //确认导出
          writeFile() {
            let checked = this.checkedList;
            let array = new Array();
            //选中字段则导出，否则提示选择
            if (checked.length > 0){
              for(let i = 0; i < checked.length; i++){
                switch(checked[i]){
                  case "角色ID": array[i] = "roleId";break;
                  case "角色名称": array[i] = "roleName";break;
                  case "支持业务线": array[i] = "bussinessLineName";break;
                  case "登陆账号": array[i] = "accountLogin";break;
                  case "员工编号": array[i] = "empId";break;
                  case "员工姓名": array[i] = "empName";break;
                  case "员工所属部门": array[i] = "departName";break;
                  case "角色状态": array[i] = "roleStatus";break;
                  case "账户状态": array[i] = "accountStatus";break;
                }
              }
              //excel数据导出
              require.ensure([], () => {
                const {
                  export_json_to_excel
                } = require('../../../excel/Export2Excel');
                const tHeader = checked;
                const filterVal = array;
                const list = this.generate(this.exportData);
                const data = this.formatJson(filterVal, list);
                let excelName = checked.toString() + new Date().toLocaleDateString();
                export_json_to_excel(tHeader, data, excelName);
                this.$message('导出成功，请关注下载文件！');
                this.exportDialogVisible = false;
              })
            }else {
              this.$message('请选择要导出的字段！');
            }

          },
          formatJson(filterVal, jsonData) {
            return jsonData.map(v => filterVal.map(j => v[j]))
          },
          // 处理导出数字处理
          generate(data) {
            if(data.length>0) {
              for(let i =0;i<data.length;i++) {
                if(1 === data[i].bussinessLineId) {
                  data[i].bussinessLineId = '买买车';
                }else if(2 === data[i].bussinessLineId){
                  data[i].bussinessLineId = '闪贷';
                }else if(3 === data[i].bussinessLineId){
                  data[i].bussinessLineId = '租车';
                }else if(4 === data[i].bussinessLineId){
                  data[i].bussinessLineId = '专车';
                }else if(5 === data[i].bussinessLineId){
                  data[i].bussinessLineId = '保险';
                }

                if(0 === data[i].roleStatus){
                  data[i].roleStatus = '无效';
                }else if(1 === data[i].roleStatus){
                  data[i].roleStatus = '有效';
                }

                if(1 === data[i].accountStatus){
                  data[i].accountStatus = '正常';
                }else if(2 === data[i].accountStatus){
                  data[i].accountStatus = '冻结';
                }else if(3 === data[i].accountStatus){
                  data[i].accountStatus = '无效';
                }
              }
            }
            return data;
          },
          //全选
          selectAll() {
            if (this.isAll) {
              this.sonChecked = this.roleOptions;
              this.isAll = false;
            }else {
              this.sonChecked = [];
              this.isAll = true;
            }
          },
            //导出窗口关闭
          dialogClose() {
            this.exportDialogVisible = false;
            this.isAll = true;
          },
          allocateDepart() {

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
            console.log(data)
            this.roleAccountDetail.empDepart= data.split("#")[0];
            this.roleAccountDetail.departName = data.split("#")[1];
            this.departDialogVisible = false
          },
          // 情况部门选择数据
          clearDepartInfo(val) {
            this.roleAccountDetail.departName = '';
            // this.empSearchForm.empDepartName = '';
            this.departDialogVisible = val;
          }
      }
    }
</script>

<style scoped>
  a {
    text-decoration:none;
  }
</style>
