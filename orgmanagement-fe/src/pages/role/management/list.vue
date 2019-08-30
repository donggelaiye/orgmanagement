<template>
  <el-row class="list-container">
    <!--搜索条件-->
    <el-col>
      <el-form :inline="true" :model="role" label-width="120px">
        <el-form-item label="角色名称">
          <el-input v-model="role.roleName" clearable></el-input>
          <!--操作按钮-->
          <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
          <el-button v-if="buttonPermission.indexOf('exportRoleList') > -1" type="primary" icon="el-icon-document" @click="fileExport" >导出</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-col :offset="1" :span="23" class="margin-top20">
      <el-button v-if="buttonPermission.indexOf('addRole') > -1" icon="el-icon-edit-outline" @click="createRole" round type="primary">新建</el-button>
      <el-button v-if="buttonPermission.indexOf('updateRole') > -1" icon="el-icon-edit-outline" @click="updateRole" round :disabled="isEnabled" type="primary" >修改 </el-button>
      <el-button v-if="buttonPermission.indexOf('deleteRole') > -1" icon="el-icon-edit-outline" @click="deleteRole" round :disabled="isEnabled"  type="primary">删除 </el-button>
      <el-button v-if="buttonPermission.indexOf('addRoleAccountList') > -1" icon="el-icon-edit-outline" @click="addAccount" round :disabled="isEnabled" type="primary">添加账号</el-button>
      <el-button v-if="buttonPermission.indexOf('permission') > -1" icon="el-icon-edit-outline" @click="allocatePermission" round :disabled="isEnabled" type="primary">分配权限</el-button>
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
          prop="id"
          width="55">
        </el-table-column>
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
            prop="status;"
          label="状态">
          <template slot-scope="scope">
            <span v-if="scope.row.status == 1">有效</span>
            <span v-else="scope.row.status == 0">无效</span>
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

    <!--导出弹窗内容-->
    <el-dialog ref="exportDialog" title="请选择导出条件" :visible.sync="exportDialogVisible">
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
  import singleTree from '../../../components/tree/singleTree'
  import '@/axios/api'
  import exportFile from '@/pages/common/exportFile'

    export default {
      name: "role_list",
      components: {
        singleTree,
        exportFile
      },
      data() {
        return {
          role: {
            roleId: '',
            roleName: '',
            approveAccount: '',
            approveId: '',
            approveName: '',
            approveDepartment: '',
            status: '',
            isDelete: 0,
            description: ''
          },
          tableData: [],
          exportData: [],
          multipleSelection: [],
          departDialogVisible: false,
          superDepartDialogVisible: false,
          checkId: '', //由子组件树状结构传来的部门id和名称
          checkVal: '',
          page: {
            total: 10,
            currentPage: 1,
            pageSize: 10,
          },
          exportDialogVisible: false, //导出弹窗
          roleOptions: ['角色ID', '角色名称', '审批人账号', '审批人员工编号', '审批人姓名',
            '审批人所属部门', '角色状态', '描述'], //传显示导出字段的数据到子窗口
          checkedList: [], //获取子窗口选中的字段信息
          sonChecked: [],
          isAll: true, //设置导出弹窗字段全选或取消全选
          isEnabled:'',
          buttonPermission: {} //控制按钮权限
        }
      },
      created: function () {
        this.search();
        this.buttonPermission = JSON.parse(sessionStorage.getItem("buttonList")).role;
        console.log(this.buttonPermission);
      },
      methods: {
        search(type) {
          if (type !== 'page') {
            this.page.currentPage = 1;
          }
          this.$axios.get('/api/orgmanagement/role/management/getRoleList',
            {
              params:
                {
                  pageNum: this.page.currentPage,
                  pageSize: this.page.pageSize,
                  roleName: this.role.roleName,
                  Date:new Date()
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
        handleSelectionChange(val) {
          this.multipleSelection = val;
          if (1 < val.length ) {
            //清除所有选择项
            this.$refs.multipleTable.clearSelection();
            //勾选最后一个点击的项目
            this.multipleSelection = val.pop();
            this.$refs.multipleTable.toggleRowSelection(this.multipleSelection)
          } else {
            this.multipleSelection = val
          }
          if(this.multipleSelection.length === 1) {
            this.isEnabled = this.multipleSelection[0].status;
            if (this.isEnabled === 1) {
              this.isEnabled = false;
              console.log(this.isEnabled + 1);
            } else {
              this.isEnabled = true;
            }
          }else {
            this.isEnabled = true;
            console.log(this.isEnabled);
          }
          console.log(this.multipleSelection);
        },

        //  导出
        exportRole() {

        },
        //  新增
        createRole() {
          this.$router.push({path: '/role/management/detail', query: {type: "add"}})
        },
        //检查选中记录，修改、删除、离职、恢复都限制只能选中一条进行操作
        checkChoiceNum() {
          let msg = '';
          if (this.multipleSelection.length === 0) {
            msg = '请选中一条记录进行操作！'

          } else if (this.multipleSelection.length > 1) {
            msg = '只能选中一条记录进行操作!'

          } else {
            return true
          }
          //弹窗提示
          this.$alert(msg, '提示', {
            confirmButtonText: '确定',
          });
        },
        //  修改
        updateRole() {
          if (this.checkChoiceNum()) {
            //跳转
            this.$router.push({
              path: '/role/management/detail',
              query: {roleId: this.multipleSelection[0].roleId,roleName: this.multipleSelection[0].roleName, type: "update"}
            })
          }

        },
        //  删除
        deleteRole() {
          if (this.checkChoiceNum()) {
            this.$confirm('确定删除角色？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              if(this.multipleSelection[0].status === 1) {
              this.$axios.get('/api/orgmanagement/role/management/deleteRole',
                {
                  params: {roleId: this.multipleSelection[0].roleId},
                  Date:new Date()
                }).then(res => {
                //调用删除员工接口返回消息
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                });
                this.search();
              }).catch(() => {
                this.$message.info('已取消删除')
              });}else {
                this.$message.info('删除失败,目标为无效状态');
              }

            })
          }
        },

        addAccount() {
          if (this.checkChoiceNum()) {
            //跳转
            this.$router.push({
              path: '/role/management/addRoleAccount',
              query: {roleId: this.multipleSelection[0].roleId,roleName: this.multipleSelection[0].roleName}
            })
          }
        },
        //  分配权限
        allocatePermission() {
          if (this.checkChoiceNum()) {
            //跳转
            //this.$router.push('/employee/detail')
            this.$router.push({
              path: '/role/management/permission',
              query: {roleId: this.multipleSelection[0].roleId,roleName: this.multipleSelection[0].roleName, type: "update"}
            })
          }
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
        // 导出
        fileExport() {
          this.exportDialogVisible = true;
          this.sonChecked = []; //控制第二次弹窗时清除数据
        },
        //获取导出数据并写入文件
        confirm() {
          debugger
          this.$axios.get('/api/orgmanagement/role/management/exportRoleList',
            {
              params:
                {
                  role: this.role,
                  Date:new Date()
                }
            })
          //then获取成功；response成功后的返回值（对象）
            .then(response => {
              if (response.data.code === 200) {
                this.exportData= response.data.data;
                this.writeFile();
              } else {
                this.$message(response.data.msg);
              }
            })
            //获取失败
            .catch(error => {
              this.$message('网络错误，不能访问，请联系管理员');
            })
        },
        //从子窗口获取选中的字段
        getCheckList(val) {
          this.checkedList = val;
        },
        //确认导出
        writeFile() {
          let checked = this.checkedList;
          let array = new Array();
          //选中字段则导出，否则提示选择
          if (checked.length > 0) {
            for (let i = 0; i < checked.length; i++) {
              switch (checked[i]) {
                case "角色ID":
                  array[i] = "roleId";
                  break;
                case "角色名称":
                  array[i] = "roleName";
                  break;
                case "审批人账号":
                  array[i] = "approveAccount";
                  break;
                case "审批人员工编号":
                  array[i] = "approveId";
                  break;
                case "审批人姓名":
                  array[i] = "approveName";
                  break;
                case "审批人所属部门":
                  array[i] = "approveDepartment";
                  break;
                case "角色状态":
                  array[i] = "status";
                  break;
                case "描述":
                  array[i] = "description";
                  break;
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
            })
          } else {
            this.$message('请选择要导出的字段！');
          }
        },
        // 处理导出数字处理
        generate(data) {
          if(data.length>0) {
            for(let i =0;i<data.length;i++) {
              if(0 === data[i].status) {
                data[i].status = '无效';
              }else{
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
          } else {
            this.sonChecked = [];
            this.isAll = true;
          }
        },
        //导出窗口关闭
        dialogClose() {
          this.exportDialogVisible = false;
          this.isAll = true;
        },
        // 处理单元格点击事件,点击角色ID显示员工详情
        handleCellClick(row,column){
          if("角色ID" === column.label){
            this.$router.push(
              {
                path:'/role/management/roleDetail',
                query:{roleId:row.roleId}
              });
          }
        }
      }
    }
</script>

<style scoped>
  a {
    text-decoration:none;
  }
</style>
