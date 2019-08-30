import {constantMenus} from '@/constant/menu'

const SET_MENU = 'SET_MENU' //获取菜单权限
const SET_BUTTON = 'SET_BUTTON' //获取菜单权限

/**
 * 递归过滤菜单，返回用户菜单
 * @param routes
 */
function recursionMenu(routes) {
  let newMenus = JSON.parse(JSON.stringify(constantMenus));
  newMenus.forEach(function(menu){
    if (routes.indexOf(menu.menu)>-1) {
      menu.hidden = false;
    }
    if (menu.subMenus && menu.subMenus.length) {
      menu.subMenus.forEach(function (v) {
        if (routes.indexOf(v.menu)>-1) {
          v.hidden = false;
        }
        if (v.subMenus && v.subMenus.length) {
          v.subMenus.forEach(function (vv) {
            if (routes.indexOf(vv.menu)>-1) {
              vv.hidden = false;
            }
          });
        }
      });

    }
  });
  return newMenus;
}

/**
 * 组装按钮权限
 * @param buttons
 */
function filterAsyncButton(buttons) {
  let dynamicButton = {};
  buttons.forEach(button => {
    let list = button.split(":");
    if (list[0] in dynamicButton){

    }else {
      dynamicButton[list[0]] = new Array();
    }
    dynamicButton[list[0]].push(list[1]);
  })
  return dynamicButton;
}


const menuList = {
  state: {
    newMenuList: [], //本用户所有菜单
    buttonList:{}
  },
  mutations: {
    [SET_MENU](state, menuList) {
      sessionStorage.removeItem('menuList');
      sessionStorage.setItem("menuList",JSON.stringify(menuList));
      state.newMenuList = menuList;
    },
    [SET_BUTTON](state, buttons) {
      sessionStorage.removeItem('buttonList');
      sessionStorage.setItem("buttonList",JSON.stringify(buttons));
      state.buttonList = buttons;
    }
  },
  actions: {
    GenerateMenus({commit},menus) {
      //声明 该角色可用的路由
      let accessedMenus = recursionMenu(menus);
      commit('SET_MENU', accessedMenus);
    },
    GenerateButton({commit},buttons){
      let dynamicButton = filterAsyncButton(buttons);
      commit('SET_BUTTON', dynamicButton);
    }
  }
}

export default menuList
