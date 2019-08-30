<template>
  <el-row class="list-container">

    <el-col :offset="1" :span="23" class="margin-top20">
      <el-button type="primary" icon="el-icon-edit-outline" @click="addCompany" round>添加</el-button>
      <el-button type="primary" icon="el-icon-edit-outline" @click="removeCompany" :disabled="isBtnDelete" round>移除</el-button>
      <el-button type="primary" icon="el-icon-edit-outline" @click="onSubmit" round >保存</el-button>
      <el-button type="primary" icon="el-icon-edit-outline" @click="cancel" round>取消</el-button>
    </el-col>

    <!--展示列表-->
    <el-col class="margin-top50">
      <el-table
        ref="multipleTable"
        :data="tableData"
        :highlight-current-row="true"
        @selection-change="handleSelectionChange"
        :default-sort="{prop:'id',order:'descending'}"
        class="table—style" border>
        <el-table-column
          type="selection"
          prop="id"
          width="55">
        </el-table-column>
        <el-table-column
          prop="id"
          label="编号"
          sortable>
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
            <span v-else="scope.row.coType === 1">有限责任公司</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="coNature"
          label="公司性质">
          <template slot-scope="scope">
            <span v-if="scope.row.coNature === 0">一般纳税人</span>
            <span v-else="scope.row.coNature === 1">小规模纳税人</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="coMark"
          label="总公司标志">
          <template slot-scope="scope">
            <span v-if="scope.row.coMark === 0">总公司</span>
            <span v-else="scope.row.coMark === 1">子公司</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态">
          <template slot-scope="scope">
            <span v-if="scope.row.status === 0">无效</span>
            <span v-else="scope.row.status === 1">有效</span>
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

  </el-row>
</template>

<script>
  export default {
    name: "associated_co_list",
    data(){
      return {
        deptId:'',
        deptName:'',
        tableData: [],
        multipleSelection: [],
        isCurrentPageLink:false,
        page: {
          total: 10,
          currentPage: 1,
          pageSize: 10
        },
        isBtnDelete: true,
      }
    },
    created: function(){
      this.loadTable();
    },
    mounted: function () {
    },
    methods:{
      addCompany(){//添加关联公司
        this.isCurrentPageLink=true;
        this.$router.push({
          name:'add_associated_co_list',
          params:{
            deptId:this.deptId,
            deptName:this.deptName,
            tableData:this.tableData
          }
        })
      },
      removeCompany(){//移除关联公司
        if(this.checkChoiceNum()){
          this.$confirm('确定移除？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.tableData=this.removeData(this.tableData,this.multipleSelection);
          }).catch(() => {});
        }
      },
      onSubmit(){//保存信息
        this.$confirm('确认保存？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.updateRequired();
        }).catch(() => {});
      },
      updateRequired(){
        this.isCurrentPageLink=true;
        this.$axios({
          url:"/api/orgmanagement/department/management/updateDeptCompany",
          method:"post",
          headers: {'Content-Type': 'application/json;charset=utf-8'},
          data:{
            deptId:this.deptId,
            companyList:this.tableData,
            date: new Date().getTime()
          },
        })
          .then(response => {
            if (response.data.code === 200) {
              this.$msgbox({
                title:'提示',
                showCancelButton: false,
                confirmButtonText: '确定',
                type:'success',
                message:'修改关联公司成功！'
              }).then(() => {
                this.$router.push('/department/list');
              }).catch(() => {});
            } else {
              this.$message(response.data.msg);
            }
          })
          .catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
      cancel(){//取消关联公司操作
        this.$confirm('确定取消？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.isCurrentPageLink=true;
          this.$router.push('/department/list');
        }).catch(() => {});
      },
      handleSizeChange(val) {
        this.page.pageSize = val;
        this.search()
      },
      handleCurrentChange(val) {//  显示第几页数据
        this.page.currentPage = val;
        this.search()
      },
      // 处理按钮权限
      checkBtnAuthority(row){
        if (0 === row.length ) {
          this.isBtnDelete = true;
        }else{
          this.isBtnDelete = false;
        }
      },
      handleSelectionChange(val) {
        this.checkBtnAuthority(val);
        this.multipleSelection = val;
        console.log("已选择的公司：", this.multipleSelection);
      },
      checkChoiceNum() {
        if (this.multipleSelection.length === 0) {
          //弹窗提示
          this.$msgbox({
            title:'提示',
            showCancelButton: false,
            confirmButtonText: '确定',
            type:'warning',
            message:'至少需要选中一条记录！'
          });
        } else {
          return true
        }
      },
      loadTable() {
        this.deptId=this.$route.params.deptId;
        this.deptName=this.$route.params.deptName;
        if(this.$route.params.tableData!=null){
          this.tableData=this.$route.params.tableData;
        }else{
          this.getCompanyByDeptId();
        }
      },
      getCompanyByDeptId(){
        this.$axios.get('/api/orgmanagement/company/management/getCompanyByDeptId',
          {
            params:
              {
                deptId:this.deptId,
                pageNum: this.page.currentPage,
                pageSize: this.page.pageSize,
                date: new Date().getTime()
              }
          })
          .then(response => {//then获取成功；response成功后的返回值（对象）
            if (response.data.code === 200) {
              this.tableData = response.data.data.pageData;
              this.page.total = response.data.data.total;
            } else {
              this.$message(response.data.msg);
            }
          })
          .catch(() => {//获取失败
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
      removeData(arr, selectArr){//移除数组中的所选中的某些元素
        for(let item of selectArr){
          for(let i=0;i<arr.length;i++){
            if(arr[i].id===item.id){
              arr.splice(i,1);
            }
          }
        }

        return arr;
      }
    },
    // 组件内导航钩子，处理未保存退出的情况
    beforeRouteLeave: function(to, from , next){
      if(this.isCurrentPageLink===false  && to.path !=="/"){
        next(false);
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
