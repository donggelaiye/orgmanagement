package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.AccountRole;
import com.ucarinc.orgmanagement.entity.Role;
import com.ucarinc.orgmanagement.entity.RoleApply;
import com.ucarinc.orgmanagement.entity.form.ApproveAccountForm;
import com.ucarinc.orgmanagement.entity.form.RoleAccountForm;
import com.ucarinc.orgmanagement.entity.form.RoleResourceForm;
import com.ucarinc.orgmanagement.entity.vo.AccountResult;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.entity.vo.Result;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository(value = "RoleMapper")
public interface RoleMapper {

    int insert(Role record);

    /**
     * 导出获取的角色列表
     * @param role 角色名称
     * @return 返回角色列表
     */
    List<Role> getRoleListMes(Role role);

    List<Role> selectAllRoleIdAndName();

    /**
     * 获取角色列表数据
     * @param roleName 角色名称
     * @return 返回角色列表
     */
    List<Role> getRoleList(String roleName);

    /**
     * 根据角色ID查询角色数据
     * @param roleId 角色ID
     * @return 返回角色列表
     */
    Role selectByRoleId(Long roleId);

    // 这个好像没用到
//    Role getRole(Long roleId);

    /**
     * 删除角色
     * @param roleId 角色ID
     * @return
     */
    int deleteRole(Long roleId);

    /**
     * 增加角色
     * @param record 角色信息
     * @return
     */
    int addRole(Role record);

    /**
     * 更新角色
     * @param role 角色信息
     * @return
     */
    int updateRole(Role role);

    /**
     * 得到角色数量
     * @param
     * @return 返回总角色数量
     */
    int getRoleCount();

    /**
     * 检测角色名称是否存在
     * @param roleName 角色名称
     * @return 返回相关角色数量
     */
    int getRoleNameCount(String roleName);

    /**
     * 检测审批人账号是否存在
     * @param  accountLogin
     * @return 返回是否存在
     */
    byte checkApproveAccount(String accountLogin);

    /**
     * 检测审批人账号是否变更
     * @param  accountLogin
     * @return
     */
    ApproveAccountForm checkApproveAccountChange(String accountLogin);

    /**
     * 获取角色的账号集合
     * @param roleAccountForm 角色ID加上账号的实体类
     * @return 返回相关角色数量
     */
    List<AccountResult> getRoleAccountList(RoleAccountForm roleAccountForm);

    /**
     *删除角色的账号
     * @param map 角色ID加上账号的实体类
     * @return 返回相关角色数量
     */
    int deleteRoleAccount(Map map);

    /**
     *检查角色的账号
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
     * @param map 角色ID加上账号的实体类
     * @return
     */
    int addRoleAccount(Map map);

    /**
     * 检查角色状态
     * @param roleId 角色ID
     * @return
     */
    Byte checkRoleStatus(Long roleId);

    /**
     * 获取角色列表数据
     * @param roleName 角色名称
     * @return 返回角色列表
     */
    List<Role> getValidRoleList(String roleName);
}
