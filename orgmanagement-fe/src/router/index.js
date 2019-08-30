import Vue from 'vue'
import Router from 'vue-router'
import EmployeeList from '@/pages/employee/list'
import EmployeeDetail from '@/pages/employee/detail'
import EmployeeAllocateDepart from '@/pages/employee/allocateDepart'
import ProvinceList from '@/pages/province/list'
import CityList from '@/pages/city/list'
import ResetPwd from '@/pages/personal/resetPwd'
import CountyList from '@/pages/county/list'
import CityDetail from '@/pages/city/detail'
import ProvinceDetail from '@/pages/province/detail'
import CompanyList from '@/pages/company/list'
import CompanyDetail from '@/pages/company/detail'
import CountyDetail from  '@/pages/county/detail'
import PwdUpdate from '@/pages/personal/pwdUpdate'
import EmpDetail from '@/pages/personal/empDetail'
import AccountList from '@/pages/account/list'
import AccountDetail from '@/pages/account/detail'
import AccountHistory from '@/pages/account/history'
import AccountModification from '@/pages/account/modification'
import AccountPermission from '@/pages/account/permission'

import RoleList from '@/pages/role/management/list'
import RoleListDetail from '@/pages/role/management/detail'
import RolePermission from '@/pages/role/management/permission'
import RoleManagementDetail from '@/pages/role/management/roleDetail'
import RoleAddRoleAccount from '@/pages/role/management/addRoleAccount'
import RoleApproveList from '@/pages/role/approve/list'
import RoleApproveDetail from '@/pages/role/approve/detail'
import RoleApplyList from '@/pages/role/apply/list'
import RoleApplyApplyInfo from '@/pages/role/apply/applyInfo'
import RoleApplyDetail from '@/pages/role/apply/detail'
import RoleAccountList from '@/pages/search/roleAccount/list'
import RolePermissionList from '@/pages/search/rolePermission/list'
import AccountPermission1 from '@/pages/search/accountPermission'

import Login from '@/pages/login'
import Index from '@/pages/index'
import P404 from '@/pages/404'

import DepartmentList from '@/pages/department/list'
import DepartmentDetail from '@/pages/department/detail'
import OptionalDeptTree from '@/pages/department/optional_dept_tree'

import SelectEmployee from '@/pages/common/selectEmployee'
import SelectAccount from '@/pages/common/selectAccount'
import SelectRole from '@/pages/common/selectRole'
import AddAccount from '@/pages/common/addAccount'

import associatedCoList from '@/pages/department/company/list'
import addAssociatedCoList from'@/pages/department/company/add_list'
import DeptSearchList from '@/pages/search/department/list'
Vue.use(Router)

//静态路由
export const allRouterMap = [
  {
    path: '/',
    component: Login
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/index',
    name: 'index',
    component: Index,
    meta: {
      requireAuth: true  //进入这个路由都需要登录
    },
    children: [
      {
        path: '/employee/list',
        name: '员工管理',
        component: EmployeeList,
        meta: {
          requireAuth: true  //进入这个路由都需要登录
        }
      },
      {
        path: '/employee/detail',
        name: 'employee_detail',
        component: EmployeeDetail,
        meta: {
          requireAuth: true  //进入这个路由都需要登录
        }
      },
      {
        path: '/employee/allocateDepart',
        name: 'employee_allocateDepart',
        component: EmployeeAllocateDepart,
        meta: {
          requireAuth: true  //进入这个路由都需要登录
        }
      },
      {
        path:'/department/list',
        name:'department_list',
        component:DepartmentList,
        meta: {
          requireAuth: true  //进入这个路由都需要登录
        }
      },
      {
        path:'/department/detail',
        name:'department_detail',
        component:DepartmentDetail,
        meta: {
          requireAuth: true  //进入这个路由都需要登录
        }
      },
      {
        path: '/department/optional_dept_tree',
        name: 'optional_dept_tree',
        component: OptionalDeptTree,
        meta: {
          requireAuth: true  //进入这个路由都需要登录
        }
      },
      {
        path: '/account/permission',
        name: 'account_permission',
        component: AccountPermission,
        meta: {
          requireAuth: true  //进入这个路由都需要登录
        }
      },
      {
        path: '/account/list',
        name: 'account_list',
        component: AccountList,
        meta: {
          requireAuth: true  //进入这个路由都需要登录
        }
      },
      {
        path: '/account/detail',
        name: 'account_detail',
        component: AccountDetail,
        meta: {
          requireAuth: true  //进入这个路由都需要登录
        }
      },
      {
        path:'/account/history',
        name:'account_history',
        component:AccountHistory,
        meta: {
          requireAuth: true  //进入这个路由都需要登录
        }
      },
      {
        path:'/account/modification',
        name:'account_modification',
        component:AccountModification,
        meta: {
          requireAuth: true  //进入这个路由都需要登录
        }
      },
      {
          path: '/role/approve',
          name: 'role_approve_list',
          component: RoleApproveList,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/role/approve/detail',
          name: 'role_approve_detail',
          component: RoleApproveDetail,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        }, {
          path: '/role/apply',
          name: 'role_apply',
          component: RoleApplyList,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/role/apply/detail',
          name: 'role_apply_detail',
          component: RoleApplyDetail,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/role/apply/applyInfo',
          name: 'role_apply_applyInfo',
          component: RoleApplyApplyInfo,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/role/management',
          name: 'role_list',
          component: RoleList,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/role/management/detail',
          name: 'role_detail',
          component: RoleListDetail,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/role/management/roleDetail',
          name: 'role_management_detail',
          component: RoleManagementDetail,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },

        {
          path: '/role/management/permission',
          name: 'role_permission',
          component: RolePermission,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/role/management/addRoleAccount',
          name: 'role_addRoleAccount',
          component: RoleAddRoleAccount,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },

        {
          path: '/account_detail',
          name: 'role_account_list',
          component: RoleAccountList,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/permission_detail',
          name: 'role_permission_list',
          component: RolePermissionList,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
       {
          path:'/search/accountPermission',
          name:'permission_detail',
          component:AccountPermission1,
          mata: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/department/company/list',
          name: 'associated_co_list',
          component: associatedCoList,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/department/company/add_list',
          name: 'add_associated_co_list',
          component: addAssociatedCoList,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/search/department/list',
          name: 'dept_search_list',
          component: DeptSearchList,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path:'/company/list',
          name:'company_list',
          component:CompanyList,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path:'/company/detail',
          name:'company_detail',
          component:CompanyDetail,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/province/list',
          name: 'province_list',
          component: ProvinceList,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/province/detail',
          name: 'province_detail',
          component: ProvinceDetail,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/city/list',
          name: 'city_list',
          component: CityList,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/city/detail',
          name: 'city_detail',
          component: CityDetail,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/county/list',
          name: 'county_list',
          component: CountyList,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path:'/county/detail',
          name:'county_detail',
          component:CountyDetail,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path:'/personal/empDetail',
          name:'personal_empDetail',
          component:EmpDetail,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path:'/personal/pwdUpdate',
          name:'personal_pwdUpdate',
          component:PwdUpdate,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/personal/resetPwd',
          name: 'personal_resetPwd',
          component: ResetPwd
        },
        {
          path: '/rule/selEmp',
          name: 'rule_selEmp',
          component: SelectEmployee,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/rule/selAcc',
          name: 'rule_selAcc',
          component: SelectAccount,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/rule/selRole',
          name: 'rule_selRole',
          component: SelectRole,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        },
        {
          path: '/rule/addAcc',
          name: 'rule_addAcc',
          component: AddAccount,
          meta: {
            requireAuth: true  //进入这个路由都需要登录
          }
        }
      ]
    },
    {
      path: '*',
      name: '404',
      component: P404
    }
]

export default new Router({
  mode: 'history',
  routes: allRouterMap,
  base: '/orgmanagement',
})

