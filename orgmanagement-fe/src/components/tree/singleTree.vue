<template>
  <div>
  <el-tree
    :data="treeData"
    :defaultProps="defaultProps"
    show-checkbox
    ref="tree"
    node-key="id"
    :check-strictly="true"
    @check = "handleCheck">
  </el-tree>
  <el-button class="margin-top20" icon="el-icon-edit-outline" @click="clearDepartInfo" round>清空</el-button>
  </div>
</template>

<script>
export default {
  title: 'single_tree',
  props:["treeData"],
  data() {
    return {
      defaultProps: {
        children: 'children',
        label: 'label',
      }
    };
  },
  created:function(){
  },
  methods: {
    handleCheck(node, data) {
      this.$refs.tree.setCheckedKeys([node.id]);
      this.$emit("checkInfo", node.id + '#' + node.label);
    },
    clearDepartInfo() {
      this.$refs.tree.setCheckedKeys([]);
      this.$emit("dialogClose", false);
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
