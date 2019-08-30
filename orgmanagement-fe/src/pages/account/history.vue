<template>
  <el-row>
    <el-form ref="history" :rules="rules" label-width="120px" size="mini">
        <el-col :span="20" class="margin-left20 font-title">
          <h2>登录账号</h2>
          <el-button type="primary" size="normal" @click="cancel" style="margin-left: 100%">返回</el-button>
        </el-col>
        <el-col class="margin-top20" style="margin-left: 20px">
          <el-table
            ref="multipleTable"
            :data="tableData"
            :highlight-current-row="true"
            @selection-change="handleSelectionChange"
            class="table—style" border>
            <el-table-column class="margin-left20"
              prop="empId"
              label="员工编号"
              width="120">
            </el-table-column>
            <el-table-column
              prop="empName"
              label="员工姓名"
              width="120">
            </el-table-column>
            <el-table-column prop="dataPermission" label="数据权限类型"  width="140">
              <template slot-scope="scope">
                <span v-if="scope.row.dataPermission === 0">全部</span>
                <span v-else-if="scope.row.dataPermission === 1">递归</span>
                <span v-else-if="scope.row.dataPermission === 2">本部门</span>
                <span v-else-if="scope.row.dataPermission === 3">本人</span>
                <span v-else="scope.row.dataPermission === 4">手动选择</span>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="账号状态"  width="110">
              <template slot-scope="scope">
                <span v-if="scope.row.status === 0">冻结</span>
                <span v-else="scope.row.status === 1">正常</span>
              </template>
            </el-table-column>
            <el-table-column prop="operateType" label="操作类型"  width="110">
              <template slot-scope="scope">
                <span v-if="scope.row.operateType === 0">新建</span>
                <span v-else-if ="scope.row.operateType === 1">修改</span>
                <span v-else-if ="scope.row.operateType === 2">删除</span>
                <span v-else-if ="scope.row.operateType === 3">冻结</span>
                <span v-else-if ="scope.row.operateType === 4">解冻</span>
                <span v-else-if ="scope.row.operateType === 5">密码重置</span>
                <span v-else-if ="scope.row.operateType === 6">分配权限</span>
                <span v-else-if="scope.row.operateType === 7">分配角色权限</span>
                <span v-else-if ="scope.row.operateType === 8">分配菜单权限</span>
                <span v-else-if ="scope.row.operateType === 9">员工删除</span>
                <span v-else ="scope.row.operateType === 10">员工离职</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="modifyEmpName"
              label="操作人"
              width="120">
            </el-table-column>
            <el-table-column
              prop="modifyTime"
              label="操作时间"
              width="160">
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
    </el-form>


  </el-row>
</template>

<script>
    export default {
        name: "account_history",

      data(){
          return{
            tableData: [{
              empId:'',
              empName:'',
              dataPermission:'',
              status:'',
              operateType:'',
              modifyTime:'',
              modifyEmp:'',
              modifyEmpName:'',
              date: new Date().getTime(),
            }],
            page:{
              total:10,
              currentPage:1,
              pageSize:10
            },
            rules:{
            },
          }
      },
      created:function(){
        this.search();
      },
      methods: {

        //取消返回列表页
        cancel(){
          this.$router.push('/account/list')
        },
        //查询
        search() {
          this.$axios.get('/api/orgmanagement/history/management/selectAllHistory',
            {
              params:
                {
                  pageNum: this.page.currentPage,
                  pageSize: this.page.pageSize,
                  account: this.account
                }
            })
          //then获取成功；response成功后的返回值（对象）
            .then(response => {
              if (response.data.code == 200) {
                console.log(response.data.data.pageData);
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
        },

        handleSizeChange(val) {
          this.page.pageSize = val;
          this.search()
        },

        //  显示第几页数据
        handleCurrentChange(val) {
          this.page.currentPage = val;
          this.search()
        },
      }
    }
</script>

<style scoped>

</style>
