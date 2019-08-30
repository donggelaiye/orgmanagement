<template>
    <div>
      <el-form ref="employee" status-icon :model="employee" label-width="120px" size="mini">
        <el-row>
          <el-col :span="20" class="margin-left20 font-title">
            <h3>员工信息</h3>
          </el-col>
          <el-col :span="24">
            <el-row>
              <el-col :span="8">
                <el-form-item label="员工编号：" prop="emp_id">
                  <el-input v-model="employee.empId" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="员工姓名：" prop="emp_name">
                  <el-input v-model="employee.empName" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="性别：">
                  <el-radio-group v-model="employee.empSex" disabled>
                    <el-radio :label="0" >男</el-radio>
                    <el-radio :label="1" >女</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="员工手机：" prop="emp_phone">
                  <el-input v-model="employee.empPhone" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="员工邮箱：" prop="emp_email">
                  <el-input v-model="employee.empEmail" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="所属部门：" prop="emp_depart">
                  <el-input v-model="employee. deptName" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="是否离职：" >
<!--                  <el-select v-model="employee.isDimission" placeholder="请选择" class="select-width" disabled>
                    <el-option label="离职" value="1">离职</el-option>
                    <el-option label="在职" value="0">在职</el-option>
                  </el-select>-->
                  <el-input v-model="employee.isDimission" readonly></el-input>
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
                  <el-input type="textarea" rows="4" v-model="employee.remark" maxlength="200" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
<!--        <el-row>-->
<!--          <el-col :offset="6" :span="5" class="margin-top20">-->
<!--            <el-button type="primary" size="normal" @click="onSubmit('employee')">保存</el-button>-->
<!--            <el-button type="primary" size="normal" @click="cancel">取消</el-button>-->
<!--          </el-col>-->
<!--        </el-row>-->

      </el-form>
    </div>
</template>

<script>
    export default {
        name: "detail",
      data(){
          return {
            employee: {
              empId: '',
              empName: '',
              empSex:'',
              empPhone:'',
              empEmail:'',
              empDepart:'',
              deptName:'',
              isDimission: '',
              createTime:'',
              createEmp:'',
              createEmpName:'',
              modifyTime:'',
              modifyEmp:'',
              modifyEmpName:'',
              remark:''
            },
          }
      },
      mounted:function () {
        this.$axios.get('/api/orgmanagement/personal/management/getEmployeeByAccountLogin',
          {
            params:
              {
                accountLogin:sessionStorage.getItem("accountLogin"),
              }
          }).then(response =>{
            if (response.data.code === 200){
              this.employee = response.data.data;
              if(0 === this.employee.isDimission){
                this.employee.isDimission = '在职';
              }else{
                this.employee.isDimission = '离职';
              }
            } else {
              this.$message.error(response.data.msg);
            }
        })
      },
      methods:{

      }
    }
</script>

<style scoped>

</style>
