<template>
  <div >
    <el-row>
      <el-col><vheader></vheader></el-col>
    </el-row>
    <el-row>
      <el-col :span="4"><vmenu></vmenu></el-col>
      <el-col :span="20"><router-view/></el-col>
    </el-row>
    <el-row>
<!--      <el-col><vfooter></vfooter></el-col>-->
    </el-row>
  </div>
</template>
<script>
  import vheader from '@/components/header/header'
  import vmenu from '@/components/menu/menu'
  import vfooter from "./footer"

  export default {
    title: 'index',
    components:{
      vfooter,
      vheader,
      vmenu
    },
    created(){
    }
  }
</script>

<style scoped>

</style>
