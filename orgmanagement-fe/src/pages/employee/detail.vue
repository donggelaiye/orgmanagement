<template>
  <div>
    <!--员工信息表单-->
    <el-form ref="employee" :rules="rules" status-icon :model="employee" label-width="120px" size="mini">
      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>员工信息</h3>
        </el-col>
        <el-col :span="24">
            <el-row>
              <el-col :span="8">
                <el-form-item label="员工编号：" prop="empId">
                  <el-input v-model="employee.empId" clearable></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="员工姓名：" prop="empName">
                  <el-input v-model="employee.empName" clearable></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="性别：">
                  <el-radio-group v-model="employee.empSex">
                    <el-radio :label="0" >男</el-radio>
                    <el-radio :label="1" >女</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="员工手机：" prop="empPhone">
                  <el-input v-model="employee.empPhone" clearable></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="员工邮箱：" prop="empEmail">
                  <el-input v-model="employee.empEmail" clearable></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="所属部门：" prop="empDepart">
                  <el-input v-model="employee.deptName" readonly></el-input>
                  <el-button @click="departDialogVisible = true">选择</el-button>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="是否离职：" >
                  <el-input value="在职" readonly>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="登录账号：">
                  <el-input v-model="employee.accountLogin" readonly></el-input>
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
              <el-form-item label="新建时间：">
                <el-input v-model="employee.createTime" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="新建人：">
                <el-input v-model="employee.createEmpName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="修改时间：">
                <el-input v-model="employee.modifyTime" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="修改人：">
                <el-input v-model="employee.modifyEmpName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="15">
              <el-form-item label="备注：" props="remark">
                <el-input type="textarea" rows="4" v-model="employee.remark" maxlength="200"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row>
        <el-col :offset="6" :span="5" class="margin-top20">
          <el-button type="primary" size="normal" @click="onSubmit('employee')">保存</el-button>
          <el-button type="primary" size="normal" @click="cancel">取消</el-button>
        </el-col>
      </el-row>
    </el-form>

    <!--分配所属部门弹窗内容-->
    <el-dialog ref="dialog" title="请选择所属部门" :visible.sync="departDialogVisible">
      <singleTree :treeData="treeData" v-on:checkInfo="getDepartInfo" @dialogClose="clearDepartInfo"></singleTree>
    </el-dialog>

  </div>
</template>

<script>
  import singleTree from '../../components/tree/singleTree';
  import '../../axios/api';

    export default {
      name: "employee_detail",
      components:{
        singleTree,
      },
      data() {
        // 校验员工编号是否已存在
        let validateEmpId = (rule, value, callback) => {
          // 判断是否为修改操作，是则比较员工编号是否有改变
          let type = this.$route.query.type;
          if("update" === type){
            if(this.$route.query.selectEmpId == value){
              callback();
            }
          }
          // 不是修改操作或员工编号已经改变
          this.$axios.get('/api/orgmanagement/employee/management/checkEmployeeId', {
            params: {
              empId: value ,
              date: new Date().getTime()
            }
          }).then(response => {
            if (0 === response.data.data || null === response.data.data) {
              callback();
            }else{
              callback(new Error('员工编号已存在，不允许重复'));
            }
          }).catch(error => {
            this.$message('网络错误，不能访问，请联系管理员');
          })
        };
        return {
          employee: {
            empId: '',
            accountLogin: '',
            empName: '',
            empSex:'',
            empPhone:'',
            empEmail:'',
            empDepart:'',
            empDepartName:'',
            isDimission: 0,
            isDelete: 0,
            createTime:'',
            createEmp:'',
            createEmpName:'',
            modifyTime:'',
            modifyEmp:'',
            modifyEmpName:'',
            remark:'',
            oldEmpId: '',
            oldEmpName: '',
            oldEmpDeptName: ''
          },
          rules: {
            empId: [
              { required: true, message: '员工编号为必填项，不允许为空', trigger: 'blur' },
              { pattern: /^[0-9]{1,20}$/, min: 1, max: 20, message: '不满足录入条件，仅支持1-20位数字', trigger: 'blur' },
              { validator: validateEmpId, trigger: 'blur' }
            ],
            empName: [
              { required: true, message: '员工姓名为必填项，不允许为空', trigger: 'blur' },
              { pattern: /^[^ ]{1,30}$/, min: 1, max: 30, message: '不满足录入条件，仅支持1-30位字符', trigger: 'blur' }
            ],
            empPhone: [
              {required: true, message: '员工手机号为必填项，不允许为空', trigger: 'blur'},
              {
                pattern: /^(13[0-9]|15[^4,\D]|16[6]|17[0-9]|18[0-9]|14[56789]|19[89])[0-9]{8}$/,
                message: '请录入正确的手机号',
                trigger: 'blur'
              }
            ],
            empDepart: [
              {required: true, message: '所属部门为必填项，不允许为空', trigger: 'change'},
            ],
            empEmail: [
              { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
            ],
            remark: [
              { min: 0, max: 200, message: '不满足录入条件，仅支持0-200位字符', trigger: 'blur' }
            ],
          },
          treeData:[],
          departDialogVisible: false,
          checkId:'',
          checkVal:'',
        }
      },
      created(){
        //获取操作类型（添加，修改）
        let type = this.$route.query.type;
        if("update" === type){
          let selectEmpId = this.$route.query.selectEmpId;
          this.getEmployee(selectEmpId);
        }
        this.getTreeData();
        this.employee.accountLogin =  sessionStorage.getItem("userName");
      },
      methods:{
        // 从后台获取部门树状结构数据
        getTreeData() {
          this.$axios.get('/api/orgmanagement/dept/validTree')
            .then(response=>{
              if (response.data.code == 200) {
                this.treeData = response.data.data;
                console.log(this.treeData);
              }else {
                this.$message(response.data.msg);
              }
            }).catch(error=>{
            this.$message('网络错误，不能访问，请联系管理员');
          })
        },
        // 从子组件中获取到选择的所属部门信息
        getDepartInfo(data) {
          this.employee.empDepart= data.split("#")[0];
          this.employee.deptName = data.split("#")[1];
          this.employee.empDepartName = data.split("#")[1];
          this.departDialogVisible = false
        },
        // 通过员工ID获取员工信息
        getEmployee(selectEmpId){
          this.$axios.get('/api/orgmanagement/employee/management/getEmployee', {
            params: {
              empId: selectEmpId ,
              date: new Date().getTime()
            }
          }).then(response => {
            if (response.data.code === 200) {
              this.employee = response.data.data;
              this.employee.oldEmpId = this.employee.empId;
              this.employee.oldEmpName = this.employee.empName;
              this.employee.oldEmpDeptName = this.employee.deptName;
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
        // 保存
        onSubmit(formName) {
          this.$refs[formName].validate((valid) => {
            if(valid){
              let type = this.$route.query.type;
              if("update" === type){
                this.doUpdateEmp();
              }else{
                this.doAddEmp();
              }
            }else {
              return false
            }
          })
        },
        // 调用添加员工API接口
        doAddEmp(){
          this.$axios.get('/api/orgmanagement/employee/management/addEmployee', {
            params: {
              employee: this.employee ,
              date: new Date().getTime()
            }
          }).then(response => {
            if (response.data.code === 200) {
              this.$message({
                type: 'success',
                message: '添加新员工成功!'
              });
              this.$router.push('/employee/list');
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
        // 调用修改员工API接口
        doUpdateEmp(){
          this.$axios.get('/api/orgmanagement/employee/management/updateEmployee', {
            params: {
              employee: this.employee ,
              date: new Date().getTime()
            }
          }).then(response => {
            if (response.data.code === 200) {
              this.$message({
                type: 'success',
                message: '修改员工信息成功!'
              });
              this.$router.push('/employee/list');
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
        // 取消
        cancel() {
          this.$confirm('确定取消？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$router.push('/employee/list');
          }).catch(() => {});
        },
        // 清除选中部门
        clearDepartInfo(val){
          this.employee.empDepart = '';
          this.employee.deptName = '';
          this.employee.empDepartName = '';
          this.departDialogVisible = val;
        },

      }
    }
</script>

<style scoped>

</style>
