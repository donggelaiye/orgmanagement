<template>
  <el-row class="list-container">

    <!--搜索条件-->
    <el-col>
      <el-form :inline="true" :model="areaForm" label-width="80px">
        <el-form-item label="国际代码">
          <el-input v-model="areaForm.interCode" clearable></el-input>
        </el-form-item>
        <el-form-item label="省/市">
          <el-input v-model="areaForm.provinceName" clearable></el-input>
        </el-form-item>
        <el-form-item label="城市">
          <el-input v-model="areaForm.cityName" clearable></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="areaForm.status" placeholder="" class="select-width">
            <el-option label="全部" value=""></el-option>
            <el-option label="有效" value="1"></el-option>
            <el-option label="无效" value="0"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </el-col>

    <!--操作按钮-->
    <el-col :offset="19" :span="5">
      <el-button v-if="buttonPermission.indexOf('cityList') > -1" type="primary" icon="el-icon-search" @click="loadTable">查询</el-button>
      <el-button v-if="buttonPermission.indexOf('exportCity') > -1" type="primary" icon="el-icon-document" @click="fileExport" >导出</el-button>
    </el-col>
    <el-col :offset="1" :span="23" class="margin-top20">
      <el-button v-if="buttonPermission.indexOf('deleteCity') > -1" type="primary" icon="el-icon-edit-outline" @click="deleteCity" :disabled="isBtnDelete" round>删除</el-button>
    </el-col>

    <!--展示列表-->
    <el-col class="margin-top20">
      <el-table
        v-loading="isLoading"
        ref="multipleTable"
        :data="tableData"
        :highlight-current-row="true"
        @selection-change="handleSelectionChange"
        class="table—style" border>
        <el-table-column
          type="selection"
          prop="id"
          width="55">
        </el-table-column>
        <el-table-column
          prop="interCode"
          label="国际代码">
        </el-table-column>
        <el-table-column
          prop="provinceName"
          label="省份名称">
        </el-table-column>
        <el-table-column
          prop="cityName"
          label="城市名称">
        </el-table-column>
        <el-table-column
          prop="cityPhonetic"
          label="城市拼音">
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          width="50">
          <template slot-scope="scope">
            <span v-if="scope.row.status === 0">无效</span>
            <span v-else="scope.row.status === 1">有效</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="modifyEmpName"
          label="修改人">
        </el-table-column>
        <el-table-column
          prop="modifyTime"
          label="修改时间">
        </el-table-column>
      </el-table>
    </el-col>

    <!--分页-->
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
  import exportFile from '@/pages/common/exportFile'
  import '@/axios/api'
  export default {
    title: "list",
    components: {
      exportFile
    },
    data() {
      return {
        areaForm: {
          interCode: '',
          cityName: '',
          provinceName: '',
          countyName: '',
          status: '',
        },
        isAll: true, //设置导出弹窗字段全选或取消全选
        exportDialogVisible:false, //导出弹窗
        sonChecked: [],
        exportData:[],
        roleOptions: ['国际代码', '省份名称', '城市名称', '城市拼音', '状态', '修改时间', '修改人'], //传显示导出字段的数据到子窗口
        tableData: [],
        multipleSelection: [],
        page: {
          total: 10,
          currentPage: 1,
          pageSize: 10
        },
        isLoading:false,
        buttonPermission: [],
        isBtnDelete: true
      }
    },
    created: function () {
      this.loadTable();
      this.buttonPermission = JSON.parse(sessionStorage.getItem("buttonList")).area;
    },
    methods: {
      fileExport() {
        this.exportDialogVisible = true;
        this.sonChecked = []; //控制第二次弹窗时清除数据
      },
      getCheckList(val){
        this.checkedList = val;
      },
      writeFile() {
        let checked = this.checkedList;
        let array = new Array();
        //选中字段则导出，否则提示选择
        if (checked.length > 0){
          for(let i = 0; i < checked.length; i++){
            switch(checked[i]){
              case "国际代码": array[i] = "interCode";break;
              case "省份名称": array[i] = "provinceName";break;
              case "城市名称": array[i] = "cityName";break;
              case "城市拼音": array[i] = "cityPhonetic";break;
              case "状态": array[i] = "status";break;
              case "修改时间": array[i] = "modifyTime";break;
              case "修改人": array[i] = "modifyEmp";break;
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
            let excelName = checked.toString() + new Date().toLocaleDateString();
            export_json_to_excel(tHeader, data, excelName);
            this.$message('导出成功，请关注下载文件！');
          })
        }else {
          this.$message('请选择要导出的字段！');
        }

      },
      confirm() {
        this.$axios.get('/api/orgmanagement/area/city/getCityList',
          {params:
              {
                areaForm: this.areaForm,
                pageNum: 0,
                pageSize: 0
              }
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response=>{
            if (response.data.code === 200) {
              console.log(response.data.data);
              this.exportData= response.data.data;
              this.writeFile();
              this.dialogClose();
            }else {
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error=>{
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
      formatJson(filterVal, jsonData) {
        console.log(jsonData)
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      selectAll() {
        if (this.isAll) {
          this.sonChecked = this.roleOptions;
          this.isAll = false;
        }else {
          this.sonChecked = [];
          this.isAll = true;
        }
      },
      dialogClose() {
        this.exportDialogVisible = false;
        this.isAll = true;
      },
      handleSizeChange(val) {
        this.page.pageSize = val;
        this.loadTable('page')
      },
      //  显示第几页数据
      handleCurrentChange(val) {
        this.page.currentPage = val;
        this.loadTable('page')
      },
      // 处理按钮权限
      checkBtnAuthority(row){
        if (0 === row.length ) {
          this.isBtnDelete = true;
        }else{
          this.isBtnDelete = false;
        }
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
      generate(data){
        if(data.length>0)
        {
          for(let i =0;i<data.length;i++)
          {
            if(data[i].status == 0)
            {
              data[i].status ="无效";
            }
            else if(data[i].status ==1)
            {
              data[i].status = "有效";
            }
          }
        }
        return data;
      },
      loadTable(type) {
        if (type !== 'page') {
          this.page.currentPage = 1;
        }
        this.isLoading = true;
        this.$axios.get('/api/orgmanagement/area/city/getCityList',
          {
            params:
              {
                pageNum: this.page.currentPage,
                pageSize: this.page.pageSize,
                areaForm: this.areaForm
              }
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
            if (response.data.code == 200) {
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
      //  删除
      deleteCity() {
        if (this.checkChoiceNum()) {
          this.$confirm('确定删除城市？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$axios.get('/api/orgmanagement/area/city/deleteCityById',
              {
                params:
                  {
                    interCode: this.multipleSelection[0].interCode
                  }
              }).then(response => {
              if (response.data.code === 200) {
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                });
                this.loadTable();
              } else {
                this.$message({
                  type: 'error',
                  message: response.data.msg
                });
              }
            }).catch(error => {
              this.$message('网络错误，不能访问，请联系管理员');
            })
          }).catch(() => {
          });
        }
      },
    }
  }
</script>

<style scoped>

</style>
