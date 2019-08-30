<template>
  <el-main>
    <el-button type="text" @click="dialogVisible = true">地图</el-button>

    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%">


      <div id="map" style="width: 800px;height: 750px"></div>

      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
    </el-dialog>
  </el-main>


</template>

<script>
  import AMap from 'AMap'
    export default {
        name: "test",
      data() {
        return {
          dialogVisible: false
        };
      },
      mounted(){
        this.loadMap();
      },
      methods:{
          loadMap(){
            let map = new AMap.Map('map', {
              center: [116.397428, 39.90923],
              resizeEnable: true,
              zoom: 10
            });
            AMap.plugin(['AMap.ToolBar', 'AMap.Scale'], function () {
              map.addControl(new AMap.ToolBar())
              map.addControl(new AMap.Scale())
            })
          }
        }
    }
</script>

<style scoped>

</style>
