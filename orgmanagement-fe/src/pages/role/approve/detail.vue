<template>
  <div>
    <el-form ref="pageInfo"  status-icon :model="pageInfo" label-width="130px" size="mini">
<!--      申请信息-->
      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3 style="float: left;">申请信息</h3>
          <el-button style="margin-top: 1%; margin-left: 1%" type="primary" @click="cancle">关闭</el-button>
        </el-col>
        <el-col :span="24">
          <el-row>
            <el-col :span="8">
              <el-form-item label="角色申请编号：" prop="applyCode">
                <el-input v-model="pageInfo.applyCode" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="申请角色：" prop="roleName">
                <el-input v-model="pageInfo.roleName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="角色审批人：" prop="roleApproveName">
                <el-input v-model="pageInfo.roleApproveName" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="角色支持业务线：" prop="bussinessLine" >
                <el-input v-model="pageInfo.bussinessLine" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
<!--      申请账号明细-->
      <el-row >
        <el-col :span="20" class="margin-left20 font-title">
          <h3>申请账号明细</h3>
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
            width="140"
            prop="operateType"
            label="申请操作">
            <template slot-scope="scope">
              <span v-if="scope.row.operateType === 1" v-model="scope.row.operateType">添加</span>
              <span v-if="scope.row.operateType === 0" v-model="scope.row.operateType">删除</span>
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
                <span style="margin-left: 16px" rows="4" readonly>{{['已新建', '待审批', '审批通过','审批拒绝'][pageInfo.status]}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
<!--      按钮-->
      <el-row>
        <el-col :offset="6" :span="5" class="margin-top20">
          <el-button type="primary" size="normal" @click="approveAgree(accountData)">审核通过</el-button>
          <el-button type="primary" size="normal" @click="approveRefuse">审核拒绝</el-button>
        </el-col>
      </el-row>
<!--      审核拒绝弹窗-->
      <el-dialog ref="dialog" title="请输入审核拒绝理由" :visible.sync="approveRefuseDialogVisible" width="50%">
        <el-row class="list-container">
          <el-col>
            <el-form-item label="拒绝理由：" >
              <el-input type="textarea" rows="4" v-model="RoleApproveRefuse.approveComment" maxlength="200"></el-input>
            </el-form-item>
          </el-col>
          <el-col>
              <el-form-item style="margin-left: 6px">
                <el-button type="primary" icon="el-icon-search" @click="comfirm()">确定</el-button>
                <el-button type="primary" icon="el-icon-document" @click="approveRefuseDialogVisible = false">取消</el-button>
              </el-form-item>
          </el-col>
        </el-row>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
    export default {
        name: "role_approve_detail",
      data() {
        return {
          pageInfo: {
            roleId:'',
            applyCode: '',
            roleName: '',
            roleApproveName: '',
            businessLineName: '',
            createTime: '',
            applyEmpName: '',
            modifyTime: '',
            modifyEmp: '',
            approveTime: '',
            approveEmp: '',
            status: '',
            createEmp: '',
            applyAccountLogin: '',
            applyEmpId: ''
          },
          applyAccountName:'',
          RoleApproveRefuse:{
            applyCode:'',
            roleApproveName:'',
            approveComment:''
          },
          roleApproveAgree:{
            RoleId:'',
            roleApproveName:'',
            applyCode:''
          },
          dateTimeNow: '',
          accountData: [],
          approveRefuseDialogVisible: false,
        }
      },
      created(){
       //获取角色申请信息
        let selectApplyCode = this.$route.query.selectApplyCode;
        this.getApprove(selectApplyCode);
      },
      methods:{
        //返回上级
        cancle() {
          this.$confirm('您还未执行审批操作，确定离开当前页面吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            // 选择确定
            this.$router.go(-1)
          })
        },
        // 通过ApplyCode获取角色申请信息
        getApprove(selectApplyCode){
          this.$axios.get('/api/orgmanagement/role/approve/getApprove', {
            params: { applyCode: selectApplyCode }
          }).then(response => {
            if (response.data.code === 200) {
              let data = JSON.parse(response.data.data);
              console.log("accountData")
              console.log(data.accountData)
              this.pageInfo = data.pageInfo
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
        //审批通过
        approveAgree(accountData) {
          this.roleApproveAgree.applyCode=this.pageInfo.applyCode;
          this.roleApproveAgree.roleApproveName=this.pageInfo.roleApproveName;
          this.roleApproveAgree.RoleId = this.pageInfo.roleId;
          this.$axios.post('/api/orgmanagement/role/approve/approveAgree', {
              roleApproveAgree: JSON.stringify(this.roleApproveAgree),
              accountData: JSON.stringify(this.accountData),
          }).then(response => {
            if (response.data.code == 200) {
              this.$message({
                type: 'success',
                message: '审核通过成功!'
              })
            }
            this.$router.push('/role/approve')
          }).catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
        },
        //取消拒绝
        approveRefuse() {
          this.approveRefuseDialogVisible=true
          // this.$router.push('/role/approve')
        },
        //确认拒绝
        comfirm(){
          //拒绝理由字数校验
          if(this.RoleApproveRefuse.approveComment.length<1){
            this.$message('您的拒绝理由字数不足');
          }
          //设置角色申请状态为拒绝，并插入拒绝理由
          else{
            this.RoleApproveRefuse.applyCode=this.pageInfo.applyCode;
            this.RoleApproveRefuse .roleApproveName=this.pageInfo.roleApproveName;
            this.$axios.get('/api/orgmanagement/role/approve/approveRefuse',
              {
                params:
                  {
                    RoleApproveRefuse:this.RoleApproveRefuse
                  }
              })
            this.$router.push('/role/approve')

          }
        }
      }
    }
</script>

<style scoped>
  .table—style{
    width:60%;
    margin-left: 15px;

  }
</style>
