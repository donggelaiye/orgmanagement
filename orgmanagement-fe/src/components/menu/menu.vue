<template>
  <div class="debug-menu" v-if="showMenus">
    <el-menu
      :default-active="defaultPage"
      class="app-menu"
      :router="true">
      <el-submenu v-for="(itemOne, indexOne) in navMenus"
                  :index="`${itemOne.path}`"
                  :key="`${itemOne.path}`"
                  v-if="!itemOne.hidden">
        <template slot="title">
          <i :class="[`${itemOne.icon}`]"></i>
          <span class="menu-font">{{itemOne.title}}</span>
        </template>
        <template v-for="(itemTwo, indexTwo) in itemOne.subMenus">
          <el-menu-item
            :index="`${itemTwo.path}`"
            v-if="!itemTwo.subMenus && !itemTwo.hidden"
            :key="`${itemTwo.path}`">{{itemTwo.title}}

          </el-menu-item>
          <el-submenu
            :index="`${itemTwo.path}`"
            :key="`${itemTwo.path}`"
            v-if="itemTwo.subMenus && !itemTwo.hidden">
            <template slot="title">{{itemTwo.title}}</template>
            <el-menu-item
              v-for="(itemThree, indexThree) in itemTwo.subMenus"
              :index="`${itemThree.path}`"
              :key="`${itemThree.path}`"
              v-if="!itemThree.hidden">{{itemThree.title}}
            </el-menu-item>
          </el-submenu>
        </template>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>

  export default {
    title: "menu",
    data () {
      return {
        showMenus: false,
        defaultPage: '/',
        navMenus: []
      }
    },
    mounted () {
      this.navMenus = JSON.parse(sessionStorage.getItem("menuList"));
      this.showMenus = true;
    }
  }
</script>

<style scoped>
.app-menu{
  height: 580px;
  color: gray;
}
.menu-font {
  font-weight: bold;
  color: gray;
}
</style>
