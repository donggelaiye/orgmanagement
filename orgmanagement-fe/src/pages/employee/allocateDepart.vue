<template>
  <div>
    <!--员工信息表单-->
    <el-form ref="employee" status-icon :model="employee" label-width="125px" size="mini">
      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>分配部门</h3>
        </el-col>
        <el-col :span="24">
          <el-row>
            <el-col :span="8">
              <el-form-item label="员工编号：">
                <el-input v-model="employee.empId" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="员工姓名：">
                <el-input v-model="employee.empName" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="员工性别：">
                <el-input v-model="employee.empSex" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="员工手机：">
                <el-input v-model="employee.empPhone" readonly></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="当前归属部门：">
                <el-input v-model="employee.empDepart" readonly></el-input>
              </el-form-item>
            </el-col>
            <el-form-item label="调整后归属部门: ">
              <el-input v-model="employee.empReDepartName" clearable readonly></el-input>
              <el-button @click="departDialogVisible = true">选择</el-button>
            </el-form-item>
          </el-row>
        </el-col>
      </el-row>
      <el-row>
        <el-col :offset="6" :span="5" class="margin-top20">
          <el-button type="primary" size="normal" @click="onSubmit()">保存</el-button>
          <el-button type="primary" size="normal" @click="cancel">取消</el-button>
        </el-col>
      </el-row>
    </el-form>

    <!--分配所属部门弹窗内容-->
    <el-dialog ref="dialog" title="请选择归属部门" :visible.sync="departDialogVisible">
      <singleTree :treeData="treeData" v-on:checkInfo="getDepartInfo"></singleTree>
    </el-dialog>
  </div>
</template>

<script>
  import singleTree from '../../components/tree/singleTree';
  import '../../axios/api';

   export default {
     name: "allocateDepart",
     components:{
       singleTree,
     },
     data() {
       return {
         // 从后台获取树状结构数据
         treeData: [],
         employee: {
           empId: '',
           empName: '',
           empSex: '',
           empPhone: '',
           empDepart: '',
           empReDepart: '',
           empReDepartName: ''
         },
         departDialogVisible: false,
         checkId:'',
         checkVal:''
       }
     },
     created:function(){
       //初始化获取部门结构数据
       this.getTreeData();
       let selectEmpId = this.$route.query.selectEmpId;
       this.getEmployee(selectEmpId);
     },
     methods: {
       // 从后台获取部门树状结构数据
       getTreeData() {
         this.$axios.get('/api/orgmanagement/dept/validTree')
           .then(response=>{
             if (response.data.code == 200) {
               this.treeData = response.data.data;
             }else {
               this.$message(response.data.msg);
             }
           }).catch(error=>{
           this.$message('网络错误，不能访问，请联系管理员');
         })
       },
       // 从子组件中获取到选择的所属部门信息
       getDepartInfo(data) {
         console.log(data);
         this.employee.empReDepart= data.split("#")[0];
         this.employee.empReDepartName = data.split("#")[1];
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
             if(0 === this.employee.empSex){
               this.employee.empSex = '男';
             }else{
               this.employee.empSex = '女';
             }
             this.employee.empDepart = this.employee.deptName;
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
       // 提交修改部门编号
       onSubmit() {
          if(undefined === this.employee.empReDepartName){
            this.$message({
              type: 'error',
              message: "调整后归属部门不允许为空!"
            });
          }else{
            this.doUpdateEmpDept();
          }
       },
       // 调用修改员工部门API接口
       doUpdateEmpDept(){
         this.$axios.get('/api/orgmanagement/employee/management/updateEmployeeDept', {
           params: {
             empId: this.employee.empId,
             empDepart:this.employee.empReDepart,
             date: new Date().getTime()
           }
         }).then(response => {
           if (response.data.code === 200) {
             this.$message({
               type: 'success',
               message: '修改员工归属部门成功!'
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
       }
     }
   }
</script>

<style scoped>

</style>
