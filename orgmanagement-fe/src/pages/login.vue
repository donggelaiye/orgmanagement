<template>
    <el-container class="login">
      <el-header class="header"></el-header>
      <el-main>
        <el-row>
          <el-col :offset="15" :span="6" class="login-form">
            <el-form ref="account" :model="account" :rules="rules" label-width="120px" size="mini">
              <el-row >
                <el-col class="margin-left60 font-title">
                  <h1>优车大平台欢迎你！</h1>
                </el-col>
                <el-col class="margin-top20">
                  <el-row>
                    <el-col>
                      <el-form-item label="登录账号：" prop="accountLogin">
                        <el-input v-model="account.accountLogin" clearable></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col>
                      <el-form-item label="登录密码：" prop="accountPassword">
                        <el-input type="password" v-model="account.accountPassword" clearable></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col class="margin-left50">
                      <span class="login-msg">{{account.message}}</span>
                    </el-col>
                  </el-row>
                </el-col>
                <el-col class="margin-top20 margin-left50">
                  <el-button class="login-button" type="primary" :loading="submitLoading" @click="onSubmit('account')">
                    <span v-if="!submitLoading">立即登录</span>
                    <span v-else>正在登录，请稍候...</span>
                  </el-button>
                </el-col>
              </el-row>
            </el-form>
          </el-col>
        </el-row>

    </el-main>
    <el-footer></el-footer>
  </el-container>
</template>

<script>
    import md5 from 'js-md5';
    import '../axios/api';

    export default {
        name: "login",
        data(){
          return {
            account: {
              accountLogin: '',
              accountPassword: '',
              message:''
            },
            submitLoading: false,
            rules: {
              accountLogin: [
                {required: true, message: '用户名不为空', trigger: 'blur'}
              ],
              accountPassword: [
                {required: true, message: '密码不为空', trigger: 'blur'}
              ]
            }
          }
        },
        created:function(){
        },
        methods:{
          // 登录时获取部门树结构信息存到缓存
          getDepartTreeData() {
            this.$axios({
              method: 'get',
              url: '/api/orgmanagement/dept/tree'
            }).then(response => {
                if (response.data.code == 200) {
                  this.$store.commit('SET_DEPT_TREE', response.data.data);
                  // this.$store.dispatch('AddRouter');
                  //跳转页面
                  if (this.$route.query.redirect) {
                    this.$router.push(this.$route.query.redirect);
                  }else {
                    this.$router.push('/index');
                  }
                }else {
                  this.$message.error(response.data.msg);
                }
              })
              //获取失败
              .catch(error => {
                this.$message.error('网络错误，不能访问，请联系管理员');
              })

          },
          //登录请求
          login(){
            this.$axios({
              method: 'post',
              url: '/api/orgmanagement/security/login',
              data:
                {
                  userName: this.account.accountLogin,
                  password: md5(this.account.accountPassword)
                }
            })
            //then获取成功；response成功后的返回值（对象）
              .then(response => {
                if (response.data.code === 200) {
                  //将token存到本地
                  this.$store.commit('SET_TOKEN', response.data.data.SUID);
                  this.$store.commit('SET_ACCOUNT_LOGIN', response.data.data.ACCOUNTLOGIN);
                  //装载权限信息
                  this.$store.dispatch('GenerateRoutes', response.data.data).then(()=>{
                    //登录成功后将部门树状结构存入缓存
                    this.getDepartTreeData();
                  });
                } else {
                  this.submitLoading = false;
                  //首次登录跳转重置密码页面
                  if (response.data.code === 202) {
                    this.$store.commit('SET_TOKEN', response.data.data.SUID);
                    this.$store.commit('SET_ACCOUNT_LOGIN', response.data.data.ACCOUNTLOGIN);
                    this.$confirm(response.data.msg, '提示', {
                      confirmButtonText: '确定',
                      cancelButtonText: '取消',
                      type: 'warning'
                    }).then(() => {
                      this.$router.push({path:'/personal/resetPwd', query:{'accountLogin':response.data.data.ACCOUNTLOGIN}});
                    }).catch(() => {});

                  }
                  this.$message.error(response.data.msg);
                }
              })
              //获取失败
              .catch(error => {

              })

          },
          onSubmit(formName) {
            this.$refs[formName].validate((valid) => {
              if(valid){
                this.submitLoading = true;
                this.login();
              }else {
                return false
              }
            })
          }
        }
  }
</script>

<style scoped>

</style>
