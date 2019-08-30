<template>
  <el-tree
    :data="treeData"
    :defaultProps="defaultProps"
    show-checkbox
    ref="tree"
    node-key="id"
    :check-strictly="true"
    @check="handleCheck"
    :default-checked-keys="editCheckIds">
  </el-tree>

</template>

<script>
  export default {
    title: 'multi_tree',
    props: ["treeData", "checkedKeys"],
    data() {
      return {
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        editCheckIds: []
      };
    },
    created: function () {
      this.editCheckIds = this.checkedKeys;
    },
    methods: {
      handleCheck(node, data) {
        this.editCheckIds = this.$refs.tree.getCheckedKeys();
        this.$emit("checkInfo", this.editCheckIds);
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
