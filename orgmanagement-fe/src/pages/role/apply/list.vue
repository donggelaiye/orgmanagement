<template>
  <el-row class="list-container">
    <!--搜索条件-->
    <el-col>
      <el-form :inline="true" ref="roleApplyForm" :rules="searchRules" :model="roleApplyForm" label-width="110px">
        <el-form-item label="角色申请编号" prop="applyCode">
          <el-input v-model="roleApplyForm.applyCode" clearable></el-input>
        </el-form-item>
        <el-form-item label="申请角色ID" prop="roleId">
          <el-input v-model="roleApplyForm.roleId" clearable></el-input>
        </el-form-item>
        <el-form-item label="申请角色名称" prop="roleName">
          <el-input v-model="roleApplyForm.roleName" clearable></el-input>
        </el-form-item>
        <el-form-item label="申请登录人账号" prop="applyAccountLogin">
          <el-input v-model="roleApplyForm.applyAccountLogin" clearable></el-input>
        </el-form-item>
        <el-form-item label="申请人员工编号" prop="applyEmpId">
          <el-input v-model="roleApplyForm.applyEmpId" clearable></el-input>
        </el-form-item>
        <el-form-item label="申请人员工姓名" prop="applyEmpName">
          <el-input v-model="roleApplyForm.applyEmpName" clearable></el-input>
        </el-form-item>
        <el-form-item label="所属部门" prop="applyDept">
          <el-input v-model="roleApplyForm.applyDept"></el-input>
          <el-button @click="departDialogVisible = true">选择</el-button>
        </el-form-item>

        <!--        申请时间 开始到结束-->
        <el-form-item label="申请时间">
          <div class="block">
            <el-date-picker
              v-model="applyTimePicker"
              type="datetimerange"
              :picker-options="pickerOptions"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              align="right">
            </el-date-picker>
          </div>
        </el-form-item>
        <!--        操作时间 开始到结束-->
        <el-form-item label="操作时间">
          <div class="block">
            <el-date-picker
              v-model="operateTimePicker"
              type="datetimerange"
              :picker-options="pickerOptions"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              align="right">
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="roleApplyForm.status" placeholder="" class="select-width">
            <el-option v-for="option in statusOptions" :label="option.label" :value="option.value"
                       v-bind:key="option.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="float: right;">
          <el-button v-if="(buttonPermission.indexOf('exportApply') > -1)" type="primary" icon="el-icon-document"
                     @click="fileExport">导出
          </el-button>
        </el-form-item>
        <el-form-item style="float: right;">
          <el-button type="primary" icon="el-icon-search" @click="search('roleApplyForm')">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--操作按钮-->
    <el-col :offset="19" :span="5">

    </el-col>
    <el-col :offset="1" :span="23" class="margin-top20">
      <el-button v-if="(buttonPermission.indexOf('addApply') > -1)" type="primary" icon="el-icon-edit-outline"
                 @click="addRoleApply" round>新建
      </el-button>
      <el-button
        v-if="(buttonPermission.indexOf('updateApply') > -1)&&this.multipleSelection.length <= 1 && this.isAllNewApply"
        type="primary" icon="el-icon-edit-outline"
        @click="updateRoleApply" round>修改
      </el-button>
      <el-button v-else-if="(buttonPermission.indexOf('updateApply') > -1)" type="info" icon="el-icon-edit-outline"
                 @click="updateRoleApply" round>修改
      </el-button>

      <el-button v-if="(buttonPermission.indexOf('deleteApply') > -1 && this.isAllNewApply)" type="primary"
                 icon="el-icon-edit-outline"
                 @click="deleteRoleApply" round>删除
      </el-button>
      <el-button v-else-if="(buttonPermission.indexOf('deleteApply') > -1)" type="info" icon="el-icon-edit-outline"
                 @click="deleteRoleApply" round>删除
      </el-button>
      <el-button v-if="(buttonPermission.indexOf('deleteApply') > -1)&&this.isAllNewApply" type="primary"
                 icon="el-icon-edit-outline"
                 @click="submitApprove" round>提交审核
      </el-button>
      <el-button v-else-if="buttonPermission.indexOf('deleteApply') > -1" type="info" icon="el-icon-edit-outline"
                 @click="submitApprove" round>提交审核
      </el-button>
    </el-col>
    <!--展示列表-->
    <el-col class="margin-top20">
      <el-table v-loading="listLoading"
                ref="multipleTable"
                :data="tableData"
                :highlight-current-row="true"
                @row-click="rowClick"
                @selection-change="handleSelectionChange"
                @cell-click="handleCellClick"
                class="table—style" border>
        <el-table-column
          type="selection"
          prop="id"
          width="55">
        </el-table-column>
        <el-table-column
          prop="applyCode"
          label="角色申请编号"
          width="143">
          <template slot-scope="scope">
            <el-link type="primary">{{scope.row.applyCode}}</el-link>
          </template>
        </el-table-column>
        <el-table-column
          prop="roleId"
          label="申请角色ID"
          width="93">
          <template slot-scope="scope">
            <el-link type="primary">{{scope.row.roleId}}</el-link>
          </template>
        </el-table-column>
        <el-table-column
          width="92"
          prop="roleName"
          label="申请角色名称">
        </el-table-column>
        <el-table-column
          prop="roleApproveName"
          label="角色审批负责人">
        </el-table-column>
        <el-table-column
          width="198"
          prop="bussinessLine"
          label="角色支持业务线">
        </el-table-column>
        <el-table-column
          prop="applyAccountLogin"
          label="申请人登录账号">
        </el-table-column>
        <el-table-column
          width="90"
          prop="applyEmpId"
          label="申请人员工编号">
        </el-table-column>
        <el-table-column
          width="106"
          prop="applyEmpName"
          label="申请人员工姓名">
        </el-table-column>
        <el-table-column
          prop="applyDept"
          label="申请人所属部门">
        </el-table-column>
        <el-table-column
          width="152"
          prop="createTime"
          label="申请时间">
        </el-table-column>
        <el-table-column
          width="78"
          prop="status"
          label="状态">
          <template slot-scope="scope">
            <span v-if="scope.row.status === 0" v-model="scope.row.status">已新建</span>
            <span v-if="scope.row.status === 1" v-model="scope.row.status">待审批</span>
            <span v-if="scope.row.status === 2" v-model="scope.row.status">审批通过</span>
            <span v-if="scope.row.status === 3" v-model="scope.row.status">审批拒绝</span>
            <span v-if="scope.row.status === 4" v-model="scope.row.status">已删除</span>
          </template>
        </el-table-column>
        <el-table-column
          width="124"
          prop="operateEmp"
          label="操作人">
        </el-table-column>
        <el-table-column
          width="152"
          prop="operateTime"
          label="操作时间">
        </el-table-column>
        <el-table-column
          width="152"
          prop="approveComment"
          label="拒绝原因">
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
    <el-dialog ref="exportDialog" title="请选择导出条件" :before-close="beforeExportClose" :visible.sync="exportDialogVisible">
      <exportFile v-on:checkInfo="getCheckList" :checkOptions="roleApplyOptions"
                  :sonChecked="sonChecked"></exportFile>
      <el-row>
        <el-col :offset="10" :span="14" class="margin-top20">
          <el-button icon="el-icon-edit-outline" @click="selectAll" round>
            <span v-if="isSelectAllExport">全选</span>
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
  import singleTree from '@/components/tree/singleTree';
  import '@/axios/api';
  import exportFile from '@/pages/common/exportFile';
  import {exportDate1} from '@/constant/exportDate';

  export default {
    name: "role_apply",
    components: {
      singleTree,
      exportFile,
    },
    data() {
      return {
        roleApplyForm: {
          applyCode: '',
          roleId: '',
          roleName: '',
          applyAccountLogin: '',
          applyEmpId: '',
          applyEmpName: '',
          applyDept: '',
          status: 5,
          applyTimeStart: '',
          applyTimeEnd: '',
          operateTimeStart: '',
          operateTimeEnd: '',

        },
        // 时间格式是这样的
        // applyTimePicker:[new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)]
        applyTimePicker: '',
        operateTimePicker: '',
        statusName: '',
        tableData: [{
          applyCode: '',
          roleId: '',
          roleName: '',
          roleApproveName: '',
          bussinessLine: '',
          applyAccountLogin: '',
          applyEmpId: '',
          applyEmpName: '',
          applyDept: '',
          createTime: '',
          status: '',
          operateEmp: '',
          operateTime: '',
          approveComment: '',
        }],
        listLoading: false,
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        multipleSelection: [],
        treeData: [],
        departDialogVisible: false,
        superDepartDialogVisible: false,
        exportDialogVisible: false,
        roleApplyOptions: ['角色申请编号', '申请角色ID', '申请角色名称', '角色审批负责人', '角色支持业务线', '申请人登录账号',
          '申请人员工编号', '申请人员工姓名', '申请人所属部门', '申请时间', '状态', '操作人', '操作时间'], //传显示导出字段的数据到子窗口
        sonChecked: [],
        currentChecked: [],
        isSelectAllExport: true,
        exportData: [],
        checkId: '', //由子组件树状结构传来的部门id和名称
        checkVal: '',
        statusOptions: [
          {value: 0, label: '已新建'},
          {value: 1, label: '待审批'},
          {value: 2, label: '审批通过'},
          {value: 3, label: '审批拒绝'},
          {value: 4, label: '已删除'},
          {value: 5, label: '全部'},
        ],
        searchRules: {
          applyCode: [
            {min: 0, max: 15, message: '仅支持0-15位编码', trigger: 'blur'},
            {pattern: /SQ[0-9]*$/, min: 0, max: 15, message: '需要SQ开头，其余都为数字', trigger: 'blur'},
          ],
          roleId: [
            {pattern: /^[0-9]*$/, message: '请填入数字', trigger: 'blur'},
            {min: 0, max: 19, message: '长度要小于19位数字', trigger: 'blur'},
          ],
          roleName: [
            {min: 0, max: 20, message: '长度要小于30个字符', trigger: 'blur'},
          ],
          applyAccountLogin: [
            {min: 0, max: 15, message: '长度要小于15个字符', trigger: 'blur'},
          ],
          applyEmpId: [
            {pattern: /^[0-9]*$/, min: 0, max: 20, message: '仅支持1-20位数字', trigger: 'blur'},
          ],
          applyEmpName: [
            {min: 0, max: 30, message: '长度要小于30个字符', trigger: 'blur'},
          ],
          applyDept: [
            {min: 0, max: 40, message: '长度要小于40个字符', trigger: 'blur'},
          ],
        },
        page: {
          total: 10,
          currentPage: 1,
          pageSize: 10
        },
        buttonPermission: {},

      }
    },
    computed: {
      // 判断选中的数据的状态是否都是已新建
      isAllNewApply: function () {
        for (let i = 0; i < this.multipleSelection.length; i++) {
          if (this.multipleSelection[i].status != 0) {
            return false;
          }
        }
        return true;
      }
    },
    created: function () {
      this.search();
      this.getTreeData();
      this.buttonPermission = JSON.parse(sessionStorage.getItem("buttonList")).roleApply;
    },
    methods: {
      //  新增
      addRoleApply() {
        this.$router.push({path: '/role/apply/applyInfo', query: {type: "add"}})
      },
      //  修改
      updateRoleApply() {
        if (this.checkChoiceNum()) {
          if (!this.isAllNewApply) {
            this.$alert("只能修改状态为已新建的角色申请", '提示', {
              confirmButtonText: '确定',
            });
          } else {
            this.$router.push({
              path: '/role/apply/applyInfo',
              query: {applyCode: this.multipleSelection[0].applyCode, type: "update"}
            })
          }
        }
      },
      search(type) {
        if (type !== 'page') {
          this.page.currentPage = 1;
        }
        this.getTimeFromTimePicker();
        this.listLoading = true;
        this.$axios.post('/api/orgmanagement/role/apply/getApplyList',
          {
            pageNum: this.page.currentPage,
            pageSize: this.page.pageSize,
            roleApplyForm: JSON.stringify(this.roleApplyForm)
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
            if (response.data.code == 200) {

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
      handleSelectionChange(row) {
        if (row.length === 0) {
          this.multipleSelection = row;
          return;
        }
        if (row.length > 1) {
          //清除所有选择项
          this.$refs.multipleTable.clearSelection();
          //勾选最后一个点击的项目
          this.multipleSelection = row.pop();
          this.$refs.multipleTable.toggleRowSelection(this.multipleSelection)
        } else {
          this.multipleSelection = row
        }

      },

      //  删除
      deleteRoleApply() {
        if (this.multipleSelection.length === 0) {
          console.log("没有数据")
          this.$alert("请选择数据", '提示', {
            confirmButtonText: '确定',
          });
          return;
        }
        if (!this.isAllNewApply) {
          this.$alert("只能删除状态为已新建的数据", '提示', {
            confirmButtonText: '确定',
          });
          return;
        }
        let msg = '确定要删除选中的' + this.multipleSelection.length + '条数据吗？';
        this.$confirm(msg, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.doDeleteRoleApply(this.multipleSelection);
        }).catch(() => {

        });
      },
      //检查选中记录，修改、删除、离职、恢复都限制只能选中一条进行操作
      checkChoiceNum() {
        let msg = '';
        if (this.multipleSelection.length == 0) {
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

      async doDeleteRoleApply(deleteItems) {
        this.listLoading = true;
        await this.$axios.post('/api/orgmanagement/role/apply/deleteApplys',
          {
            deleteItems: deleteItems
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
            if (response.data.code == 200) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              // this.tableData = response.data.data.pageData;
              this.search()

            } else {
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
      //  提交审核
      submitApprove() {

        if (this.multipleSelection.length === 0) {

          console.log("没有数据")
          this.$alert("请选择数据", '提示', {
            confirmButtonText: '确定',
          });
          return;
        }
        if (!this.isAllNewApply) {
          this.$alert("请选择状态为已新建的角色申请", '提示', {
            confirmButtonText: '确定',
          });
        } else {
          let msg = '确定要提交选中的' + this.multipleSelection.length + '条数据吗？';
          this.$confirm(msg, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.listLoading = true;
            this.doSubmitApprove(this.multipleSelection);

          }).catch(() => {

          });
        }
      },
      rowClick(row) {
        this.$refs.multipleTable.toggleRowSelection(row)
      },
      doSubmitApprove(approveItems) {
        this.$axios.post('/api/orgmanagement/role/apply/approveApplys',
          {
            approveItems: approveItems
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
            if (response.data.code == 200) {
              this.$message({
                type: 'success',
                message: '提交审核成功!'
              });
              this.search()
            } else {
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
      checkApprove() {
        this.$alert("只能选择状态为已新建的角色申请", '提示', {
          confirmButtonText: '确定',
        });
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
        this.roleApplyForm.applyDept = data.split("#")[1];
        this.departDialogVisible = false
      },
      getTimeFromTimePicker() {
        if (this.applyTimePicker) {
          this.roleApplyForm.applyTimeStart = this.applyTimePicker[0] ? this.applyTimePicker[0] : null
          this.roleApplyForm.applyTimeEnd = this.applyTimePicker[1] ? this.applyTimePicker[1] : null
        } else {
          this.roleApplyForm.applyTimeStart = null
          this.roleApplyForm.applyTimeEnd = null
        }
        if (this.operateTimePicker) {
          this.roleApplyForm.operateTimeStart = this.operateTimePicker[0] ? this.operateTimePicker[0] : null;
          this.roleApplyForm.operateTimeEnd = this.operateTimePicker[1] ? this.operateTimePicker[1] : null
        } else {
          this.roleApplyForm.operateTimeStart = null;
          this.roleApplyForm.operateTimeEnd = null;
        }
      },
      // 导出
      fileExport() {
        this.exportDialogVisible = true;
        this.sonChecked = []; //控制第二次弹窗时清除数据
      },
      //获取导出数据并写入文件
      confirm() {
        this.getTimeFromTimePicker();
        this.$axios.post('/api/orgmanagement/role/apply/exportApplyList',
          {
            roleApplyForm: this.roleApplyForm
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
            if (response.data.code === 200) {
              this.exportData = response.data.data;
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
        this.currentChecked = val;
      },
      //确认导出
      writeFile() {
        let checked = this.currentChecked;
        let array = new Array();
        //选中字段则导出，否则提示选择
        if (checked.length > 0) {
          for (let i = 0; i < checked.length; i++) {
            switch (checked[i]) {
              case "角色申请编号":
                array[i] = "applyCode";
                break;
              case "申请角色ID":
                array[i] = "roleId";
                break;
              case "申请角色名称":
                array[i] = "roleName";
                break;
              case "角色审批负责人":
                array[i] = "roleApproveName";
                break;
              case "角色支持业务线":
                array[i] = "bussinessLine";
                break;
              case "申请人登录账号":
                array[i] = "applyAccountLogin";
                break;
              case "申请人员工编号":
                array[i] = "applyEmpId";
                break;
              case "申请人员工姓名":
                array[i] = "applyEmpName";
                break;
              case "申请人所属部门":
                array[i] = "applyDept";
                break;
              case "申请时间":
                array[i] = "createTime";
                break;
              case "状态":
                array[i] = "status";
                break;
              case "操作人":
                array[i] = "operateEmp";
                break;
              case "操作时间":
                array[i] = "operateTime";
                break;
            }
          }
          //excel数据导出
          require.ensure([], () => {
            const {
              export_json_to_excel
            } = require('@/excel/Export2Excel');
            const tHeader = checked;
            const filterVal = array;
            const list = this.generate(this.exportData);
            const data = this.formatJson(filterVal, list);
            let excelName = checked.toString() + " " + exportDate1();
            export_json_to_excel(tHeader, data, excelName);
            this.$message('导出成功，请关注下载文件！');
            this.dialogClose();
          })
        } else {
          this.$message('请选择要导出的字段！');
        }

      },
      beforeExportClose() {
        this.dialogClose();
      },
      generate(data) {
        if (data.length > 0) {
          for (let i = 0; i < data.length; i++) {
            switch (data[i].status) {
              case 0:
                data[i].status = '已新建';
                break;
              case 1:
                data[i].status = '待审批';
                break;
              case 2:
                data[i].status = '审批通过';
                break;
              case 3:
                data[i].status = '审批拒绝';
                break;
              case 4 :
                data[i].status = '已删除';
                break;
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
        if (this.isSelectAllExport) {
          this.sonChecked = this.roleApplyOptions;
          this.isSelectAllExport = false;
        } else {
          this.sonChecked = [];
          this.isSelectAllExport = true;
        }
      },
      //导出窗口关闭
      dialogClose() {
        this.exportDialogVisible = false;
        this.isSelectAllExport = true;
      },

      getTreeData() {
        if (sessionStorage.getItem('deptTree')) {
          this.treeData = JSON.parse(sessionStorage.getItem('deptTree'));
        }
      },
      // 清楚部门选择数据
      clearDepartInfo(val) {
        this.roleApplyForm.applyDept = '';
        // this.empSearchForm.empDepartName = '';
        this.departDialogVisible = val;
      },
      // 处理单元格点击事件,点击角色ID显示角色详情
      handleCellClick(row, column) {
        if ("角色申请编号" === column.label) {
          this.$router.push(
            {
              path: '/role/apply/detail',
              query: {applyCode: row.applyCode}
            });
        } else if ("申请角色ID" === column.label) {
          this.$router.push(
            {
              path: '/role/management/roleDetail',
              query: {roleId: row.roleId}
            });
        }
      }
    }
  }
</script>

<style scoped>
  a {
    text-decoration: none;
  }
</style>
