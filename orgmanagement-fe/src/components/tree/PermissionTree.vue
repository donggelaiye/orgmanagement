<template>
  <el-tree
    :data="treeData"
    :defaultProps="defaultProps"
    :default-checked-keys="defaultCheckedKeys"
    show-checkbox
    ref="tree"
    node-key="id"
    @check="handleCheck"
    :check-strictly="false"
    >
  </el-tree>

</template>

<script>
  export default {
    title: 'permission_tree',
    props: ["treeData","defaultCheckedKeys"],
    data() {
      return {
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        checkedKeys: [],
      };
    },
    created: function () {
      //获取公司树状数据赋给data展示
    },
    methods: {
      handleCheck() {
        //仅获取叶子节点Id
        this.checkedKeys = this.$refs.tree.getCheckedKeys(true);
        //监听checkInfo,返回editCheckIds
        this.$emit("checkInfo", this.checkedKeys)
      },
    },
  }
</script>
<style scoped>

</style>
