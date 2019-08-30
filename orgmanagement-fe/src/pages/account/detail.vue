<template>
  <div>
    <el-form ref="account" :rules="rules" label-width="120px" size="mini" status-icon :model="account">
      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>账号信息</h3>
        </el-col>
        <el-col :span="23"  class="margin-left20 font-title">
          <el-row>
            <el-col :span="8">
              <el-form-item label="登录账号：" prop="accountLogin">
                <el-input clearable v-model="account.accountLogin"></el-input>
              </el-form-item>
              <div style="position: absolute;width: 0">
                <el-form-item >
                  <el-input></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <div  style="position: absolute;width: 0">
                <el-form-item >
                  <el-input type="password" show-password></el-input>
                </el-form-item>
              </div>
              <el-form-item label="密码：" prop="accountPassword">
                <el-input v-model.trim="account.accountPassword" type="password" show-password onkeyup=""></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="关联员工编号：" prop="empId" >
                <el-input v-model="account.empId" readonly></el-input>
                <el-button @click="addAccountDialogVisible=true">选择</el-button>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="员工姓名：" prop="empName">
                <el-input v-model="account.empName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="数据权限类型：" prop="dataPermissionId">
                <el-select  v-model="account.dataPermissionId" class="select-width" @change="addDataPermissionVisible">
                  <el-option label="全部" value="0"></el-option>
                  <el-option label="递归" value="1" ></el-option>
                  <el-option label="本部门" value="2"></el-option>
                  <el-option label="本人" value="3"></el-option>
                  <el-option label="手动选择" value="4"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="密保邮箱：" prop="accountEmail">
                <el-input v-model="account.accountEmail" :readonly="isReadOnly"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row v-if="isDisplay">
            <el-col :span="8" >
              <el-form-item label="部门信息：" prop="accountDept">
                <MultiTree :treeData="treeData" v-on:checkInfo="getDepartInfo" v-model="account.grantDeptList"></MultiTree>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>其他信息</h3>
        </el-col>
        <el-col :span="24">
          <el-row>
            <el-col :span="8">
              <el-form-item label="新建人："prop="createEmp">
                <el-input v-model="account.createEmp" readonly ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="新建时间：" prop="createTime">
                <el-input v-model="account.createTime" readonly ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="修改人："  prop="modifyEmp">
                <el-input v-model="account.modifyEmp" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="修改时间：" prop="modifyTime">
                <el-input v-model="account.modifyTime" readonly ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="15">
              <el-form-item label="备注：" props="remark">
                <el-input type="textarea" rows="4"  maxlength="200" v-model="account.remark"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row>
        <el-col :offset="6" :span="5" class="margin-top20">
          <el-button type="primary" size="normal" @click="onSubmit('account')">保存</el-button>
          <el-button type="primary" size="normal" @click="cancel">取消</el-button>
        </el-col>
      </el-row>
    </el-form>

    <!--分配所属员工弹窗-->
    <el-dialog ref="dialog" title="请选择账号的员工" :visible.sync="addAccountDialogVisible" width="75%">
      <SelectEmployee :treeData="treeData" v-on:checkInfo = "getEmployeeInfo" :parentVal="addAccountDialogVisible"></SelectEmployee>
    </el-dialog>

  </div>
</template>

<script>

  import md5 from 'js-md5';
  import MultiTree from '../../components/tree/MultiTree';
  import SelectEmployee from "../common/selectEmployee1";

    export default {
      name: "account_detail",
      components: {
        SelectEmployee,
        MultiTree,
      },
      data() {
        let validateAccountLogin = (rule, value, callback) => {  this.$axios.get('/api/orgmanagement/account/management/IsLoginOnly', {
          params: {accountLogin: value,date: new Date().getTime()}
        }).then(response => {
          console.log('111');
          if (0 === response.data.data) {
            callback();
          }else{
            callback(new Error('员工编号已存在，不允许重复'));
          }
        }).catch(error => {
          this.$message('网络错误，不能访问，请联系管理员');
        })
        };
        return {
          account: {
            accountLogin: '',
            accountPassword:'',
            empName:'',
            // accountPassword:md5(this.account.accountPassword),
            empId:'',
            isLinkEmp: '',
            grantDeptList:[],
            deptName:'',
            dataPermissionId: '',
            accountEmail: '',
            createEmp: '',
            createTime: '',
            modifyEmp: '',
            modifyTime: '',
            remark:'',
            isDelete:0,
            status:1,
            date: new Date().getTime(),
          },
          addAccountDialogVisible :false,
          treeData:[],
          isDisplay: false,
          isReadOnly:false,
          rules: {
            accountLogin: [
              { required: true, message: '请输入登录账号', trigger: 'blur' },
              { min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' },
              { validator: validateAccountLogin, trigger: 'blur' }
            ],
            accountPassword: [
              { required: true, message: '请输入密码', trigger: 'blur' },
              { pattern:/^[^\u4e00-\u9fa5]*$/, message: '密码中不能存在中文' },
              { pattern: /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_]+$)(?![a-z0-9]+$)(?![a-z\W_]+$)(?![0-9\W_]+$)[a-zA-Z0-9\W_]{1,32}$/, message: '密码为数字，小写字母，大写字母，特殊符号至少包含三种' },
            ],
            accountEmail: [
              {required: true, type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
            ],
            dataPermissionId: [
              { required: true, message: '请输入数据权限类型', trigger: 'blur' },
            ],
            remark: [
              {min: 0, max: 200, message: '长度在 0 到 200 个字符', trigger: 'blur'}
            ],
          },

        }
      },

      created:function(){
        this.getTreeData();
      },

      methods:{

        // 从后台获取部门树状结构数据
        getTreeData() {
          if (sessionStorage.getItem('deptTree')) {
            this.treeData = JSON.parse(sessionStorage.getItem('deptTree'));
          }
        },

        // 从子组件中获取到选择的所属部门信息
        getDepartInfo(data) {
          if (data.length > 0){
            this.account.grantDeptList = data;
          }

        },

        //员工编号为空时，
        addDataPermissionVisible(){
          if ((this.account.empId == ''||this.account.empId == null) && this.account.dataPermissionId>=1 &&
          this.account.dataPermissionId<=2)
          {
            this.account.dataPermissionId = '';
            this.$message.error("该账号未有关联员工，因此没有该数据权限")
          }
          //判断是否选择手动选择
          if (this.account.dataPermissionId === '4') {
            console.log(this.isDisplay);
            this.isDisplay = true;
          }
          else {
            this.isDisplay = false;
          }

        },

        getEmployeeInfo(data){
          if(data ===false)
          {
            this.addAccountDialogVisible = false;
          }
          else {
            console.log(data[0]);
            this.account.empName = data[0].empName;
            this.account.empId = data[0].empId;
            this.account.accountEmail = data[0].empEmail;
            this.addAccountDialogVisible = false;
          }
          if("" === this.account.accountEmail){
            this.isReadOnly = false;
          }else{
            this.isReadOnly = true;
          }
        },
        getNewAccount(data){
          let newAccount = Object.assign({},data);
          newAccount.accountPassword = md5(newAccount.accountPassword);
          return newAccount;
        },
        onSubmit(formName) {
          this.$refs[formName].validate((valid) => {
            if(valid){
              //请求后端接口
              this.$axios.post('/api/orgmanagement/account/management/addAccount',
                this.getNewAccount(this.account))
                .then(response=>{
                  if (response.data.code == 200) {
                    this.$message.success('新增账号成功!')
                    this.$router.push('/account/list')
                  }else {
                    this.$message(response.data.msg);
                  }
                })

            }else {
              return false
            }
          })

        },

        cancel() {
          this.$router.push('/account/list')
        }

      },
    }
</script>

<style scoped>

</style>
