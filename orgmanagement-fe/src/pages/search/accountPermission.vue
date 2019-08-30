<template>
  <el-row class="list-container">
    <!--搜索条件-->
    <el-col>
      <el-form :inline="true" :model="accountPermission" label-width="80px">
        <el-form-item label="登录账号">
          <el-input v-model="accountPermission.accountLogin" clearable></el-input>
        </el-form-item>
        <el-form-item label="权限名称">
          <el-input v-model="accountPermission.permissionName" clearable readonly placeholder="请选择权限名称"></el-input>
          <el-button @click="permissionDialogVisible = true">选择</el-button>
        </el-form-item>
        <el-form-item label="员工编号">
          <el-input v-model="accountPermission.empId" clearable></el-input>
        </el-form-item>
        <el-form-item label="员工姓名">
          <el-input v-model="accountPermission.empName"  class="select-width" clearable></el-input>
        </el-form-item>
        <el-form-item label="所属部门">
          <el-input v-model="accountPermission.deptName" clearable readonly placeholder="请选择所属部门"></el-input>
          <el-button @click="departDialogVisible = true">选择</el-button>
        </el-form-item>
        <el-form-item label="账号状态">
          <el-select v-model="accountPermission.status"  class="select-width">
            <el-option label="正常" value="1"></el-option>
            <el-option label="冻结" value="0"></el-option>
            <el-option label="全选" value=""></el-option>
          </el-select>
        </el-form-item>

      </el-form>
    </el-col>
    <!--操作按钮-->
    <el-col :offset="19" :span="5">
      <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
      <el-button type="primary" icon="el-icon-document" @click="fileExport" >导出</el-button>
    </el-col>
    <!--展示列表-->
    <el-col class="margin-top20">
      <el-table
        v-loading="isLoading"
        ref="multipleTable"
        :data="tableData"
        :highlight-current-row="true"
        @cell-click = "handleCellClick"
        class="table—style" border>
        <el-table-column prop="accountLogin" label="登录账号" width="175">
          <template slot-scope="scope">
            <el-link type="primary">{{scope.row.accountLogin}}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="empId" label="员工编号"width="175"></el-table-column>
        <el-table-column prop="empName" label="员工姓名"width="175"></el-table-column>
        <el-table-column prop="deptName" label="所属部门"width="175"></el-table-column>
        <el-table-column prop="status" label="账号状态"width="175 ">
          <template slot-scope="scope">
            <span v-if="scope.row.status === 0">冻结</span>
            <span v-else="scope.row.status === 1">正常</span>
          </template>
        </el-table-column>
        <el-table-column prop="permissionName" label="权限名称">
          <template slot-scope="scope">
            <el-popover
              placement="bottom"
              width="600"
              trigger="hover"
              :content="scope.row.permissionName">
              <el-button slot="reference" type="text">{{scope.row.permissionName|ellipsis}}</el-button>
            </el-popover>
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
        :page-sizes="[10, 20, 30, 40]"
        :page-size=page.pageSize
        layout="total, sizes, prev, pager, next, jumper"
        :total=page.total>
      </el-pagination>
    </el-col>
    <!--分配所属部门弹窗内容-->
    <el-dialog ref="dialog" title="请选择部门" :visible.sync="departDialogVisible">
      <singleTree :treeData="treeData" v-on:checkInfo="getDepartInfo" @dialogClose="clearDepartInfo"></singleTree>
    </el-dialog>
    <!--分配所属权限弹窗内容-->
    <el-dialog ref="dialog" title="请选择权限" :visible.sync="permissionDialogVisible">
      <singleTree :treeData="permissionData" v-on:checkInfo="getPermissionInfo" @dialogClose="clearPermissionInfo"></singleTree>
    </el-dialog>
    <!--导出弹窗内容-->
    <el-dialog ref="exportDialog" title="请选择导出条件" :visible.sync="exportDialogVisible" @close="closeExportDailog">
      <exportFile v-on:checkInfo="getCheckList" :checkOptions="accountPermissionOptions" :sonChecked="sonChecked"></exportFile>
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
  import singleTree from '@/components/tree/singleTree';
  import exportFile from '@/pages/common/exportFile';

  export default {
    name: "accountPermission",
    components: {
      singleTree,
      exportFile
    },
    data(){
      return {
        treeData:[],
        exportData:[],
        permissionData:[],
        accountPermission:{
          accountLogin:"",
          permissionName:"",
          empId:"",
          empName:"",
          deptName:"",
          status:"",
          resourceId:""
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
        tableData: [],
        detailDialogVisible:false,
        departDialogVisible: false,
        permissionDialogVisible: false,
        checkId:'', //由子组件树状结构传来的部门id和名称
        checkVal:'',
        page: {
          total: 10,
          currentPage: 1,
          pageSize: 10
        },
        exportDialogVisible:false, //导出弹窗
        accountPermissionOptions: ['登录账号', '权限名称', '员工编号', '员工姓名', '所属部门',
          '账号状态'], //传显示导出字段的数据到子窗口
        checkedList: [], //获取子窗口选中的字段信息
        sonChecked: [],
        isAll: true, //设置导出弹窗字段全选或取消全选
        isLoading: false
      }
    },
    mounted: function () {
      this.search();
      this.getTreeData(); //初始化获取部门结构数据
      this.getPermissionData(); // 初始化获取权限结构数据
    },
    filters:{
      ellipsis(value){
        if(value&& value.length > 20) {
          value= value.substring(0,20)+ '...';
        }
        return value;
      }
    },

    methods:{
      search(type) {
        if (type !== 'page') {
          this.page.currentPage = 1;
        }
        this.isLoading = true;
        this.$axios.get('/api/orgmanagement/search/getAccountPermissionList',
          {
            params:
              {
                pageNum: this.page.currentPage,
                pageSize: this.page.pageSize,
                accountPermission: this.accountPermission
              }
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
            if (response.data.code === 200) {
              this.tableData = response.data.data.pageData;
              this.page.total = response.data.data.total;
              this.isLoading = false;
            } else {
              this.isLoading = false;
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
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
      //从后台获取权限结构数据
      getPermissionData() {
        this.$axios.get('/api/orgmanagement/resource/tree')
          .then(response=>{
            if (response.data.code === 200) {
              this.permissionData = response.data.data;
            }else {
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error=>{
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
      // 修改分页显示条数
      handleSizeChange(val) {
        this.page.pageSize = val;
        this.search('page');
      },
      //  显示第几页数据
      handleCurrentChange(val) {
        this.page.currentPage = val;
        this.search('page');
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
        }
      },
      //从子组件中获取到选择的所属部门信息
      getDepartInfo(data) {
        //this.role.approveDept= data.split("#")[0];
        this.accountPermission.deptName = data.split("#")[1];
        this.departDialogVisible = false
      },
      //从子组件中获取到选择的所属权限信息
      getPermissionInfo(data) {
        this.accountPermission.resourceId = data.split("#")[0];
        this.accountPermission.permissionName = data.split("#")[1];
        this.permissionDialogVisible = false
      },
      // 清除选中部门
      clearDepartInfo(val){
        this.accountPermission.deptName = '';
        this.departDialogVisible = val;
        this.search();
      },
      //清除选中的权限名称
      clearPermissionInfo(val){
        this.accountPermission.permissionName = '';
        this.accountPermission.resourceId = "";
        this.permissionDialogVisible = val;
        this.search();
      },
      // 导出
      fileExport() {
        this.exportDialogVisible = true;
        this.sonChecked = []; //控制第二次弹窗时清除数据
      },
      //获取导出数据并写入文件
      confirm() {
        debugger
        this.$axios.get('/api/orgmanagement/search/exportAccountPermissionListAll',
          {params:
              {
                accountPermission:this.accountPermission
              }
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response=>{
            if (response.data.code === 200) {
              this.exportData= response.data.data;
              console.log(this.tableData[0])
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
              case "登录账号": array[i] = "accountLogin";break;
              case "权限名称": array[i] = "permissionName";break;
              case "员工编号": array[i] = "empId";break;
              case "员工姓名": array[i] = "empName";break;
              case "所属部门": array[i] = "deptName";break;
              case "账号状态": array[i] = "status";break;
            }
          }
          //excel数据导出
          require.ensure([], () => {
            const {
              export_json_to_excel
            } = require('../../excel/Export2Excel');
            const tHeader = checked;
            const filterVal = array;
            const list = this.generate(this.exportData)
            const data = this.formatJson(filterVal, list);

            const {
              exportDate1
            } = require('../../constant/exportDate');
            let excelName = checked.toString() + " " + exportDate1();

            export_json_to_excel(tHeader, data, excelName);
            this.$message('导出成功，请关注下载文件！');
            this.exportDialogVisible = false;
          })
        }else {
          this.$message('请选择要导出的字段！');
        }

      },
      generate(data) {
        if(data.length>0) {
          for(let i =0;i<data.length;i++) {
            data[i].status = data[i].status === 0 ? "冻结" : "正常";
          }
        }
        return data;
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      //全选
      selectAll() {
        if (this.isAll) {
          this.sonChecked = this.accountPermissionOptions;
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
      closeExportDailog(){
        this.isAll = true;
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
