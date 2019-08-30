<script src="../../../constant/util.js"></script>
<template>
  <div>
    <p><span>角色【{{roleName}}】所拥有的特殊权限：</span>
      <el-button-group>
        <el-button type="primary" size="small" @click="onSubmit">提交</el-button>
        <el-button size="small" @click="onCancle">取消</el-button>
      </el-button-group>
    </p>
    <div>
      <PermissionTree :treeData="sysResourceTree" :defaultCheckedKeys="originResourceIdList"
                      @checkInfo="getCheckedKeys"></PermissionTree>
    </div>
  </div>
</template>

<script>
  import PermissionTree from '../../../components/tree/PermissionTree'
  import '../../../axios/api'
  import ArrayUtil from '../../../constant/arrayUtil'

  let thisVue;
  export default {
    components: {
      PermissionTree: PermissionTree,
    },
    data() {
      return {
        //角色Id
        roleId: '',
        //角色名
        roleName: '',
        //权限树
        sysResourceTree: [],
        //账号原始权限
        originResourceIdList: [],
        //被选中的叶子权限节点
        checkedKeys: [],
        //待移除权限Id
        revokeResourceIdList: [],
        //待添加权限Id
        grantResourceIdList: [],
      }
    },
    beforeCreate: function () {
      thisVue = this;
    },
    created: function () {
      this.roleId = this.$route.query.roleId;
      this.roleName = this.$route.query.roleName;
      this.getPermissionData();
    },
    methods: {
      getPermissionData() {
        this.$axios({
          method: 'post',
          url: '/api/orgmanagement/permission/getRoleResourceInfo',
          data: JSON.stringify({
            roleId: thisVue.roleId,
          })
        }).then(response => {
          if (response.data.code == 200) {
            //树
            this.sysResourceTree = response.data.data.sysResourceTree;
            //原始拥有权限
            this.originResourceIdList = response.data.data.roleResourceIdList;
            //选中叶子节点
            this.checkedKeys = response.data.data.roleResourceIdList;
          } else {
            this.$message(response.data.msg);
          }
        })
        //获取失败
          .catch(error => {
            console.log(error)
          })
      },
      //为角色分配权限
      grantAccountRole() {
        //分配权限判空,无操作直接返回
        if (ArrayUtil.isEmpty(thisVue.grantResourceIdList) && ArrayUtil.isEmpty(thisVue.revokeResourceIdList)) {
          thisVue.$message({
            type: 'success',
            message: '操作成功!'
          });
          thisVue.$router.push('/role/management')
          return false;
        }
        ;
        this.$axios({
          method: 'post',
          url: '/api/orgmanagement/permission/grantRoleResource',
          data: JSON.stringify({
            roleId: thisVue.roleId,
            grantResourceIdList: thisVue.grantResourceIdList,
            revokeResourceIdList: thisVue.revokeResourceIdList,
          })
        }).then(response => {
          if (response.data.code == 200) {
            this.$message({
              type: 'success',
              message: '操作成功!'
            });
            thisVue.$router.push('/role/management');
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
      onCancle() {
        thisVue.$router.push('/role/management')
      },
      analyseData() {
        //最终拥有权限 - 原始拥有权限 = 分配权限
        thisVue.grantResourceIdList = ArrayUtil.differenceSet(thisVue.checkedKeys, thisVue.originResourceIdList);
        //原始拥有 - 最终拥有 = 移除权限
        thisVue.revokeResourceIdList = ArrayUtil.differenceSet(thisVue.originResourceIdList, thisVue.checkedKeys);
      },
      //选中节点后获取选中叶子结点key
      getCheckedKeys(keys) {
        this.checkedKeys = keys;
      },
    },
  };
</script>
