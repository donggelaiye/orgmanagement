<template>
  <div>
    <el-form ref="roleForm" :rules="rules" status-icon :model="roleForm" label-width="140px" size="mini">
      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>角色信息</h3>
        </el-col>
        <el-col :span="24">
          <el-row>
            <el-col :span="8">
              <el-form-item label="角色ID：" prop="role.roleId">
                <el-input v-model="roleForm.role.roleId"  readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="角色名称：" prop="role.roleName">
                <el-input v-model="roleForm.role.roleName"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="审批人账号：" prop="role.approveAccount">
                <el-input v-model="roleForm.role.approveAccount" readonly></el-input>
                <el-button type="text" @click="accountDialogVisible = true">选择</el-button>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="审批人员工编号：" prop="role.approveId">
                <el-input v-model="roleForm.role.approveId"  readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="审批人姓名：" prop="role.approveName">
                <el-input v-model="roleForm.role.approveName"  readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="审批人所属部门：" prop="role.approveDepartment">
                <el-input v-model="roleForm.role.approveDepartment"  readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="16">
              <el-form-item label="支持业务线：" prop="bussinessLine">
                <el-checkbox-group v-model="roleForm.bussinessLine" @change="handleSelectionChange">
                  <el-checkbox v-for="bus in bussinessLineInit" :key="bus.bussinessLineId"
                               :value="bus.bussinessLineId" :label="bus.bussinessLineId">{{bus.bussinessLineName}}
                  </el-checkbox>
                  <!--<el-checkbox value="1" label="1" >买买车</el-checkbox>-->
                </el-checkbox-group>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="12">
          <el-form-item label="描述：" prop="description">
            <el-input type="textarea" rows="4" v-model="roleForm.role.description" maxlength="200"></el-input>
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
                <el-input v-model="roleForm.role.createTime"  readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="新建人：">
                <el-input v-model="roleForm.role.createEmpName"  readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="修改时间：">
                <el-input v-model="roleForm.role.modifyTime"  readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="修改人：">
                <el-input v-model="roleForm.role.modifyEmpName"  readonly></el-input>
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
          <el-button type="primary" size="normal" @click="onSubmit('roleForm')">保存</el-button>
          <el-button type="primary" size="normal" @click="cancel">取消</el-button>
        </el-col>
      </el-row>

      <el-dialog ref="dialog" title="请添加账号" :visible.sync="accountDialogVisible" width="80%">
        <selectAccount v-on:checkInfo="accountComfirm" @dialogClose="childClose"
                    :parentVal="accountDialogVisible"></selectAccount>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
  import selectAccount from '@/pages/role/management/selectAccount'
  //import addAccount from '@/pages/common/addAccount'
  import '@/axios/api'

  var date = new Date();
  export default {
    name: "role_detail",
    components: {
      selectAccount
    },
    data() {
      // 校验角色名称是否已存在
      let validateRoleName = (rule, value, callback) => {
        // 判断是否为修改操作，是则比较角色名称是否有改变
        let type = this.$route.query.type;
        if("update" === type){
          if(this.$route.query.roleName == value){
            callback();
          }
        }
        // 不是修改操作或角色名称已经改变
        this.$axios.get('/api/orgmanagement/role/management/checkRoleName', {
          params: {roleName: value}
        }).then(response => {
          if (0 === response.data.data) {
            callback();
          } else {
            callback(new Error('角色名称已存在，不允许重复'));
          }
        }).catch(error => {
          this.$message('网络错误，不能访问，请联系管理员');
        })
      };
      //判断审批人账号是否被删除
      let validateAccount = (rule, value, callback) => {
        // 判断是否为修改操作，是则比较员工编号是否有改变
          if(this.$route.query.approveAccount == value){
            callback();
          }
        // 不是修改操作或角色名称已经改变
        this.$axios.get('/api/orgmanagement/role/management/checkApproveAccount', {
          params: {approveAccount: value}
        }).then(response => {
          if (0 === response.data.data) {
            callback();
          } else {
            callback(new Error('审批人账号已被删除，请重新选择'));
          }
        }).catch(error => {
          this.$message('网络错误，不能访问，请联系管理员');
        })
      };

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
            createTime: '',
            createEmp: '',
            createEmpName:'',
            modifyTime: '',
            modifyEmp: '',
            modifyEmpName:'',
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
        accountDialogVisible: false,
        rules: {
          role: {
            roleName: [
              {required: true, message: '角色名称为必填项，不允许为空', trigger: 'blur'},
              {min: 1, max: 30, message: '不满足录入条件，仅支持1-30位字符', trigger: 'blur'},
              {validator: validateRoleName, trigger: 'blur'}
            ],
            approveAccount: [
              {required: true, message: '审批人为必填项，不允许为空', trigger: 'blur'},
              {validator: validateAccount, trigger: 'blur'}
            ],
            description: [
              {required: false, message: '描述非必填', trigger: 'blur'},
              {min: 0, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur'}
            ],
            status: [
              {min: 0, max: 200, message: '长度在 0 到 200 个字符', trigger: 'blur'}
            ],
            type: [
              {type: 'array', required: true, message: '请至少选择一个业务线', trigger: 'change'}
            ],
          },
          bussinessLine: [
              {type: 'array', required: true, message: '请至少选择一个业务线', trigger: 'change'},
            ]
          }
        }
    },
    created: function () {
      this.search();
      let type = this.$route.query.type;
      if ("update" === type) {
        this.roleForm.roleId = this.$route.query.roleId;
        this.roleForm.roleName = this.$route.query.roleName;
        this.getRole(this.roleForm.roleId);
        this.getRoleLine(this.roleForm.roleId);
        console.log(this.roleForm.role)
      }else {
      }
    },
    // 组件内导航钩子，处理未保存退出的情况
    /*beforeRouteLeave: function(to, from , next){
      next(false)
      this.$confirm('您还有信息未保存，确定离开当前页面吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 选择确定
        next();
        this.$router.push('/role/management');
      })
    },*/
    methods: {
      onSubmit(formName) {
        console.log(JSON.stringify(this.roleForm));
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(this.roleForm.role.status === '有效') {
              this.roleForm.role.status = 1;
            }else {
              this.roleForm.role.status = 0;
            }
            let type = this.$route.query.type;
            if ("update" === type) {
              this.doUpdateRole();
            } else {
              this.doAddRole();
            }
          } else {
            return false
          }
        })
      },
      doAddRole() {
        //请求后端接口
        //this.roleForm.role.createTime = this.dateFormat(date);
        //this.roleForm.role.modifyTime = this.dateFormat(date);
        this.$axios.post('/api/orgmanagement/role/management/addRole',
          {
            role: JSON.stringify(this.roleForm.role),
            bussinessLine: JSON.stringify(this.roleForm.bussinessLine)
          })
        //then获取成功；response成功后的返回值（对象）
          .then((response) => {
            //调用删除员工接口返回消息
            if (response.data.data === 0) {
              this.$message({
                type: 'success',
                message: '新增角色成功!'
              });
              this.$router.push('/role/management');
            }else {
              this.$message({
                type: 'error',
                message: '添加角色失败，该角色的审批人账号信息变更,请重新选择!'
              });
              this.$router.push({path: '/role/management/detail', query: {type: "add"}})
            }
          }).catch(error => {
          this.$message('网络错误，不能访问，请联系管理员');
        });
      },


      doUpdateRole() {
        //this.roleForm.role.modifyTime = this.dateFormat(date);

        this.$axios.post('/api/orgmanagement/role/management/updateRole',
          {
            role: JSON.stringify(this.roleForm.role),
            bussinessLine: JSON.stringify(this.roleForm.bussinessLine)
          })
        //then获取成功；response成功后的返回值（对象）
          .then(response => {
            if (response.data.code == 200) {
              //调用删除员工接口返回消息
              this.$message({
                type: 'success',
                message: '修改角色成功!'
              });
              //this.$router.push('/employee/list');
              this.$router.push('/role/management');
            }else if (response.data.code === 702) {
              this.$message({
                type: 'error',
                message: response.data.msg
              });
              this.$router.push('/role/management');
            } else {
              this.$message('稍后重试');
            }
          }).catch(() => {
          this.$message('网络错误，不能访问，请联系管理员');
        });
      },

      accountComfirm: function (roleRow) {

        this.roleForm.accountData = roleRow[0];
        console.log("accountData")
        console.log(this.roleForm.accountData)
        this.roleForm.role.approveDepartment = roleRow.deptName;
        this.roleForm.role.approveAccount = roleRow.accountLogin;
        this.roleForm.role.approveName = roleRow.empName;
        this.roleForm.role.approveId = roleRow.empId;
        console.log(roleRow)
        console.log(this.roleForm.role)
      },
      childClose: function (val) {
        this.accountDialogVisible = val
      },
      cancel() {
        this.$router.push('/role/management');
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
            if(this.roleForm.role.status === 1) {
              this.roleForm.role.status = '有效'
            }else {
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
      // 获取当前时间
      dateFormat(time) {
        let year=date.getFullYear();
        /* 在日期格式中，月份是从0开始的，因此要加0
         * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
         * */
        let month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
        let day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
        let hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
        let minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
        let seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
        // 拼接
        return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
      }
    }
  }
</script>

<style scoped>

</style>
