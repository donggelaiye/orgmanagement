import {asyncRouterMap, constantRouterMap, containerRouterMap} from '@/router/routes'
import {constantMenus} from '@/constant/menu'

//该权限部分暂时只控制菜单权限和按钮权限

const SET_ROUTER = 'SET_ROUTER' //获取菜单权限

/**
 * 判断用户是否拥有此菜单
 * @param menus
 * @param route
 */
function hasPermission(menus, route) {
  if (route.menu) {
    /*
    * 如果这个路由有menu属性,就需要判断用户是否拥有此menu权限
    */
    console.log(menus.indexOf(route.menu))
    console.log(route.menu)
    return menus.indexOf(route.menu) > -1;
  } else {
    return false
  }
}

/**
 * 递归过滤异步路由表，返回符合用户菜单权限的路由表
 * @param asyncRouterMap
 * @param menus
 */
function filterAsyncRouter(asyncRouterMap, menus) {
  const accessedRouters = asyncRouterMap.filter(route => {
    //filter,js语法里数组的过滤筛选方法
    if (hasPermission(menus, route)) {
      return true
    }
    return false
  })
  return accessedRouters
}

/**
 * 递归过滤菜单，返回用户菜单
 * @param asyncRouterMap
 * @param menus
 */
function packageMenu(parentMenuList) {
  let newList = new Array();
  parentMenuList.forEach(pMenu => {
    let obj = {"path": pMenu};
    newList.push(obj);
  });
  return newList;
}

const permission = {
  state: {
    routers: constantRouterMap, //本用户所有的路由,包括了固定的路由和下面的addRouters
    addRouters: [] //本用户的角色赋予的新增的动态路由
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers;
      state.routers = constantRouterMap.concat(routers); //将固定路由和新增路由进行合并, 成为本用户最终的全部路由信息
      sessionStorage.setItem("routers", JSON.stringify(routers));
    }
  },
  actions: {
    GenerateRoutes({dispatch,commit}, datas) {
      //生成路由
        let menuList = datas.menuList;
        let parentMenuList = datas.parentMenuList;
        let permissionList = datas.permissionList;
        let newList = menuList.concat(parentMenuList);
        //通过菜单menu匹配对应的菜单权限
        dispatch('GenerateMenus', newList).then(()=>{
          //控制显示按钮
          dispatch('GenerateButton', permissionList);
        });

        //声明 该角色可用的路由
        // let accessedRouters;
        // accessedRouters = filterAsyncRouter(asyncRouterMap, menuList)
        // let MainContainer = containerRouterMap.find(v => v.path === '/index')
        // let children = MainContainer.children
        // children.push(...accessedRouters)
        // // console.log(containerRouterMap);
        // // this.state.addRouters = containerRouterMap;
        // // this.state.routers = constantRouterMap.concat(containerRouterMap);
        // let newList = packageMenu(parentMenuList);
        // newList = children.concat(newList);
        // //控制显示菜单
        // dispatch('GenerateMenus', newList);
        // //设置路由
        // commit("SET_ROUTERS",containerRouterMap);
        // // router.options.routes = constantRouterMap.concat(containerRouterMap);
        // // router.addRoutes(containerRouterMap);
        // //控制显示按钮
        // dispatch('GenerateButton', permissionList);
    },


  }
}

export default permission
