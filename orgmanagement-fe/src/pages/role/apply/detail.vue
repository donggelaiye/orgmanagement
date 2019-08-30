<template>
  <div id="mainPage">
    <el-form ref="pageInfo" status-icon :model="pageInfo" label-width="150px" size="mini">
      <!--      角色信息-->
      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3 style="float: left;">角色信息</h3>

        </el-col>
        <el-col :span="24">
          <el-row>
            <el-col :span="8">
              <el-form-item label="角色申请编号：">
                <el-input v-model="pageInfo.applyCode" clearable readonly placeholder="请先选择申请角色"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="申请角色：" prop="roleName">
                <span>{{pageInfo.roleName}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="角色审批人：">
                <el-input v-model="pageInfo.roleApproveName" readonly placeholder="请先选择申请角色"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="角色支持业务线：">
                <el-input v-model="pageInfo.bussinessLine" readonly placeholder="暂无"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <!--      申请账号明细-->
      <el-row>
        <el-col :span="8" class="margin-left20 font-title">
          <h3 style="float: left;">申请账号明细</h3>
        </el-col>
        <el-table
          ref="multipleTable"
          :data="accountData"
          :highlight-current-row="true"
          class="table—style" border>
          <el-table-column
            prop="accountLogin"
            label="申请账号"
            width="140">
          </el-table-column>
          <el-table-column
            prop="empName"
            label="关联员工姓名"
            width="140">
          </el-table-column>
          <el-table-column
            width="140"
            prop="empId"
            label="关联员工编号">
          </el-table-column>
          <el-table-column
            width="140"
            prop="deptName"
            label="关联员工所属部门">
          </el-table-column>
          <el-table-column
            width="190"
            prop="operateType"
            label="申请操作">
            <template slot-scope="scope">
              <span>{{['移除','添加'][scope.row.operateType]}}</span>
            </template>
          </el-table-column>
        </el-table>

      </el-row>
      <!--      其他信息-->
      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>其他信息</h3>
        </el-col>
        <el-col :span="24">
          <el-row>
            <el-col :span="8">
              <el-form-item label="申请时间：">
                <div class="block">
                  <el-date-picker readonly
                                  v-model="pageInfo.createTime"
                                  type="datetime"
                                  placeholder="选择日期时间">
                  </el-date-picker>
                </div>

              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="申请人：">
                <el-input v-model="applyAccountName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="修改时间：">
                <div class="block">
                  <el-date-picker readonly
                                  v-model="pageInfo.modifyTime"
                                  type="datetime"
                                  placeholder="暂无">
                  </el-date-picker>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="修改人：">
                <el-input v-model="pageInfo.modifyAccountName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="审批时间：">
                <div class="block">
                  <el-date-picker readonly
                                  v-model="pageInfo.approveTime"
                                  type="datetime"
                                  placeholder="暂无">
                  </el-date-picker>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="审批人：">
                <el-input v-model="pageInfo.approveAccountName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="15">
              <el-form-item label="状态：">
                <span style="margin-left: 16px" rows="4">{{['已新建', '待审批', '审批通过','审批拒绝'][pageInfo.status]}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="15">
              <el-form-item label="审批意见：">
                <el-input type="textarea" rows="4" v-model="pageInfo.approveComment" maxlength="200"
                          readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row>
        <el-col :offset="7" :span="17">
          <el-button type="primary" @click="cancle">关闭</el-button>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
  import '@/axios/api'

  export default {
    name: "role_apply_applyInfo",

    data() {
      return {
        role: {
          roleId: '',
          roleName: '',
          approveAccount: '',
          approveId: '',
          approveName: '',
          approveDepart: '',
          status: '',
          description: ''
        },
        pageInfo: {
          applyCode: '',
          roleId: '',
          roleName: '',
          roleApproveName: '',
          bussinessLine: '',
          createTime: '',
          applyEmpName: '',
          modifyTime: '',
          modifyEmp: '',
          approveTime: '',
          approveEmp: '',
          status: '',
          createEmp: '',
          applyAccountLogin: '',
          applyEmpId: '',
          approveComment: '',
          applyAccountName: '',
          approveAccountName: '',
        },
        applyAccountName: '',
        dateTimeNow: '',
        accountData: [
          // {
          // applyAccount: '',
          // empName: '',
          // empId: '',
          // empDept: '',
          // operateType: ''
          // }
        ],
        tableData: [],
        page: {
          total: 10,
          currentPage: 1,
          pageSize: 10,
        },
        operateType: '',
        businessLineName: '',
        selectVal: [],
        roleDialogVisible: false,
      }
    },
    created: function () {
      this.pageInfo.applyCode = this.$route.query.applyCode;
      this.showApplyDetail(this.pageInfo.applyCode);
      this.pageInfo.createTime = new Date();
      this.pageInfo.applyEmpName = "申请人账号（姓名）";
      this.pageInfo.createEmp = 123123; //登录账号的id
      this.pageInfo.applyAccountLogin = "324252"; // 登录账号
      this.pageInfo.applyEmpId = 23424;
      this.pageInfo.status = 0;
    },

    methods: {
      cancle() {
        this.$router.go(-1)
      },
      showApplyDetail(applyCode) {
        this.$axios.get('/api/orgmanagement/role/apply/showApplyDetail', {
          params: {applyCode: applyCode}
        }).then(response => {
          if (response.data.code === 200) {
            let data = JSON.parse(response.data.data)
            this.pageInfo = data.pageInfo
            console.log(this.pageInfo)
            this.applyAccountName = this.pageInfo.applyAccountLogin + '(' + this.pageInfo.applyEmpName + ')';
            this.accountData = data.accountData
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

      getApplyCode() {
        this.$axios.get('/api/orgmanagement/role/apply/generateApplyCode',{
          params : {
            date:new Date()
          }
        })
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
            if (response.data.code == 200) {
              this.pageInfo.applyCode = response.data.data.applyCode
              // this.pageInfo.applyEmpName = response.data.
            } else {
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
    }
  }
</script>

<style>
</style>
