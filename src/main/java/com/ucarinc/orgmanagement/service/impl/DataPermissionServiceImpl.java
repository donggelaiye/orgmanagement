package com.ucarinc.orgmanagement.service.impl;

import com.ucarinc.orgmanagement.common.Constant;
import com.ucarinc.orgmanagement.dao.AccountMapper;
import com.ucarinc.orgmanagement.dao.DeptPermissionMapper;
import com.ucarinc.orgmanagement.dao.EmployeeMapper;
import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.entity.vo.EmployeeResult;
import com.ucarinc.orgmanagement.entity.vo.TreeResult;
import com.ucarinc.orgmanagement.service.IDataPermissionService;
import com.ucarinc.orgmanagement.service.IDepartmentService;
import com.ucarinc.orgmanagement.utils.ShiroUtil;
import com.ucarinc.orgmanagement.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * description:数据权限service接口实现类
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-17 9:21
 */
@Service
public class DataPermissionServiceImpl implements IDataPermissionService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private DeptPermissionMapper deptPermissionMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<String> getActiveAccountDataPermissionDeptIdList() {
        String activeLoginAccount = ShiroUtil.getActiceLoginAccount();
        Account activeAccount = accountMapper.selectByLoginAccount(activeLoginAccount);
        return getAccountDataPermissionDeptIdList(activeAccount);
    }

    @Override
    public List<String> getAccountDataPermissionDeptIdList(Account account) {
            String loginAccount = account.getAccountLogin();
            switch (account.getDataPermissionId()) {
                case Constant.DATA_PERMISSION_ALL:
                    //数据权限为所有,不对部门做限制
                    return null;
                case Constant.DATA_PERMISSION_RECURSION:
                    //递归获取子部门ID
                    return getRecursionDeptIdList(loginAccount);
                case Constant.DATA_PERMISSION_CURRENT_DEPT:
                    //直接获取当前部门
                    List<String> deptIdList = new ArrayList<>();
                    deptIdList.add(getEmployeeDeptIdByLoginAccount(loginAccount));
                    return deptIdList;
                case Constant.DATA_PERMISSION_PERSON:
                    return null;
                case Constant.DATA_PERMISSION_MANUAL:
                    //手动选择,表中取
                    return deptPermissionMapper.selectDeptIdListByLoginAccount(loginAccount);
                default:
                    return null;
            }
    }

    /**
     * description:获取账号关联员工的部门编号
     *
     * @param loginAccount 登录账号
     * @return java.lang.String  部门id
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/17 9:59
     */
    private String getEmployeeDeptIdByLoginAccount(String loginAccount) {
        EmployeeResult employee = employeeMapper.getEmployeeByAccount(loginAccount);
        return employee.getEmpDepart();
    }

    /**
     * description:获取账号对应员工递归数据权限部门
     *
     * @param loginAccount 登录账号
     * @return java.util.List<java.lang.String>
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/17 10:45
     */
    private List<String> getRecursionDeptIdList(String loginAccount) {
        //当前账号关联员工部门id
        String currentDeptId = getEmployeeDeptIdByLoginAccount(loginAccount);
        //部门树
        TreeResult deptTree = departmentService.selectDepartment(false);
        List<TreeResult> deptTreeList = new ArrayList<>();
        deptTreeList.add(deptTree);
        //树索引Map
        Map<String, String> treeIndexMap = TreeUtil.getChildParentIdIndexMap(deptTreeList);
        //自身部门索引列表
        List<String> selfDeptIndexList = TreeUtil.getTreeNodeIndexList(treeIndexMap, currentDeptId, new ArrayList<>());
        //将索引list反序,变为由顶自底
        Collections.reverse(selfDeptIndexList);
        TreeResult selfDeptNode = getTreeNodeByIndexList(deptTreeList, selfDeptIndexList);
        //获取自身及下级部门Id列表
        return TreeUtil.recursionDownGetNodeIdListByTreeNode(selfDeptNode,new ArrayList<>());
    }

    /**
     * description:依照节点索引获取目标部门节点
     *
     * @param tree 权限树
     * @param empDeptIndexList 目标结点索引
     * @return com.ucarinc.orgmanagement.entity.vo.TreeResult
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/17 12:59
     */
    private TreeResult getTreeNodeByIndexList(List<TreeResult> tree, List<String> empDeptIndexList) {
        return TreeUtil.getTargetNodeByIndex(tree, empDeptIndexList);
    }

}
