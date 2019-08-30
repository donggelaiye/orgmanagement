<template>
  <div class="header">
    <el-row>
      <el-col :span="17" class="title">
        <span>UCAR</span>
      </el-col>
      <!--<el-col :span="2" class="show_name" style="text-align: right">-->
        <!---->
      <!--</el-col >-->
      <el-col :span="7" class="show_name">
        <span >欢迎你，</span>
        <el-dropdown STYLE="font-size: 18px;color: #ffffff;">
          <span class="header-btn">
              {{userName}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="$router.push('/personal/empDetail')"><i style="padding-right: 8px" class="fa fa-cog"></i>个人信息</el-dropdown-item>
            <el-dropdown-item @click.native="$router.push('/personal/pwdUpdate')"><i style="padding-right: 8px" class="fa fa-cog"></i>密码修改</el-dropdown-item>
            <el-dropdown-item @click.native="loginOut"><i style="padding-right: 8px" class="fa fa-key"></i>退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>


    </el-row>
  </div>
</template>

<script>
    export default {
        title: "header",
        data(){
          return{
            userName: ''
          }
        },
      created:function () {
          this.userName = sessionStorage.getItem('accountLogin');
      },
      methods: {
        requestLogout() {
          this.$axios({
            method: 'post',
            url: '/api/orgmanagement/logout'
          }).then(response => {
            this.$store.dispatch('Logout').then(()=>{
              this.$router.push('/');
            });
          })
        },
        loginOut() {
          this.$confirm('确定退出系统？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$store.dispatch('Logout').then(()=>{
              this.$router.push('/');
            });
          }).catch(() => {

          });

        }
      }
    }
</script>

<style lang="scss" scoped>
.header{
  height: 60px;
  width: 100%;
  background-color: #169bd5;
  opacity: 0.9;
  box-shadow: rgba(255,255,255,0.25) 0px 1px 0px, inset rgba(255,255,255,0.25) 0px 1px 0px, inset rgba(0,0,0,0.25) 0px 0px 0px, inset rgba(255,255,255,0.03) 0px 20px 0px, inset rgba(0,0,0,0.15) 0px -20px 20px, inset rgba(255,255,255,0.05) 0px 20px 20px;
}

  .title{
    font-size: 30px;
    font-weight: bold;
    color: #ffffff;
    padding-top: 15px;
    padding-left: 30px;
  }

  .show_name{
    padding-top: 20px;
    padding-right: 15px;
    color: #ffffff;
    text-align: right;
  }

  .logout-button{
    padding-top: 17px;
    font-size: 34px;
    cursor: pointer;
    color: #ffffff;
    a:hover{
      background-color: white;
      color: #000000;
    }
  }
</style>
