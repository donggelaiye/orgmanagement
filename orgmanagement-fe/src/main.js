// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router/index'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import './assets/index.css' //自定义css样式
import axios from 'axios'
import VueAxios from 'vue-axios'
import Vuex from 'vuex'; //引入状态管理
import permission from './store/permission'
import menuList from './store/dynamic_menu'

import 'babel-polyfill'

Vue.config.productionTip = false
Vue.prototype.HOST = '/api';//封装跨域接口


Vue.use(ElementUI)
Vue.use(VueAxios, axios)
Vue.use(Vuex)

const SET_TOKEN = 'SET_TOKEN'; //用常量代替事件类型，获取用户TOKEN
const SET_ACCOUNT_LOGIN = 'SET_ACCOUNT_LOGIN'; //设置名称
const LOGIN_OUT = 'LOGIN_OUT'; //删除用户信息
const SET_DEPT_TREE = 'SET_DEPT_TREE' //获取部门树状信息


//注册状态管理全局参数
var store = new Vuex.Store({
  modules: {
    permission,
    menuList
  },
  state: {
    token: '',
    accountLogin: '',
    deptTree: []
  },
  mutations: {
    // state用于变更登录状态 此处为登录
    [SET_TOKEN](state, token) {
      sessionStorage.setItem("token", token);
      state.token = token;
    },
    [SET_ACCOUNT_LOGIN](state, accountLogin) {
      sessionStorage.setItem("accountLogin", accountLogin);
      state.accountLogin = accountLogin;
    },
    // 退出登录
    [LOGIN_OUT](state) {
      sessionStorage.clear();
      state.token = '';
      state.accountLogin = '';
      state.deptTree = [];
      state.menuList.newMenuList = [];
      state.menuList.buttonList = {};
    },
    [SET_DEPT_TREE](state, deptTree) {
      sessionStorage.setItem("deptTree", JSON.stringify(deptTree));
      state.deptTree = deptTree;
    }

  },
  actions: {
    Logout({commit}) {
      commit('LOGIN_OUT');
    }
  }
});


//钩子函数
router.beforeEach((to, from, next) => {

  store.state.token = sessionStorage.getItem("token");
  // 判断路由是否需要登录权限
  if (to.meta.requireAuth) {
    if (store.state.token !== "" && store.state.token !== null && store.state.token !== undefined) {
      next();

    } else {
      if (to.fullPath.indexOf('list') > -1) {
        next({
          path: `/login`,
          query: {
            redirect: to.fullPath  //将跳转的路由path作为路径，登录成功后转到该路由
          }
        })
      } else {
        next({path: `/login`})
      }

    }

  } else {
    next()

  }

});


router.afterEach((to, from) => {

})


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store, //注册组件
  components: {App},
  template: '<App/>'
})


