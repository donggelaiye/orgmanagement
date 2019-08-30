<template>
  <el-row class="list-container">
    <!--搜索条件-->
    <el-col>
      <el-form :inline="true" :model="deptSearchForm" label-width="100px">
        <el-form-item label="部门名称：" >
          <el-input v-model="deptSearchForm.deptName" clearable></el-input>
        </el-form-item>
        <el-form-item label="负责人ID：" >
          <el-input v-model="deptSearchForm.empId" clearable></el-input>
        </el-form-item>
        <el-form-item label="负责人姓名：" >
          <el-input v-model="deptSearchForm.empName" clearable></el-input>
        </el-form-item>
        <el-form-item label="手机号：" >
          <el-input v-model="deptSearchForm.empPhone" clearable></el-input>
        </el-form-item>
        <el-form-item label="所在城市：" >
          <el-autocomplete
            popper-class="my-autocomplete"
            v-model="cityTest"
            :fetch-suggestions="querySearch"
            placeholder="全部"
            @blur="cityChange"
            @focus="getCityList"
            @select="handleSelectCity"
          >
            <template slot-scope="scope">
              <div class="name">{{ scope.item.name }}</div>
            </template>
          </el-autocomplete>
        </el-form-item>
        <el-form-item label="部门级别：" >
          <el-select v-model="deptSearchForm.deptLevel" class="select-width">
            <el-option label="全部" value=9></el-option>
            <el-option label="总部" value=0></el-option>
            <el-option label="分公司" value=1></el-option>
            <el-option label="管理部" value=2></el-option>
            <el-option label="区域" value=3></el-option>
            <el-option label="办公点" value=4></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上级部门:" >
          <el-input v-model="superDeptName" clearable readonly></el-input>
          <el-input v-model="deptSearchForm.superDeptId" clearable style="display: none"></el-input>
          <el-button @click="superDepartDialogVisible = true">选择</el-button>
        </el-form-item>

        <el-form-item label="状态：" >
          <el-select v-model="deptSearchForm.status" clearable>
            <el-option label="全部" value="9" ></el-option>
            <el-option label="有效" value="1" ></el-option>
            <el-option label="无效" value="0"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="部门类型：">
          <el-select v-model="deptSearchForm.deptType">
            <el-option label="全部" value="9"></el-option>
            <el-option label="门店" value="0"></el-option>
            <el-option label="停车场" value="1"></el-option>
            <el-option label="交车中心" value="2"></el-option>
            <el-option label="维修厂" value="3"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </el-col>
    <!--操作按钮-->
    <el-col :offset="19" :span="5">
      <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
      <el-button type="primary" icon="el-icon-document" @click="exportDept" >导出</el-button>
    </el-col>

<!--    展示列表-->
    <el-col class="margin-top20">
      <el-table
        v-loading="isLoading"
        ref="multipleTable"
        :data="tableData"
        :highlight-current-row="true"
        @cell-click="handleCellClick"
        class="table—style" border>
        <el-table-column
          prop="dept.deptId"
          label="部门编号"
          width="80">
          <template slot-scope="scope">
            <el-link type="primary">{{scope.row.dept.deptId}}</el-link>
          </template>
        </el-table-column>
        <el-table-column
          prop="dept.deptName"
          label="部门名称"
          width="160">
        </el-table-column>
        <el-table-column
          prop="dept.empId"
          label="负责人ID"
          width="100">
        </el-table-column>
        <el-table-column
          prop="dept.empName"
          label="负责人姓名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="dept.empPhone"
          label="手机号"
          width="110">
        </el-table-column>
        <el-table-column
          prop="cityName"
          label="所在城市"
          width="80">
        </el-table-column>
        <el-table-column
          prop="dept.deptLevel"
          label="部门级别"
          width="80">
          <template slot-scope="scope">
            <span v-if="scope.row.dept.deptLevel === 0">总部</span>
            <span v-else-if="scope.row.dept.deptLevel === 1">分公司</span>
            <span v-else-if="scope.row.dept.deptLevel === 2">管理部</span>
            <span v-else-if="scope.row.dept.deptLevel === 3">区域</span>
            <span v-else-if="scope.row.dept.deptLevel === 4">办公点</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="dept.deptType"
          label="部门类型"
          width="80">
          <template slot-scope="scope">
            <span v-if="scope.row.dept.deptType === 0">门店</span>
            <span v-else-if="scope.row.dept.deptType === 1">停车场</span>
            <span v-else-if="scope.row.dept.deptType === 2">交车中心</span>
            <span v-else-if="scope.row.dept.deptType === 3">维修厂</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="dept.officeSpot"
          label="办公点标识"
          width="100">
        </el-table-column>
        <el-table-column
          prop="superDeptName"
          label="上级部门"
          width="160">
        </el-table-column>
        <el-table-column
          prop="logogramBussinessLine"
          label="支持业务线"
          width="140">
        </el-table-column>
        <el-table-column
          prop="logogramCompany"
          label="关联公司名称"
          width="200">
        </el-table-column>
        <el-table-column
          prop="dept.status"
          label="状态"
          width="60">
          <template slot-scope="scope">
            <span v-if="scope.row.dept.status === 0">无效</span>
            <span v-else="scope.row.dept.status === 1">有效</span>
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

    <!--分配上级部门弹窗内容-->
    <el-dialog ref="dialog" title="请选择上级部门" :visible.sync="superDepartDialogVisible">
      <singleTree :treeData="treeData" v-on:checkInfo="getSuperDepartInfo" @dialogClose="clearSuperDepartInfo"></singleTree>
    </el-dialog>

    <el-dialog ref="dialog" title="部门详细信息" :visible.sync="detailDialogVisible">
      <DeptDetail :deptInfoDetail="deptInfoDetail"></DeptDetail>
    </el-dialog>

    <!--导出对话框-->
    <el-dialog ref="exportDialog" title="请选择导出条件" :visible.sync="exportDialogVisible" @close="closeExportDialog">
      <exportFile v-on:checkInfo = "getCheckList" :checkOptions = "deptOptions" :sonChecked="sonChecked"></exportFile>
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
  import singleTree from '../../../components/tree/singleTree';
  import '../../../axios/api';
  import DeptDetail from '../department/detail';
  import exportFile from '@/pages/common/exportFile';

  export default {
    name: "dept_search_list",
    components:{
      singleTree,
      DeptDetail,
      exportFile
    },
    data(){
      return{
        // 从后台获取树状结构数据
        treeData:[],
        superDeptName:'',
        deptLevel:'',
        deptType:'',
        status:'',
        deptSearchForm:{
          deptName:'',
          empId:'',
          empName:'',
          empPhone:'',
          cityInterCode:'',
          countyInterCode:'',
          isCity:'',
          deptLevel:'9',
          superDeptId:'',
          status:'9',
          deptType:'9'
        },
        deptExportInfo:{},
        tableData: [],
        deptInfoDetail:{},
        multipleSelection: [],
        detailDialogVisible: false,
        superDepartDialogVisible: false,
        // 由子组件树状结构传来的部门id和名称
        checkId:'',
        checkVal:'',
        page:{
          total:10,
          currentPage:1,
          pageSize:10
        },
        cityList:[],
        restaurants:[],
        cityTest:'',

        exportDialogVisible:false,
        deptOptions:['部门编号','部门名称','负责人ID','负责人姓名',
          '手机号','所在城市','部门级别','部门类型','办公点标识',
          '上级部门','支持业务线','关联公司名称','状态'],
        sonChecked:[],
        exportData:[],
        isAll:true,
        isLoading: false
      }
    },
    created:function(){
      //初始化获取所有有效员工数据
      this.search();
      //初始化获取部门结构数据
      this.getTreeData();
    },
    methods: {
      // 从后台获取部门树状结构数据
      getTreeData() {
        if (sessionStorage.getItem('deptTree')){
          this.treeData = JSON.parse(sessionStorage.getItem('deptTree'));
        }
      },

      // 从子组件中获取到选择的上级部门信息
      getSuperDepartInfo(data) {
        this.deptSearchForm.superDeptId = data.split("#")[0];
        this.superDeptName = data.split("#")[1];
        this.superDepartDialogVisible = false
      },
      // 清除选择上级部门
      clearSuperDepartInfo(val){
        this.deptSearchForm.superDeptId = '';
        this.superDeptName = '';
        this.superDepartDialogVisible = val;
      },

      // 修改分页显示条数
      handleSizeChange(val) {
        this.page.pageSize = val;
        this.search('page');
      },
      // 修改当前页面数
      handleCurrentChange(val) {
        this.page.currentPage = val;
        this.search('page');
      },

      // 处理单元格点击事件,点击员工ID显示员工详情
      handleCellClick(row,column){

        if("部门编号" === column.label){
          this.deptInfoDetail = row ;
          this.checkDeptLevel(this.deptInfoDetail.dept.deptLevel);
          this.deptInfoDetail.dept.deptLevel=this.deptLevel;

          this.checkDeptType(this.deptInfoDetail.dept.deptType);
          this.deptInfoDetail.dept.deptType=this.deptType;

          this.checkStatus(this.deptInfoDetail.dept.status);
          this.deptInfoDetail.dept.status=this.status;

          this.deptInfoDetail.cityList=this.removeRepeat(row.cityList);
          this.detailDialogVisible = true ;
        }
      },
      checkDeptLevel(data){
        if(data===0){
          this.deptLevel="总部";
        }else if(data===1){
          this.deptLevel="分公司";
        }else if(data===2){
          this.deptLevel="管理部";
        }else if(data===3){
          this.deptLevel="区域";
        }else if(data===4){
          this.deptLevel="办公点";
        }else {
          this.deptLevel="";
        }
      },
      checkDeptType(data){
        if(data===0){
          this.deptType="门店";
        }else if(data===1){
          this.deptType="停车场";
        }else if(data===2){
          this.deptType="交车中心";
        }else if(data===3){
          this.deptType="维修厂";
        }else {
          this.deptType="";
        }
      },
      checkStatus(data){
        if(data===0){
          this.status="无效";
        }else if(data===1){
          this.status="有效";
        }else {
          this.status="";
        }
      },

      // 搜索部门方法
      search(type) {
        if (type !== 'page') {
          this.page.currentPage = 1;
        }
        this.isLoading = true;
        this.$axios.get('/api/orgmanagement/department/management/getDeptSearchList',{
          params: {
            pageNum:this.page.currentPage,
            pageSize:this.page.pageSize,
            deptSearchForm:this.deptSearchForm,
            date: new Date().getTime()
          }
        }) .then(response=>{
          if (response.data.code === 200) {
            this.tableData = response.data.data.pageData;
            this.page.total = response.data.data.total;
            this.isLoading = false;
            this.exportData=[];
          }else {
            this.isLoading = false;
            this.$message(response.data.msg);
          }
        }).catch(error=>{
          this.$message('网络错误，不能访问，请联系管理员');
        })
      },

      removeRepeat(data){
        let newData=new Set(data);
        return Array.from(newData);
      },



      getCityList(){//获取城市列表
        if(this.cityList.length<=0){// 使用axios请求数据，将返回值赋给cityList
          this.$axios.get('/api/orgmanagement/area/city/getCityListToDept',{
            params:{date: new Date().getTime()}
          }).then(response => {
              if (response.data.code === 200) {
                this.cityList = response.data.data;
                this.restaurants=this.cityList;
              } else {
                this.$message(response.data.msg);
              }
            })
            .catch(error => {
              this.$message('网络错误，不能访问，请联系管理员');
            })
        }
      },
      querySearch(queryString, cb) {//显示过滤后的内容
        let restaurants = this.restaurants;
        let results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
        // 调用 callback 返回建议列表的数据
        cb(results);
      },
      createFilter(queryString) {//过滤输入的城市搜索内容
        return (restaurant) => {
          return (restaurant.name.toLowerCase().indexOf(queryString.toLowerCase()) !== -1);
        };
      },
      cityChange(){
        if(''===this.cityTest || null== this.cityTest){
          this.deptSearchForm.cityInterCode='';
          this.deptSearchForm.countyInterCode='';
          this.deptSearchForm.isCity='';
        }
      },
      handleSelectCity(item) {
        this.cityTest = item.name;
        this.deptSearchForm.isCity=item.type;
        if(item.type===1){
          this.deptSearchForm.cityInterCode=item.value;
          this.deptSearchForm.countyInterCode='';
        }else if(item.type===2){
          this.deptSearchForm.countyInterCode=item.value;
          this.deptSearchForm.cityInterCode='';
        }else {
          this.$message('异常错误，错误原因：未知');
        }
      },

      // 导出部门数据表
      exportDept() {
        this.exportDialogVisible = true;
        this.sonChecked = []; //控制第二次弹窗时清除数据
      },
      //从子窗口获取选中的字段
      getCheckList(val){
        this.checkedList = val;
      },
      //全选
      selectAll(){
        if (this.isAll) {
          this.sonChecked = this.deptOptions;
          this.isAll = false;
        }else {
          this.sonChecked = [];
          this.isAll = true;
        }
      },
      //确认导出
      confirm(){
        this.$axios.get('/api/orgmanagement/department/management/getDeptList',
          {params:
              {
                deptSearchForm:this.deptSearchForm,
                date: new Date().getTime()
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
      //导出窗口关闭
      dialogClose(){
        this.exportDialogVisible = false;
        this.isAll = true;
      },
      //确认导出
      writeFile() {
        let checked = this.checkedList;
        console.log("导出的字段：",checked);
        let array = new Array();
        //选中字段则导出，否则提示选择
        if (checked.length > 0){
          for(let i = 0; i < checked.length; i++){
            switch(checked[i]){
              case this.deptOptions[0]: array[i] = "deptId";break;
              case this.deptOptions[1]: array[i] = "deptName";break;
              case this.deptOptions[2]: array[i] = "empId";break;
              case this.deptOptions[3]: array[i] = "empName";break;
              case this.deptOptions[4]: array[i] = "empPhone";break;
              case this.deptOptions[5]: array[i] = "cityName";break;
              case this.deptOptions[6]: array[i] = "deptLevel";break;
              case this.deptOptions[7]: array[i] = "deptType";break;
              case this.deptOptions[8]: array[i] = "officeSpot";break;
              case this.deptOptions[9]: array[i] = "superDeptName";break;
              case this.deptOptions[10]: array[i] = "bussinessLineList";break;
              case this.deptOptions[11]: array[i] = "companyList";break;
              case this.deptOptions[12]: array[i] = "status";
            }
          }
          //excel数据导出
          require.ensure([], () => {
            const {
              export_json_to_excel
            } = require('../../../excel/Export2Excel');
            const tHeader = checked;
            const filterVal = array;
            const list = this.exportData;
            console.log("导出的数据",list);
            const data = this.formatJson(filterVal, list);
            let excelName = "部门信息表-" + new Date().toLocaleDateString();
            export_json_to_excel(tHeader, data, excelName);
            this.$message('导出成功，请关注下载文件！');
            this.exportDialogVisible = false;
          })
        }else {
          this.$message('请选择要导出的字段！');
        }

      },

      formatJson(filterVal,jsonData){
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      closeExportDialog() {
        this.isAll = true;
      }
    }
  }
</script>

<style scoped>

</style>
