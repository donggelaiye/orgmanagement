<template>
  <div>
    <el-form ref="roleForm" status-icon :model="roleForm" label-width="140px" size="mini">
      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>角色信息</h3>
        </el-col>
        <el-col :span="24">
          <el-row>
            <el-col :span="8">
              <el-form-item label="角色ID：" prop="role.roleId">
                <el-input v-model="roleForm.role.roleId" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="角色名称：" prop="role.roleName">
                <el-input  v-model="roleForm.role.roleName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="审批人账号：" prop="role.approveAccount">
                <el-input v-model="roleForm.role.approveAccount" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="审批人员工编号：" prop="role.approveId">
                <el-input v-model="roleForm.role.approveId" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="审批人姓名：" prop="role.approveName">
                <el-input v-model="roleForm.role.approveName" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="审批人所属部门：" prop="role.approveDepartment">
                <el-input v-model="roleForm.role.approveDepartment" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="16">
              <el-form-item label="支持业务线：" prop="bussinessLine">
                <el-checkbox-group   v-model="roleForm.bussinessLine" @change="handleSelectionChange">
                  <el-checkbox  v-for="bus in bussinessLineInit" :key="bus.bussinessLineId"
                               :value="bus.bussinessLineId" :label="bus.bussinessLineId" disabled>{{bus.bussinessLineName}}
                  </el-checkbox>
                  <!--<el-checkbox value="1" label="1" >买买车</el-checkbox>-->
                </el-checkbox-group >
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="12">
          <el-form-item label="描述：" prop="description">
            <el-input type="textarea" rows="4" v-model="roleForm.role.description" maxlength="200" readonly></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>其他信息</h3>
        </el-col>
        <el-col :span="24">
          <el-row>
            <el-col :span="8">
              <el-form-item label="新建时间：">
                <el-input v-model="roleForm.role.createTime" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="新建人：">
                <el-input v-model="roleForm.role.createEmpName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="修改时间：">
                <el-input v-model="roleForm.role.modifyTime" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="修改人：">
                <el-input v-model="roleForm.role.modifyEmpName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="15">
              <el-form-item label="状态：" props="status">
                <el-input type="textarea" v-model="roleForm.role.status" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row>
        <el-col :offset="6" :span="5" class="margin-top20">
          <el-button type="primary" size="normal" @click="cancel">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
  import '@/axios/api'

  var date = new Date();
  export default {
    name: "RoleManagementDetail",
    components: {
    },
    data() {
      return {
        roleForm: {
          role: {
            roleId: '',
            roleName: '',
            approveAccount: '',
            approveId: '',
            approveName: '',
            approveDepartment: '',
            description: '',
            createTime: date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate(),
            createEmp: '',
            createEmpName: '',
            modifyTime: date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate(),
            modifyEmp: '',
            modifyEmpName: '',
            isDelete: 0,
            status: '有效',
            remark: ''
          },
          bussinessLine: [],
        },
        bussinessLineInit: [{
          bussinessLineId: '',
          bussinessLineName: '',
        }],
        accountData: {
          accountLogin: '',
          empName: '',
          empId: '',
          deptName: ''
        },
      }
    },
    created: function () {
      this.search();
      let type = this.$route.query.type;
      this.roleForm.roleId = this.$route.query.roleId;
      this.roleForm.roleName = this.$route.query.roleName;
      this.getRole(this.roleForm.roleId);
      this.getRoleLine(this.roleForm.roleId);
    },
    methods: {

      cancel() {
        this.$router.go(-1);
      },
      search() {
        this.$axios.get('/api/orgmanagement/role/management/getLineList')
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
            if (response.data.code == 200) {
              console.log(response.data.data)
              this.bussinessLineInit = response.data.data;

            } else {
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
      },
      handleSelectionChange() {
        console.log(this.roleForm.bussinessLine);
      },
      // 通过员工ID获取员工信息
      getRole(roleId) {
        this.$axios.get('/api/orgmanagement/role/management/getRole', {
          params: {roleId: roleId}
        }).then(response => {
          if (response.data.code === 200) {
            this.roleForm.role = response.data.data;
            if (this.roleForm.role.status === 1) {
              this.roleForm.role.status = '有效'
            } else {
              this.roleForm.role.status = '无效'
            }
            console.log(this.roleForm.role)
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
      // 通过员工ID获取业务线Id
      getRoleLine(roleId) {
        this.$axios.get('/api/orgmanagement/role/management/getLineId', {
          params: {roleId: roleId}
        }).then(response => {
          if (response.data.code === 200) {
            this.roleForm.bussinessLine = response.data.data;
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

    }
  }
</script>

<style>
  .el-checkbox__input.is-disabled+span.el-checkbox__label{
    color:black;
  }
  .el-checkbox__input.is-disabled.is-checked .el-checkbox__inner{
    color: black;
  }
  .el-checkbox__input.is-disabled.is-checked .el-checkbox__inner::after{
    color:black;
  }
  .el-checkbox__input.is-disabled.is-checked .el-checkbox__inner::after{
    border-color:black;
  }


</style>
