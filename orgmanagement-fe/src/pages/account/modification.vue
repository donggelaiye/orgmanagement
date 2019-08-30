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
              <el-form-item label="登录账号：" prop="accountLogin" >
                <el-input clearable v-model="account.accountLogin" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="密码：" >
                <el-input v-model="account.accountLogin" show-password readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="关联员工编号：" >
                <el-input v-model="account.empId" readonly></el-input>
                <el-button @click="addAccountDialogVisible=true">选择</el-button>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="员工姓名：" >
                <el-input v-model="account.empName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="数据权限类型：" prop="dataPermissionId">
                <el-select  v-model="account.dataPermissionId" class="select-width" @change="addDataPermissionVisible">
                  <el-option v-for="option in statusOptions" :label="option.label" :value="option.value"
                             v-bind:key="option.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="密保邮箱：" prop="accountEmail">
                <el-input v-model="account.accountEmail" :readonly="isReadOnly"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row v-if="isDisplay">
            <el-col :span="8" >
              <el-form-item label="部门信息：" prop="accountDept">
                <MultiTree :treeData="treeData" v-on:checkInfo="getDepartInfo" :checkedKeys="checkedKeys"></MultiTree>
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
              <el-form-item label="新建人：">
                <el-input v-model="account.createEmpName" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="新建时间：">
                <el-input v-model="account.createTime" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="修改人：">
                <el-input v-model="account.modifyEmpName" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="修改时间：">
                <el-input v-model="account.modifyTime" readonly></el-input>
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
      <SelectEmployee v-on:checkInfo = "getEmployeeInfo" :parentVal="addAccountDialogVisible"></SelectEmployee>
    </el-dialog>
  </div>
</template>

<script>

  import SelectEmployee from "../common/selectEmployee1";
  import MultiTree from '../../components/tree/MultiTree';
  import ArrayUtil from '../../constant/arrayUtil'
  let thisVue;
  export default {
    name: "account_modification",
    components: {
      SelectEmployee,
      MultiTree,
    },

    data() {
      return {
        account: {
          accountLogin: '',
          empId:'',
          empName:'',
          isLinkEmp: '',
          dataPermissionId: '',
          preDataPermission:'',
          accountEmail: '',
          createEmp: '',
          createTime: '',
          modifyEmp: '',
          modifyTime: '',
          createEmpName:'',
          modifyEmpName:'',
          remark:'',
          isDelete:0,
          //分配权限
          grantDeptList:[],
          //移除权限
          revokeDeptList:[],
          status:1,
          date: new Date().getTime(),
        },
        //最终拥有权限
        checkedKeys:[],
        //原始拥有权限
        originPermission:[],
        addAccountDialogVisible :false,
        treeData:[],
        isDisplay:false,
        statusOptions: [
          {value: 0, label: '全部'},
          {value: 1, label: '递归'},
          {value: 2, label: '本部门'},
          {value: 3, label: '本人'},
          {value: 4, label: '手动选择'},
        ],
        rules: {
          accountLogin: [
            { required: true, message: '请输入登录账号', trigger: 'blur' },
            { min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' }
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
        isReadOnly: true
      }
    },
    beforeCreate(){
      thisVue = this;
    },
    created () {
      this.getTreeData();
      this.getAccount();

    },
    methods:{

      //分析数据
      analyseData(checkedKeys, originPermission){
        //最终拥有权限 - 原始拥有权限 = 分配权限
        this.account.grantDeptList = ArrayUtil.differenceSet(checkedKeys, originPermission);
        //原始拥有 - 最终拥有 = 移除权限
        this.account.revokeDeptList = ArrayUtil.differenceSet(originPermission,checkedKeys);
      },

      //从后台获取账号信息
      getAccount(){
        this.$axios.get('/api/orgmanagement/account/management/getAccount', {
          params: { accountLogin: this.$route.query.accountLogin }
        }).then(response => {
          if (response.data.code==200) {
            thisVue.account = response.data.data;
            if (response.data.data.originPermission != null) {
              thisVue.originPermission = response.data.data.originPermission;
              thisVue.checkedKeys = response.data.data.originPermission;
              this.isDisplay = true;
            }
            if(response.data.data.empId==null)
            {
              this.isReadOnly = false;
            }
            // this.analyseData(this.checkedKeys, this.originPermission);
            thisVue.addDataPermissionVisible();
          }
        });
      },
      // 从后台获取部门树状结构数据
      getTreeData() {
        if (sessionStorage.getItem('deptTree')) {
          this.treeData = JSON.parse(sessionStorage.getItem('deptTree'));
        }
      },

      // 从子组件中获取到选择的所属部门信息
      getDepartInfo(data) {
        this.analyseData(data, this.originPermission);
      },

      getEmployeeInfo(data){
        if(data ===false)
        {
          this.addAccountDialogVisible = false;
        }
        else {
        this.account.empName = data[0].empName;
        this.account.empId = data[0].empId;
        this.account.accountEmail = data[0].empEmail;
        if("" === this.account.accountEmail){
          this.isReadOnly = false;
        }else{
          this.isReadOnly = true;
        }
        this.addAccountDialogVisible = false;}
      },

      addDataPermissionVisible()
      {
        if ((this.account.empId == ''||this.account.empId == null) && this.account.dataPermissionId>=1 &&
                this.account.dataPermissionId<=2)
        {
          this.account.dataPermissionId = '';
          this.$message.error("该账号未有关联员工，因此没有该数据权限")
        }
        //判断是否选择手动选择
        if (this.account.dataPermissionId === 4) {
          this.isDisplay = true;
        }
        else {
          this.isDisplay = false;
          if (this.checkedKeys) {
            this.account.revokeDeptList = this.checkedKeys;
          }

        }
      },

      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if(valid){
            //请求后端接口
            this.$axios.post('/api/orgmanagement/account/management/modifyAccount',
              this.account)
              .then(response=>{
                if (response.data.code == 200) {
                  this.$message.success('修改账号成功!')
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
