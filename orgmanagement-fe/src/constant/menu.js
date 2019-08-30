 export const constantMenus= [
  {
    // 目录或具体路由的标题
    title: '部门管理',
    // icon根据element-ui的icon官方文档选择
    icon: 'el-icon-setting',
    // path以_开头则表示这是一个目录path并不是路由的路径
    path: 'department',
    // subMenus 代表子级菜 单, 其中元素的字段名除icon外与父级相同
    hidden: true, //代表不会在菜单中显示
    menu: 'department',
    subMenus: [
      {
        title: '部门列表',
        path: '/department/list',
        hidden: true ,//代表不会在菜单中显示
        menu: 'departmentList'
      }
    ]
  },
  {
    title: '员工管理',
    icon: 'el-icon-setting',
    path: 'employee',
    menu: 'employee',
    hidden: true, //代表不会在菜单中显示
    subMenus: [
      {
        title: '员工列表',
        path: '/employee/list',
        hidden: true ,//代表不会在菜单中显示
        menu: 'employeeList',
      }
    ]
  },
  {
    title: '账号管理',
    icon: 'el-icon-setting',
    path: 'account',
    menu: 'account',
    hidden: true,
    subMenus: [
      {
        title: '账号列表',
        path: '/account/list',
        hidden: true,
        menu: 'accountList'
      },
    ]
  },
  {
    title: '角色',
    icon: 'el-icon-setting',
    path: 'role',
    menu: 'role',
    hidden: true,
    subMenus: [
      {
        title: '角色管理',
        path: '/role/management',
        hidden: true,
        menu: 'roleList',
      },
      {
        title: '角色申请',
        path: '/role/apply',
        hidden: true,
        menu: 'roleApplyList',

      },
      {
        title: '角色申请审批',
        path: '/role/approve',
        hidden: true,
        menu: 'roleApproveList',
      },
      {
        title: '角色权限管理',
        path: '/role/management/permission',
        hidden: true,
        menu: 'rolePermission',
      }
    ]
  },
  {
    title: '公司管理',
    icon: 'el-icon-setting',
    path: 'company',
    menu: 'company',
    hidden: true,
    subMenus: [
      {
        title: '公司列表',
        path: '/company/list',
        hidden: true,
        menu: 'companyList'
      }
    ]
  },
  {
    title: '查询',
    icon: 'el-icon-setting',
    path: 'search',
    menu: 'search',
    hidden: true,
    subMenus: [
      {
        title: '部门查询',
        hidden: true,
        menu: 'searchDept',
        path: '/search/department/list'
      },
      {
        title: '角色查询',
        path: '/search/role',
        hidden: true,
        menu: 'searchRole',
        subMenus: [
          {
            title: '角色账号明细查询',
            path: '/account_detail',
            hidden: true,
            menu: 'searchAccountDetail',
          },
          {
            title: '角色功能权限明细查询',
            path: '/permission_detail',
            hidden: true,
            menu: 'searchPermissionDetail',
          }
        ]
      },
      {
        title: '账号功能权限明细查询',
        path: '/search/accountPermission',
        hidden: true,
        menu: 'searchAccountPermission'
      }

    ]
  },
  {
    title: '行政区划管理',
    icon: 'el-icon-setting',
    path: 'area',
    menu: 'area',
    hidden: true,
    subMenus: [
      {
        title: '省/市',
        path: '/province/list',
        hidden: true,
        menu: 'areaProvince'
      },
      {
        title: '城市',
        path: '/city/list',
        hidden: true,
        menu: 'areaCity'
      },
      {
        title: '区/县',
        path: '/county/list',
        hidden: true,
        menu: 'areaCounty'
      }
    ]
  },
  {
    title: '个人账号管理',
    icon: 'el-icon-setting',
    path: 'empAccount',
    menu: 'empAccount',
    hidden: true,
    subMenus: [
      {
        title: '密码重置',
        path: '/personal/pwdUpdate',
        hidden: true,
        menu: 'empAccountPassReset'
      },
      {
        title: '基本信息查看',
        path: '/personal/empDetail',
        hidden: true,
        menu: 'empAccountBaseInfo'
      }
    ]
  },
  // {
  //   title: '公共规则',
  //   icon: 'el-icon-setting',
  //   path: 'rule',
  //   hidden: true,
  //   subMenus: [
  //     {
  //       title: '员工选择',
  //       path: '/rule/selEmp',
  //       hidden: true,
  //     },
  //     {
  //       title: '账号选择',
  //       path: '/rule/selAcc',
  //       hidden: true,
  //     },
  //     {
  //       title: '角色选择',
  //       path: '/rule/selRole',
  //       hidden: true,
  //     },
  //     {
  //       title: '账号添加',
  //       path: '/rule/addAcc',
  //       hidden: true,
  //     }
  //   ]
  // }
]
