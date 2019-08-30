# orgmanagement-fe

> A Vue.js project

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev
 
# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```
## 目录结构
```
├── build                  构建相关
├── config                 环境配置文件
├── node_modules           依赖包目录
├── src                    代码编写的目录
│   ├── assets             资源文件夹
│   ├── components         组件文件夹（非完整页面）
│   ├── constant           静态变量文件（目前是固定菜单）
│   ├── pages              页面文件夹（完整页面）
│   ├── router             路由文件夹
│   ├── app.vue            根组件
│   └── main.js            入口文件
├── dist                   编译输出目录
├── babelrc                babel 配置文件
├── eslintrc.js            eslint 配置文件
├── .postcssrc.js          postcss 配置文件
├── package.json           项目依赖包配置文件
└── index.html             html模板文件
```

## 相关规则 

> 1、页面组件

需要使用组件及其属性、方法可以直接访问ElemetUI官网链接。
https://element.eleme.cn/#/zh-CN/component/layout


> 2、页面字段校验

统一使用Form组件的表单校验功能，通过 rules 属性传入约定的验证规则。

具体实现例子可参见文件"pages/employee/info.vue"

参考官网链接
https://element.eleme.cn/#/zh-CN/component/form 


> 3、页面样式

页面的查询表单、按钮、列表、弹窗的样式直接参考 “pages/employee”文件夹内的list和info文件。

如果有其他页面需要用的页面块，可以单独提取出来作为组件使用。


css样式统一写在“src/assets/index.css”文件底下，命名请规范，如果是其他页面用到的新样式，可以先查阅css里面是否有该样式，不要重复书写样式。

不要在页面上写内嵌样式！


> 4、关于组织树状结构图(待完善)

树状结构图有两个样式：单选和多选，已单独整理出组件，在components/tree文件夹内。

使用方式如下：
```
# 在页面中引入
import singleTree from '../../components/tree/singleTree'

# 注入组件
export default {
    name: 'index',
    components:{
      singleTree
    }
   }

# 在页面使用
<singleTree></singleTree>

#父子组件传值
父子组件的关系可以总结为prop向下传递，事件向上传递。
父组件通过prop给子组件下发数据，子组件通过事件this.$emit()给父组件发送信息


```
具体例子详见pages/employee/list.vue页面中查询表单中的“选择”按钮事例。

该部分涉及到父子组件传值的问题，组件中已经将子组件的传值存储在“checkInfo”属性中，父组件可以通过v-on:checkInfo事件直接取值。

其中：

singleTree的返回值是“ 组织id#组织名称 ”，通过分割#字符可以获取部门名称显示在页面。

multiTree的返回值是“[组织id1，组织id2，...]”，可以按需使用。

子组件请不要随意修改，如果有需要可以联系我修改。

可以参考相关链接进行学习，
https://www.jianshu.com/p/af9cb05bfbaf

> 5、访问服务端接口

统一使用axios调用接口信息。

访问接口URL格式，以/api开头，后接接口路径，例：

```
# /api/combination/getEmployeeList
```

get请求语法：

```
# 无参数 
this.axios.get(url).then((response) => {
    done();//请求成功处理
  }).catch(error=>{
    error();//请求错误处理
  })
  
# 有参数 
this.axios.get(url,{
    params: {
        ID: 12345  //传入的参数
      }
    }).then((response) => {
    done();//请求成功处理
  }).catch(error=>{
    error();//请求错误处理
  })
```

post请求语法：
```
  
# 传入的参数是json参数 
this.axios.post(url,{
      //此参数就是写到请求体中的参数
      ID: 12345,
      NAME: 'aaa'
  }).then((response) => {
    done();//请求成功处理
  }).catch(error=>{
    error();//请求错误处理
  })
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).
