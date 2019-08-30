<template>
  <el-row class="list-container">
    <!--搜索条件-->
    <el-col>
      <el-form :inline="true" :model="role" label-width="120px">
        <el-form-item label="角色名称">
          <el-input v-model="role.roleName" clearable></el-input>
        </el-form-item>
        <el-form-item label="支持业务线">
          <el-select v-model="role.bussinessLineName" placeholder="" class="select-width">
            <el-option label="全部" value=""></el-option>
            <el-option label="买买车" value="买买车"></el-option>
            <el-option label="闪贷" value="闪贷"></el-option>
            <el-option label="租车" value="租车"></el-option>
            <el-option label="专车" value="专车"></el-option>
            <el-option label="保险" value="保险"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批人账号">
          <el-input v-model="role.approveAccount" clearable></el-input>
        </el-form-item>
        <el-form-item label="审批人员工编号">
          <el-input v-model="role.approveId" clearable></el-input>
        </el-form-item>
        <el-form-item label="审批人姓名">
          <el-input v-model="role.approveName" clearable></el-input>
        </el-form-item>
        <el-form-item label="审批人所属部门">
          <el-input v-model="role.approveDeptName" ></el-input>
          <el-button @click="departDialogVisible = true">选择</el-button>
        </el-form-item>
        <el-form-item label="权限名称">
          <el-input v-model="role.permissionName" ></el-input>
          <el-button @click="permissionDialogVisible = true">选择</el-button>
        </el-form-item>
        <el-form-item label="角色状态">
          <el-select v-model="role.status" placeholder="" class="select-width">
            <el-option label="全部" value=""></el-option>
            <el-option label="有效" value="1"></el-option>
            <el-option label="无效" value="0"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </el-col>
    <!--操作按钮-->
    <el-col :offset="19" :span="5">
      <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
      <el-button v-if="(buttonPermission.indexOf('export') > -1)" type="primary" icon="el-icon-search" @click="fileExport">导出</el-button>
    </el-col>
    <!--展示列表-->
    <el-col class="margin-top20">
      <el-table
        v-loading="isLoading"
        ref="multipleTable"
        :data="tableData"
        :highlight-current-row="true"
        @selection-change="handleSelectionChange"
        @cell-click = "handleCellClick"
        class="table—style"
        border>
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
          prop="approveAccount"
          label="审批人账号"
          width="120">
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
          prop="approveDeptName"
          label="审批人所属部门">
        </el-table-column>
        <el-table-column prop="permissionName" label="权限名称" :resizable="false">
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
        <el-table-column
          prop="status"
          label="角色状态">
          <template slot-scope="scope">
            <span v-if="scope.row.status === 0">无效</span>
            <span v-if="scope.row.status === 1">有效</span>
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
    <el-dialog ref="dialog" title="请选择部门" :visible.sync="departDialogVisible">
      <singleTree :treeData="treeData" v-on:checkInfo="getDepartInfo" @dialogClose="clearDepartInfo"></singleTree>
    </el-dialog>

    <!--分配所属权限弹窗内容-->
    <el-dialog ref="dialog" title="请选择权限" :visible.sync="permissionDialogVisible">
      <singleTree :treeData="permissionData" v-on:checkInfo="getPermissionInfo"  @dialogClose="clearPermissionInfo"></singleTree>
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
  import singleTree from '@/components/tree/singleTree'
  import '@/axios/api'
  import exportFile from '@/pages/common/exportFile'
  import {exportDate1} from "../../../constant/exportDate";

  export default {
    name: "role_permission_list",
    components: {
      singleTree,
      exportFile
    },
    data(){
      return{
        //从后台获取树状结构数据
        treeData:[],
        permissionData:[],
        role:{
          roleId:'',
          roleName:'',
          bussinessLineId:'',
          bussinessLineName:'',
          approveAccount:'',
          approveId:'',
          approveName:'',
          approveDept:'',
          approveDeptName:'',
          permissionName:'',
          status:'',
          resourceId: ""
        },
        tableData: [],
        multipleSelection: [],
        departDialogVisible: false,
        permissionDialogVisible: false,
        checkId:'', //由子组件树状结构传来的部门id和名称
        checkVal:'',
        page:{
          total:10,
          currentPage:1,
          pageSize:10
        },
        exportDialogVisible:false, //导出弹窗
        roleOptions: ['角色ID', '角色名称', '支持业务线', '审批人账号', '审批人员工编号', '审批人姓名',
          '审批人所属部门', '权限名称', '角色状态'], //传显示导出字段的数据到子窗口
        checkedList: [], //获取子窗口选中的字段信息
        sonChecked: [],
        exportData: [],
        isAll: true, //设置导出弹窗字段全选或取消全选
        isLoading: false,
        buttonPermission: {},
      }
    },
    created:function(){
      this.search();
      this.getTreeData(); //初始化获取部门结构数据
      this.getPermissionData(); // 初始化获取权限结构数据
      this.buttonPermission = JSON.parse(sessionStorage.getItem("buttonList")).searchPermissionDetail;

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
      // 清空部门选择数据
      clearDepartInfo(val) {
        this.role.approveDeptName = '';
        // this.empSearchForm.empDepartName = '';
        this.departDialogVisible = val;
      },
      //从后台获取权限结构数据
      getPermissionData() {
        this.$axios.get('/api/orgmanagement/resource/tree')
          .then(response=>{
            if (response.data.code === 200) {
              this.permissionData = response.data.data;
              console.log(response.data.data)

            }else {
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error=>{
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
      //清空权限选择数据
      clearPermissionInfo() {
        this.role.permissionName = '';
        this.permissionDialogVisible = false;
      },
      search(type) {
        if (type !== 'page') {
          this.page.currentPage = 1;
        }
        if (this.role.permissionName === ""){
          this.role.resourceId = null;
        }
        this.isLoading = true;
        this.$axios.get('/api/orgmanagement/search/role/getRolePermissionList',
          {params:
              {
                pageNum:this.page.currentPage,
                pageSize:this.page.pageSize,
                role:this.role
              }
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response=>{
            if (response.data.code === 200) {
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
      handleSelectionChange(val) {
        this.multipleSelection = val;
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
        this.role.approveDept= data.split("#")[0];
        this.role.approveDeptName = data.split("#")[1];
        this.departDialogVisible = false
      },
      //从子组件中获取到选择的所属权限信息
      getPermissionInfo(data) {
        this.role.resourceId= data.split("#")[0];
        this.role.permissionName = data.split("#")[1];
        this.permissionDialogVisible = false
      },
      // 导出
      fileExport() {
        this.exportDialogVisible = true;
        this.sonChecked = []; //控制第二次弹窗时清除数据
      },
      //获取导出数据并写入文件
      confirm() {
        this.$axios.get('/api/orgmanagement/search/role/exportRoleResourceList',
          {params:
              {
                role:this.role
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
              case "审批人账号": array[i] = "approveAccount";break;
              case "审批人员工编号": array[i] = "approveId";break;
              case "审批人姓名": array[i] = "approveName";break;
              case "审批人所属部门": array[i] = "approveDeptName";break;
              case "权限名称": array[i] = "permissionName";break;
              case "角色状态": array[i] = "status";break;
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
            let excelName = checked.toString() + " " + exportDate1();
            export_json_to_excel(tHeader, data, excelName);
            this.$message('导出成功，请关注下载文件！');
            this.exportDialogVisible = false;
          })
        }else {
          this.$message('请选择要导出的字段！');
        }

      },
      // 处理导出数字处理
      generate(data) {
        if(data.length>0) {
          for(let i =0;i<data.length;i++) {
            if(0 === data[i].status){
              data[i].status = '无效';
            }else if(1 === data[i].status){
              data[i].status = '有效';
            }
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
      // 处理单元格点击事件,点击员工ID显示员工详情
      handleCellClick(row,column){
        if("角色ID" === column.label){
          this.$router.push(
            {path:'/role/management/roleDetail',
              query:{roleId:row.roleId}
            });
        }
      },
    },
    watch: {
      parentVal: function () {
        this.dialogVisible = this.parentVal;
      }
    }
  }
</script>

<style scoped>
  a {
    text-decoration:none;
  }
</style>
