<template>
  <el-row class="list-container">

    <!--搜索条件-->
    <el-col>
      <el-form :inline="true" :model="companyForm" label-width="80px">
        <el-form-item label="企业名称">
          <el-input v-model="companyForm.coName" clearable></el-input>
        </el-form-item>
        <el-form-item label="统一社会信用代码">
          <el-input v-model="companyForm.coCreditCode" clearable></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="companyForm.coType" placeholder="" class="select-width">
            <el-option label="全部" value=""></el-option>
            <el-option label="有限责任公司" value="1"></el-option>
            <el-option label="股份有限公司" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公司性质">
          <el-select v-model="companyForm.coNature" placeholder="" class="select-width">
            <el-option label="全部" value=""></el-option>
            <el-option label="小规模纳税人" value="1"></el-option>
            <el-option label="一般纳税人" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="companyForm.status" placeholder="" class="select-width">
            <el-option label="全部" value=""></el-option>
            <el-option label="有效" value="1"></el-option>
            <el-option label="无效" value="0"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="修改时间">
          <el-date-picker
            v-model="companyForm.startTime"
            type="datetime"
            placeholder="选择开始时间">
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            v-model="companyForm.endTime"
            type="datetime"
            placeholder="选择结束时间">
          </el-date-picker>
        </el-form-item>

      </el-form>
    </el-col>

    <el-col :offset="19" :span="5">
      <el-button type="primary" icon="el-icon-search" @click="getSearchCompany">查询</el-button>
    </el-col>
    <el-col :offset="1" :span="23" class="margin-top20">
      <el-button icon="el-icon-edit-outline" @click="onSubmit" round>确认选择</el-button>
      <el-button icon="el-icon-edit-outline" @click="cancel" round>取消</el-button>
    </el-col>

    <!--展示列表-->
    <el-col class="margin-top20">
      <el-table
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
          prop="id"
          label="编号">
        </el-table-column>
        <el-table-column
          prop="coName"
          label="公司名称">
        </el-table-column>
        <el-table-column
          prop="coCreditCode"
          label="统一社会信用代码">
        </el-table-column>
        <el-table-column
          prop="coType"
          label="类型">
          <template slot-scope="scope">
            <span v-if="scope.row.coType === 0">股份有限公司</span>
            <span v-else-if="scope.row.coType === 1">有限责任公司</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="coNature"
          label="公司性质">
          <template slot-scope="scope">
            <span v-if="scope.row.coNature === 0">一般纳税人</span>
            <span v-else-if="scope.row.coNature === 1">小规模纳税人</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="coMark"
          label="总公司标志">
          <template slot-scope="scope">
            <span v-if="scope.row.coMark === 0">总公司</span>
            <span v-else-if="scope.row.coMark === 1">子公司</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态">
          <template slot-scope="scope">
            <span v-if="scope.row.status === 0">无效</span>
            <span v-else-if="scope.row.status === 1">有效</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="modifyEmpName"
          label="修改人">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.modifyEmpName }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="modifyTime"
          label="修改时间">
        </el-table-column>
      </el-table>
    </el-col>

    <el-col :span="20" class="margin-top20 margin-left20">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page=page.currentPage
        :page-sizes="[10, 20, 30, 40]"property=""
        :page-size=page.pageSize
        layout="total, sizes, prev, pager, next, jumper"
        :total=page.total>
      </el-pagination>
    </el-col>

  </el-row>
</template>

<script>
  export default {
    name: "add_associated_co_list",
    data(){
      return {
        isCurrentPageLink:false,
        companyForm:{
          coName:"",
          coCreditCode:"",
          coType:"",
          coNature:"",
          status:"",
          startTime:"",
          endTime:""
        },
        tableData: [],
        oldTableData:[],
        newTableData:[],
        multipleSelection: [],
        page: {
          total: 10,
          currentPage: 1,
          pageSize: 10
        },
      }
    },
    mounted: function () {
      this.loadTable();
    },
    methods:{
      onSubmit(){
        this.isCurrentPageLink=true;
        this.$confirm('确定选择？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.newTableData=this.oldTableData.concat(this.multipleSelection);
          this.$router.push({
            name:'associated_co_list',
            params:{
              deptId:this.deptId,
              deptName:this.deptName,
              tableData:this.newTableData
            }
          })
        }).catch(() => {});
      },
      cancel(){
        this.isCurrentPageLink=true;
        this.$confirm('确定取消？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$router.push({
            name:'associated_co_list',
            params:{
              deptId:this.deptId,
              deptName:this.deptName,
              tableData:this.oldTableData
            }
          })
        }).catch(() => {});

      },

      handleSizeChange(val) {
        this.page.pageSize = val;
        this.tableData=this.getTableData();
        console.log("改变每页数，tableData：",this.tableData);
      },
      //  显示第几页数据
      handleCurrentChange(val) {
        this.page.currentPage = val;
        this.tableData=this.getTableData();
        console.log("显示相应页数，tableData：",this.tableData);
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      getTableData(){
        let arrData=[];
        let beginIndex = (this.page.currentPage-1)*this.page.pageSize;
        let endIndex=this.page.currentPage*this.page.pageSize-1;
        if(endIndex >= this.newTableData.length){
          endIndex = this.newTableData.length - beginIndex ;
        }else{
          endIndex = this.page.pageSize;
        }
        for (let i=0; i < endIndex; i++) {
          arrData.push(this.newTableData[i+beginIndex]);
        }
        return arrData;
      },
      checkChoiceNum() {
        if (this.multipleSelection.length === 0) {
          //弹窗提示
          this.$alert('至少需要选中选中一条记录！', '提示', {
            confirmButtonText: '确定',
          });
        } else {
          return true
        }
      },

      loadTable() {
        this.deptId=this.$route.params.deptId;
        this.deptName=this.$route.params.deptName;
        this.oldTableData=this.$route.params.tableData;
        this.getSearchCompany();
      },
      removeRepeat(data1,data2){
        let newTableData=[];
        for(let item1 of data1){
          let flag=true;
          for(let item2 of data2){
            if(item1.id===item2.id){
              flag=false;
            }
          }
          if(flag){
            newTableData.push(item1);
          }
        }
        return newTableData;
      },
      getSearchCompany(){
        console.log("发送获取公司信息的请求");
        this.$axios.get('/api/orgmanagement/company/management/getCompanyList',
          {
            params:
              {
                pageNum: 0,
                pageSize: 0,
                companyForm: this.companyForm,
                date: new Date().getTime()
              }
          })
          .then(response => {
            if (response.data.code === 200) {//then获取成功；response成功后的返回值（对象）
              this.newTableData = this.removeRepeat(response.data.data,this.oldTableData);
              this.page.total = this.newTableData.length;
              this.tableData=this.getTableData();
              console.log("后台获取的table", this.tableData);
            } else {
              this.$message(response.data.msg);
            }
          })
          .catch(error => {//获取失败
            this.$message('网络错误，不能访问，请联系管理员');
          })
      }
    },
    // 组件内导航钩子，处理未保存退出的情况
    beforeRouteLeave: function(to, from , next){
      if(this.isCurrentPageLink===false  && to.path !=="/"){
        next(false)
        this.$confirm('是否离开当前页面?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 选择确定
          next()
        })
      }else{
        next();
      }
    },
  }
</script>

<style scoped>

</style>
