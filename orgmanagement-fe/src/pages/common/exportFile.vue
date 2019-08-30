<template>
  <el-row>
    <el-col :offset="1" :span="23" class="margin-top20">
      <el-checkbox-group v-model="checkList" @change="handleSelectionChange">
        <el-checkbox v-for="opt in multiOptions" :label="opt" :key="opt"></el-checkbox>
      </el-checkbox-group>
    </el-col>
  </el-row>
</template>

<script>

    export default {
      name: "export",
      //从父组件获取的值
      props: ["checkOptions", "sonChecked"],
      data () {
        return {
          checkList: this.sonChecked,
          multiOptions: this.checkOptions
        }
      },
      methods: {
        handleSelectionChange(val) {
          this.$emit('checkInfo', val);
        }
      },
      watch: {
        sonChecked: function () {
          this.checkList=this.sonChecked;
          this.$emit('checkInfo', this.checkList);
        }
      }
    }
</script>

<style scoped>

</style>
