package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.BussinessLine;
import com.ucarinc.orgmanagement.entity.Role;
import com.ucarinc.orgmanagement.entity.form.RoleAccountForm;
import com.ucarinc.orgmanagement.entity.form.RoleResourceForm;
import com.ucarinc.orgmanagement.entity.RoleLine;
import com.ucarinc.orgmanagement.entity.vo.AccountResult;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.entity.vo.Result;

import java.util.List;
import java.util.Map;

public interface IRoleService {

    /**
     * 获取角色数据集合 不分页
     * @param role 角色信息
     * @return 角色链表
     */
    List<Role> getRoleListMes(Role role);

    /**
     * 获取角色数据集合
     * @param roleName 角色名称
     * @param pageNum 当前页面
     * @param pageSize 每页条数
     * @return 当前页数据集合
     */
    PageResult getRoleList(String roleName,Integer pageNum, Integer pageSize);

    /**
     * 根据角色ID获取角色信息
     * @param roleId 角色ID
     * @return 当前页数据集合
     */
    Role getRole(Long roleId);

    /**
     * 删除角色
     * @param roleId 角色ID
     * @return 返回状态值
     */
    int deleteRole(Long roleId);

    /**
     * 新建角色
     * @param JSON 角色相关信息
     * @return 返回状态值
     */
    int addRole(String JSON);

    /**
     * 检测审批人账号是否存在
     * @param  accountLogin 审批人账号
     * @return 返回是否存在
     */
    byte checkApproveAccount(String accountLogin);


    /**
     * 更新角色信息
     * @param updateJSON 角色信息
     * @return 返回业务线
     */
    Result updateRole(String updateJSON);

    /**
     * 获得当前角色数量
     *
     * @return 返回业务线
     */
    Long getRoleCount();

    /**
     * 更新角色信息
     * @param map 角色ID和业务线id
     * @return 返回业务线数量
     */
    int getRoleLineCount(Map map);

    /**
     * 检查角色名称
     * @param roleName 角色名称
     * @return 返回业务线
     */
    Long checkRoleName(String roleName);

    /**
     * 获取所有业务线
     *
     * @return 返回业务线
     */
    List<BussinessLine> getLineList();

    /**
     * 根据角色ID得到业务线
     * @param roleId 角色ID
     * @return 返回业务线
     */
    List<Long> getLineId(Long roleId);

    /**
     * 为角色增加业务线
     * @param map 角色和业务线id信息
     * @return 返回业务线
     */
    int addRoleLine(Map map);

    /**
     * 为角色更新业务线
     * @param map 角色和业务线id信息
     * @return 返回业务线
     */
    int updateRoleLine(Map map);

    /**
     * 删除角色业务线
     * @param roleId 角色id
     * @return 返回业务线
     */
    int deleteRoleLine(Long roleId);

    /**
     * 获取角色的账号集合
     * @param roleAccountForm 角色ID加上账号
     * @return 返回相关角色数量
     */
    PageResult getRoleAccountList(RoleAccountForm roleAccountForm, Integer pageNum, Integer pageSize);

    /**
     * 删除角色账号
     * @param jsonStr  roleId角色ID加账号
     * @return
     */
    Result deleteRoleAccount(String jsonStr);


    /**
     *检查角色的账号集合
     * @param map 角色ID加上账号的实体类
     * @return 返回相关角色数量
     */
    int getRoleAccountCount(Map map);

    /**
     * 改变角色账号状态
     * @param map 角色ID加上账号的实体类
     * @return
     */
    int updateRoleAccount(Map map);

    /**
     * 添加角色账号
     * @param
     * @return
     */
    //int addRoleAccount(String jsonStr);

    Result addRoleAccount(List<AccountResult> accounts,Long roleId);
    /**
     * 查找角色账号
     * @param loginAccount  roleId角色ID加账号
     * @return
     */
    //int getRoleRoleAccounts(String loginAccount, Long roleId);
}
