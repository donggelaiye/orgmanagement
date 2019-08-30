import axios from 'axios'
import qs from "qs";
import Vue from "vue";
import '../main'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';

/****** 创建axios实例 ******/
const service = axios.create({
  baseURL: process.env.BASE_URL,  // api的base_url
  // timeout: 10000  // 请求超时时间
});

/****** request拦截器==>对请求参数做处理 ******/
service.interceptors.request.use(config => {
  if (sessionStorage.getItem('token')) {
    config.headers.post['Content-Type'] = 'application/json';
    config.headers.common['Authorization'] = sessionStorage.getItem('token');
  }
  config.headers.common["Access-Control-Allow-Methods"] = "OPTIONS,POST,GET";
  config.headers.common["Access-Control-Allow-Headers"] = "x-requested-with";
  config.headers.common["Access-Control-Allow-Origin"] = "*";
  return config;
}, error => {  //请求错误处理
  app.$vux.toast.show({
    type: 'warn',
    text: error
  });
  Promise.reject(error)
});

/****** respone拦截器==>对响应做处理 待完善******/
service.interceptors.response.use(
  response => {  //成功请求到数据
    if (response.data.code === 401) {
      alert('登录过期，请重新登录');
      window.location.href = '/login';
      return false;
    } else if (response.data.code === 403) {
      alert(response.data.msg);
      window.location.href = '/index';
      return false;
    } else {
      return Promise.resolve(response);
    }
  },
  error => {  //响应错误处理
    console.log(error);
    if (error.response === undefined){
      alert('该账号可能发生变动，请尝试登录或者联系管理员！');
      window.location.href = '/login'
    }

    return false;
  }
);

Vue.prototype.$axios = service
