<template>
  <el-row>
    <el-col :span="20" class="margin-left20 font-title">
      <h3>账号 {{this.loginAccount}} 权限配置</h3>
    </el-col>
    <el-col :offset="19" :span="5">
      <el-button type="primary" @click="onSubmit">保存</el-button>
      <el-button @click="onCancel">取消</el-button>
    </el-col>
    <el-col :offset="4" :span="20">
      <el-transfer v-model="unassignedId" :data="roleData"
                   :titles="['已拥有角色', '可分配角色']"
                   :button-texts="['分配', '移除']"
                   :format="{
        noChecked: '${total}',
        hasChecked: '${checked}/${total}',
      }">
      </el-transfer>
    </el-col>
    <el-col style="margin-top: 20px" :offset="4" :span="20">
      <div>
        <p>所拥有的特殊权限：</p>
        <PermissionTree :treeData="permissionTreeData" :defaultCheckedKeys="originPermissionId"
                        @checkInfo="getCheckedKeys"></PermissionTree>
      </div>
    </el-col>


  </el-row>
</template>

<script>
  import PermissionTree from '../../components/tree/PermissionTree'
  import ArrayUtil from '../../constant/arrayUtil'
  import '../../axios/api'

  let thisVue;
  export default {

    name: "account_permission",

    components: {
      PermissionTree: PermissionTree,
    },
    data() {
      return {
        //登录账号
        loginAccount: '',
        //全部角色信息
        roleData: [],
        //原始拥有角色Id
        originRoleId: [],
        //未分配角色Id
        unassignedId: [],
        //原始未分配角色Id
        originUnassignedId: [],
        //待移除角色Id
        revokeRoleIdList: [],
        //待添加角色Id
        grantRoleIdList: [],
        //权限树
        permissionTreeData: [],
        //账号原始权限
        originPermissionId: [],
        //被选中的叶子权限节点
        checkedKeys: [],
        //待移除权限Id
        revokePermissionIdList: [],
        //待添加权限Id
        grantPermissionIdList: [],
      }
    },
    beforeCreate: function () {
      thisVue = this;
    },
    created: function () {
      console.info("create");
      console.log(this.$route.query);
      let accountLogin = this.$route.query.accountLogin;
      // console.log(accountLogin);
      this.loginAccount = accountLogin;
      console.log(this.loginAccount);
      this.getRoleData();
      this.getPermissionData();
    },
    methods: {
      getRoleData() {
        //存储Vue实例的this指向
        this.$axios({
          method: 'post',
          url: '/api/orgmanagement/permission/getAccountRoleInfo',
          data: JSON.stringify({
            loginAccount: thisVue.loginAccount,
          })
        })
          .then(response => {
            if (response.data.code == 200) {
              response.data.data.roleList.forEach(function (itm, idx) {
                thisVue.roleData.push({
                  key: itm.roleId,
                  label: itm.roleName
                });
              });
              this.originUnassignedId = response.data.data.unassignedRoleIdList;
              this.unassignedId = response.data.data.unassignedRoleIdList;
              this.originRoleId = response.data.data.accountRoleIdList;
            } else {
              this.$message(response.data.msg);
            }
          })
          //获取失败
          .catch(error => {
            console.log(error)
          })
      },
      getPermissionData() {
        this.$axios({
          method: 'post',
          url: '/api/orgmanagement/permission/getAccountPermissionInfo',
          data: JSON.stringify({
            loginAccount: thisVue.loginAccount
          })
        }).then(response => {
          if (response.data.code == 200) {
            this.permissionTreeData = response.data.data.permissionTree;
            this.originPermissionId = response.data.data.accountPermissionList;
            this.checkedKeys = response.data.data.accountPermissionList;
          } else {
            this.$message(response.data.msg);
          }
        })
        //获取失败
          .catch(error => {
            console.log(error)
          })
      },
      //为账户分配角色
      grantAccountRole() {
        //判空
        if (ArrayUtil.isEmpty(thisVue.revokeRoleIdList) && ArrayUtil.isEmpty(thisVue.grantRoleIdList) &&
          ArrayUtil.isEmpty(thisVue.revokePermissionIdList) && ArrayUtil.isEmpty(thisVue.grantPermissionIdList)) {
          this.$message({
            type: 'success',
            message: '操作成功!',
          });
          thisVue.$router.push('/account/list')
          return false;
        };
        this.$axios({
          method: 'post',
          url: '/api/orgmanagement/permission/grantAccountRoleAndPermission',
          data: JSON.stringify({
            loginAccount: thisVue.loginAccount,
            revokeRoleIdList: thisVue.revokeRoleIdList,
            grantRoleIdList: thisVue.grantRoleIdList,
            revokePermissionIdList: thisVue.revokePermissionIdList,
            grantPermissionIdList: thisVue.grantPermissionIdList
          })
        }).then(response => {
          if (response.data.code == 200) {
            this.$message({
              type: 'success',
              message: '操作成功!',
            });
            thisVue.$router.push('/account/list')
          } else {
            this.$message(response.data.msg);
          }
        })
        //获取失败
          .catch(error => {
            console.log(error)
          })
      }
      , onSubmit() {
        this.$confirm('是否确认提交?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true,
          type: 'warning'
        }).then(() => {
          this.analyseData();
          this.grantAccountRole();
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          });
        });
      },
      onCancel() {
        this.$router.push('/account/list')
      },
      analyseData() {
        //原始未分配 - 最终未分配 = 分配角色
        thisVue.grantRoleIdList = ArrayUtil.differenceSet(thisVue.originUnassignedId, thisVue.unassignedId);
        //最终未分配 - 原始未分配 = 移除角色
        thisVue.revokeRoleIdList = ArrayUtil.differenceSet(thisVue.unassignedId, thisVue.originUnassignedId);
        //最终拥有权限 - 原始拥有权限 = 分配权限
        thisVue.grantPermissionIdList = ArrayUtil.differenceSet(thisVue.checkedKeys, thisVue.originPermissionId);
        //原始拥有 - 最终拥有 = 移除权限
        thisVue.revokePermissionIdList = ArrayUtil.differenceSet(thisVue.originPermissionId, thisVue.checkedKeys);
      },
      //选中节点后获取选中叶子结点key
      getCheckedKeys(keys) {
        this.checkedKeys = keys;
      },
    },

  };
</script>
