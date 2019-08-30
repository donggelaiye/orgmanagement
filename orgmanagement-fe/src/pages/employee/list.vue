<template>
  <el-row class="list-container">
    <!--搜索条件-->
    <el-col>
        <el-form :inline="true" :model="empSearchForm" label-width="80px">
          <el-form-item label="员工编号">
            <el-input v-model="empSearchForm.empId" clearable></el-input>
          </el-form-item>
          <el-form-item label="员工姓名">
            <el-input v-model="empSearchForm.empName" clearable></el-input>
          </el-form-item>
          <el-form-item label="登录账号">
            <el-input v-model="empSearchForm.accountLogin" clearable></el-input>
          </el-form-item>
          <el-form-item label="是否离职">
            <el-select v-model="empSearchForm.isDimission" placeholder="" class="select-width" clearable>
              <el-option label="全部" value=""></el-option>
              <el-option label="在职" value="0"></el-option>
              <el-option label="离职" value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="所属部门">
              <el-input v-model="empSearchForm.empDepartName" clearable readonly></el-input>
              <el-button @click="departDialogVisible = true">选择</el-button>
          </el-form-item>
          <el-form-item label="上级部门">
            <el-input v-model="empSearchForm.superDepartName" clearable readonly></el-input>
            <el-button @click="superDepartDialogVisible = true">选择</el-button>
          </el-form-item>
        </el-form>
    </el-col>
    <!--操作按钮-->
    <el-col :offset="19" :span="5">
      <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
        <el-button type="primary" icon="el-icon-document" @click="fileExport" >导出</el-button>
    </el-col>
    <!--功能按钮-->
    <el-col :offset="1" :span="23" class="margin-top20">
      <el-button icon="el-icon-edit-outline" @click="createEmp" round type="primary" :disabled="isBtnCreate">新建</el-button>
      <el-button icon="el-icon-edit-outline" @click="updateEmp" round type="primary" :disabled="isBtnUpdata">修改</el-button>
      <el-button icon="el-icon-edit-outline" @click="deleteEmp" round type="primary" :disabled="isBtnDelete">删除</el-button>
      <el-button icon="el-icon-edit-outline" @click="dimissionEmp" round type="primary" :disabled="isBtnDimission">离职</el-button>
      <el-button icon="el-icon-edit-outline" @click="recoverEmp" round type="primary" :disabled="isBtnReDimission">恢复</el-button>
      <el-button icon="el-icon-edit-outline" @click="allocateDepart" round type="primary" :disabled="isBtnAllocateDepart">分配部门</el-button>
    </el-col>
    <!--展示列表-->
    <el-col class="margin-top20">
      <el-table ref="multipleTable" v-loading="isLoading" :data="tableData" :highlight-current-row="true"
        @selection-change="handleSelectionChange"
        @row-dblclick="rowClick"
        @cell-click="handleCellClick"
        class="table—style" border>
        <el-table-column type="selection" label="操作" prop="id" width="40"></el-table-column>
        <el-table-column prop="empId" label="员工编号" width="100">
          <template slot-scope="scope">
            <el-link type="primary">{{scope.row.empId}}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="accountLogin" label="登录账号" width="100"></el-table-column>
        <el-table-column prop="empName" label="员工姓名"></el-table-column>
        <el-table-column prop="empSex" label="性别" width="50">
          <template slot-scope="scope">
            <span v-if="scope.row.empSex === 0">男</span>
            <span v-if="scope.row.empSex === 1">女</span>
          </template>
        </el-table-column>
        <el-table-column prop="empPhone" label="员工手机" width="115">
          <template slot-scope="scope">
            {{scope.row.empPhone|maskPhone}}
          </template>
        </el-table-column>
        <el-table-column prop="empEmail" label="员工邮箱" width="170"></el-table-column>
        <el-table-column prop="deptName" label="所属部门" width="130"></el-table-column>
        <el-table-column prop="superDepartName" label="上级部门" width="130"></el-table-column>
        <el-table-column prop="isDimission" label="是否离职">
          <template slot-scope="scope">
            <span v-if="scope.row.isDimission === 0">在职</span>
            <span v-else="scope.row.isDimission === 1">离职</span>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
    <!--分页导航-->
    <el-col :span="20" class="margin-top20 margin-left20">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page=page.currentPage
        :page-sizes="[10, 20, 30, 40, 50]"
        :page-size=page.pageSize
        layout="total, sizes, prev, pager, next, jumper"
        :total=page.total>
      </el-pagination>
    </el-col>

    <!--分配所属部门弹窗内容-->
    <el-dialog ref="dialog" title="请选择所属部门" :visible.sync="departDialogVisible">
      <singleTree :treeData="treeData" v-on:checkInfo="getDepartInfo" @dialogClose="clearDepartInfo"></singleTree>
    </el-dialog>
    <!--分配上级部门弹窗内容-->
    <el-dialog ref="dialog" title="请选择上级部门" :visible.sync="superDepartDialogVisible">
      <singleTree :treeData="treeData" v-on:checkInfo="getSuperDepartInfo" @dialogClose="clearSuperDepartInfo"></singleTree>
    </el-dialog>
    <!--员工详细信息弹窗-->
    <el-dialog ref="dialog" title="员工详细信息" :visible.sync="detailDialogVisible">
      <el-form ref="employee" status-icon :model="employee" label-width="120px" size="mini">
        <el-row>
          <el-col :span="20" class="margin-left20 font-title">
            <h3>员工信息</h3>
          </el-col>
          <el-col :span="24">
            <el-row>
              <el-col :span="10">
                <el-form-item label="员工编号：">
                  <el-input v-model="employee.empId" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="员工姓名：">
                  <el-input v-model="employee.empName" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="性别：">
                  <el-radio-group v-model="employee.empSex" disabled>
                    <el-radio :label="0" >男</el-radio>
                    <el-radio :label="1" >女</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="员工手机：">
                  <el-input v-model="employee.empPhone" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="员工邮箱：">
                  <el-input v-model="employee.empEmail" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="所属部门：">
                  <el-input v-model="employee.deptName" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="是否离职：" >
                  <el-input v-model="employee.isDimission" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="登录账号：">
                  <el-input v-model="employee.accountLogin" readonly></el-input>
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
                  <el-input v-model="employee.createTime" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="新建人：">
                  <el-input v-model="employee.createEmpName" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="修改时间：">
                  <el-input v-model="employee.modifyTime" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="修改人：">
                  <el-input v-model="employee.modifyEmpName" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="20">
                <el-form-item label="备注：" props="remark">
                  <el-input type="textarea" rows="4" v-model="employee.remark" maxlength="200" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
      </el-form>
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

  </el-row>

</template>

<script>
  import singleTree from '../../components/tree/singleTree';
  import '../../axios/api';
  import exportFile from '@/pages/common/exportFile'

  export default {
        name: "employee_list",
        components:{
          singleTree,
          exportFile
        },
        data(){
          return{
            treeData:[], // 部门树数据
            // 员工搜索表单数据
            empSearchForm:{
              empId:'',
              empName:'',
              accountLogin:'',
              isDimission:'',
              empDepart:'',
              empDepartName:'',
              superDepart:'',
              superDepartName:''
            },
            // 员工详情数据
            employee: {
              empId: '',
              accountLogin: '',
              empName: '',
              empSex:'',
              empPhone:'',
              empEmail:'',
              empDepart:'',
              deptName:'',
              isDimission: 0,
              isDelete: 0,
              createTime:'',
              createEmp:'',
              createEmpName:'',
              modifyTime:'',
              modifyEmp:'',
              modifyEmpName:'',
              remark:''
            },
            tableData: [],  // 表格数据
            multipleSelection: [],
            detailDialogVisible: false,
            departDialogVisible: false,
            superDepartDialogVisible: false,
            // 由子组件树状结构传来的部门id和名称
            checkId:'',
            checkVal:'',
            page:{
              total:10,
              currentPage:1,
              pageSize:10
            },
            exportDialogVisible:false, //导出弹窗
            roleOptions: ['员工编号', '登陆账号', '员工姓名', '性别', '员工手机', '员工邮箱',
              '所属部门', '上级部门', '是否离职'], //传显示导出字段的数据到子窗口
            checkedList: [], //获取子窗口选中的字段信息
            sonChecked: [],
            exportData: [],
            isAll: true ,//设置导出弹窗字段全选或取消全选
            // 按钮控制判断变量
            isBtnCreate: false,
            isBtnUpdata: true,
            isBtnDelete: true,
            isBtnDimission: true,
            isBtnReDimission: true,
            isBtnAllocateDepart: true,
            isLoading: false
          }
        },
        created:function(){
          //初始化获取所有有效员工数据
          this.search();
          //初始化获取部门结构数据
          this.getTreeData();
        },
        filters: {
          maskPhone: function (value) {
            if(!value){
              return '';
            }
            return value.substr(0, 3) + "****" + value.substr(-4);
          }
        },
        methods: {
          // 从后台获取部门树状结构数据
          getTreeData() {
            this.$axios.get('/api/orgmanagement/dept/validTree')
              .then(response=>{
                if (response.data.code == 200) {
                  this.treeData = response.data.data;
                  console.log(this.treeData);
                }else {
                  this.$message(response.data.msg);
                }
              }).catch(error=>{
              this.$message('网络错误，不能访问，请联系管理员');
            })
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
              this.multipleSelection = row
            }
          },
          // 修改分页显示条数
          handleSizeChange(val) {
            this.page.pageSize = val;
            this.search('page')
          },
          // 修改当前页面数
          handleCurrentChange(val) {
            this.page.currentPage = val;
            this.search('page')
          },
          // 检查选中记录，修改、删除、离职、恢复都限制只能选中一条进行操作
          checkChoiceNum() {
            let msg = '';
            if (this.multipleSelection.length == 0){
              msg = '请选中一条记录进行操作！'
            }else if (this.multipleSelection.length > 1) {
              msg = '只能选中一条记录进行操作!'
            }else {
              return true
            }
            // 弹窗提示
            this.$alert(msg, '提示', {
              confirmButtonText: '确定',
            });
          },
          // 处理单元格点击事件,点击员工ID显示员工详情
          handleCellClick(row,column){
            if("员工编号" === column.label){
              this.getEmployee(row.empId);
              this.detailDialogVisible = true ;
            }
          },
          // 双击列选中事件
          rowClick(row) {
            this.$refs.multipleTable.toggleRowSelection(row)
          },
          // 处理按钮权限
          checkBtnAuthority(row){
            if (0 === row.length ) {
              this.isBtnUpdata = true;
              this.isBtnDelete = true;
              this.isBtnDimission = true;
              this.isBtnReDimission = true;
              this.isBtnAllocateDepart = true;
            }else{
              this.isBtnDelete = false;
              if(0 === row[0].isDimission){
                this.isBtnUpdata = false;
                this.isBtnDimission = false;
                this.isBtnAllocateDepart = false;
              }else{
                this.isBtnReDimission = false;
              }
            }
          },

          // 搜索员工方法
          search(type) {
            if (type !== 'page') {
              this.page.currentPage = 1;
            }
            this.isLoading = true;
            this.$axios.get('/api/orgmanagement/employee/management/getEmployeeList',{
              params: {
                pageNum:this.page.currentPage,
                pageSize:this.page.pageSize,
                empSearchForm:this.empSearchForm,
                date: new Date().getTime()
              }
            }) .then(response=>{
              if (response.data.code == 200) {
                console.log(response.data.data.pageData);
                this.tableData = response.data.data.pageData;
                this.page.total = response.data.data.total;
                this.isLoading = false;
              }else {
                this.isLoading = false;
                this.$message(response.data.msg);
              }
            }).catch(error=>{
              this.$message('网络错误，不能访问，请联系管理员');
            })
          },
          // 通过员工ID获取员工信息
          getEmployee(selectEmpId){
            this.$axios.get('/api/orgmanagement/employee/management/getEmployee', {
              params: { empId: selectEmpId ,
                date: new Date().getTime()
              }
            }).then(response => {
              if (response.data.code === 200) {
                this.employee = response.data.data;
                if(0 === this.employee.isDimission){
                  this.employee.isDimission = '在职';
                }else{
                  this.employee.isDimission = '离职';
                }
              } else {
                this.$message({
                  type: 'error',
                  message: response.data.msg
                });
              }
            }).catch(error => {
              this.$message('网络错误，不能访问，请联系管理员');
            })
          },

          // 导出员工数据表
          fileExport() {
            this.exportDialogVisible = true;
            this.sonChecked = []; //控制第二次弹窗时清除数据
          },
          //获取导出数据并写入文件
          confirm() {
            this.$axios.get('/api/orgmanagement/employee/management/getEmployeeListAll',{
              params:
                  {
                    empSearchForm:this.empSearchForm ,
                    date: new Date().getTime()
                  }
              })
              .then(response=>{
                if (response.data.code === 200) {
                  this.exportData= response.data.data;
                  this.writeFile();
                  this.exportDialogVisible = false;
                }else {
                  this.$message(response.data.msg);
                }
              })
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
                  case "员工编号": array[i] = "empId";break;
                  case "登陆账号": array[i] = "accountLogin";break;
                  case "员工姓名": array[i] = "empName";break;
                  case "性别": array[i] = "empSex";break;
                  case "员工手机": array[i] = "empPhone";break;
                  case "员工邮箱": array[i] = "empEmail";break;
                  case "所属部门": array[i] = "deptName";break;
                  case "上级部门": array[i] = "superDepartName";break;
                  case "是否离职": array[i] = "isDimission";break;
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
                let excelName = checked.toString() + " " + exportDate1();

                //let excelName = checked.toString() + '+' + new Date().toLocaleDateString();
                export_json_to_excel(tHeader, data, excelName);
                this.$message('导出成功，请关注下载文件！');
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
                if(0 === data[i].empSex) {
                  data[i].empSex = '男';
                }else{
                  data[i].empSex = '女';
                }

                if(0 === data[i].isDimission){
                  data[i].isDimission = '在职';
                }else{
                  data[i].isDimission = '离职';
                }
              }
            }
            return data;
          },
          // 全选
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

          // 新增员工
          createEmp() {
            this.$router.push({path: '/employee/detail',query:{type:"add"}})
          },
          // 修改员工
          updateEmp() {
            if(0 === this.multipleSelection[0].isDimission){
              this.$router.push({path: '/employee/detail',query:{selectEmpId:this.multipleSelection[0].empId , type:"update"}})
            }else{
              this.$alert('无法修改离职员工!', '提示');
            }
          },

          // 删除员工
          deleteEmp() {
            this.$confirm('确定删除员工？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.doDeleteEmp();
            }).catch(() => {});
          },
          // 调用删除员工API接口
          doDeleteEmp(){
            this.$axios.get('/api/orgmanagement/employee/management/deleteEmployee', {
              params: {
                empId: this.multipleSelection[0].empId ,
                date: new Date().getTime()
              }
            }).then(response => {
              if (response.data.code === 200) {
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                });
                this.search();
              } else {
                this.$message({
                  type: 'error',
                  message: response.data.msg
                });
              }
            }).catch(error => {
              this.$message('网络错误，不能访问，请联系管理员');
            })
          },

          // 离职员工
          dimissionEmp() {
            if(1 === this.multipleSelection[0].isDimission){
              this.$alert('该员工已为离职状态!', '提示');
            }else{
              this.$confirm('确定将该员工置为离职状态？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                this.doDimissionEmp();
              }).catch(() => {});
            }
          },
          // 调用离职员工API接口
          doDimissionEmp(){
            this.$axios.get('/api/orgmanagement/employee/management/updateDimission', {
              params: {
                empId: this.multipleSelection[0].empId ,
                date: new Date().getTime()
              }
            }).then(response => {
              if (response.data.code === 200) {
                this.$message({
                  type: 'success',
                  message: '置为离职状态成功!'
                });
                this.search();
              } else {
                this.$message({
                  type: 'error',
                  message: response.data.msg
                });
              }
            }).catch(error => {
              this.$message('网络错误，不能访问，请联系管理员');
            })
          },

          // 恢复离职员工
          recoverEmp() {
            if(0 === this.multipleSelection[0].isDimission){
              this.$alert('该员工已为在职状态!', '提示');
            }else{
              this.$confirm('确定恢复该员工为在职状态？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                this.doRecoverEmp();
              }).catch(() => {});
            }
        },
          // 调用恢复离职员工API接口
          doRecoverEmp(){
            this.$axios.get('/api/orgmanagement/employee/management/updateReDimission', {
              params: {
                empId: this.multipleSelection[0].empId ,
                date: new Date().getTime()
              }
            }).then(response => {
              if (response.data.code === 200) {
                this.$message({
                  type: 'success',
                  message: '恢复员工离职成功!'
                });
                this.search();
              } else {
                this.$message({
                  type: 'error',
                  message: response.data.msg
                });
              }
            }).catch(error => {
              this.$message('网络错误，不能访问，请联系管理员');
            })
        },

          // 分配部门
          allocateDepart() {
            this.$router.push({path: '/employee/allocateDepart', query: {selectEmpId: this.multipleSelection[0].empId}})
          },
          closeExportDialog() {
            this.isAll = true;
          }

      }
    }
</script>

<style>
  th.el-table-column--selection div.cell {
    display: none;
  }
</style>
