<template>
  <div>
    <el-form ref="account" :rules="rules" status-icon :model="account" label-width="120px" size="mini" >
      <el-row>
        <el-col :span="20" class="margin-left20 font-title">
          <h3>重置密码</h3>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8" style="margin-bottom: 10px">
          <el-form-item label="新密码：" prop="newPwd">
            <el-input type="password" v-model="account.newPwd"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8" style="margin-bottom: 10px">
          <el-form-item label="重复密码：" prop="newPwdRepeat">
            <el-input type="password" v-model="account.newPwdRepeat"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :offset="6" :span="5" class="margin-top20">
          <el-button type="primary" size="normal" @click="onSubmit('account')">保存</el-button>
          <el-button type="primary" size="normal" @click="cancel">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
  import md5 from 'js-md5';
  export default {
    name: "pwdUpdate",
    data(){
      var checkPwd = (rule, value, callback) => {
        var iNow = 0;
        if (value.match(/\s/)) {
          callback(new Error("禁止输入空白字符"));
        }
        if (value.match(/[\u4E00-\u9FA5]/g)) {
          callback(new Error("禁止输入中文"));
        }
        if(value.match(/[0-9]/g)){
          iNow++;
        }
        if(value.match(/[a-z]/g)){
          iNow++;
        }
        if(value.match(/[A-Z]/g)){
          iNow++;
        }
        if(value.match(/[-|'":?{}()<>.~!@#$%^&*_=\\/+]/g)){
          iNow++;
        }
        if(iNow < 3){
          callback(new Error("至少包含数字，大写字符，小写字符，特殊符号中的三种"));
        }

        callback();
      };
      var checkPwdRepeat = (rule, value, callback) =>{
        if (value === this.account.newPwd){
          callback();
        }else {
          callback(new Error("两次输入的密码不一致"))
        }
      };
      return{
        account:{
          accountLogin:"",
          newPwd:"",
          newPwdRepeat:""
        },
        rules:{
          newPwd:[
            { required: true, message: '请输入新密码', trigger: 'blur' },
            { max: 32, message: '密码最长32位', trigger: 'blur' },
            { validator:checkPwd,trigger:'blur' },
          ],
          newPwdRepeat:[
            { required: true, message: '请确认新密码', trigger: 'blur' },
            { max: 32, message: '密码最长32位', trigger: 'blur' },
            { validator:checkPwd,trigger:'blur' },
            { validator:checkPwdRepeat,trigger:'blur' }
          ]
        }

      }
    },
    methods:{
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if(valid){
            console.log(this.$route);
            this.$axios({
                url:'/api/orgmanagement/personal/management/resetPwd',
                method:"get",
                params:{
                  accountLogin:this.$route.query.accountLogin,
                  newPwd:md5(this.account.newPwd)
                }
              },
            ).then(response=>{
              if (response.data.code == 200) {
                debugger
                this.$message.success('修改密码成功!');
                this.$router.push('/login');
              }else {
                this.$message(response.data.msg);
              }
            })
            console.log(this.$route);
          }else {
            return false
          }
        })
      },
      // 取消
      cancel() {
        this.$confirm('确定取消？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$router.go(-1);
        }).catch(() => {});
      }
    }
  }
</script>

<style scoped>

</style>
