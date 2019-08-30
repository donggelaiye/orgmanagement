package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.RoleApply;
import com.ucarinc.orgmanagement.entity.form.RoleAccountForm;
import com.ucarinc.orgmanagement.entity.form.RoleApplySearchForm;
import com.ucarinc.orgmanagement.entity.vo.AccountResult;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.entity.vo.RoleInfoResult;

import java.util.List;

/**
 * description: 文件说明
 * @author 作者 （邮箱）
 * @date yyyy-MM-dd HH:mm:ss
 * @version 1.0
 */
public interface IRoleApplyService {


    /**
     * 获取角色申请数据集合
     * @param pageNum 当前页面
     * @param pageSize 每页条数
     * @return 当前页数据集合
     */
    PageResult getRoleApplyList(Integer pageNum, Integer pageSize);

    /**
     * 根据查询条件返回角色申请数据集合
     * @param pageNum 当前所在页面
     * @param pageSize 页面条数
     * @param roleApplyForm 查询表单
     * @return
     */
    PageResult getRoleApplyList(Integer pageNum, Integer pageSize, RoleApplySearchForm roleApplyForm);
    /**
     * 根据角色ID返回角色详情信息
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/7 9:49
     * @param roleId 角色id
     * @return: 包括角色的信息和业务线的信息,如果该角色无效则返回null
     */
    public RoleInfoResult getRoleInfoById(Long roleId);

    /**
     * 添加角色申请
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/9 17:07
     * @param newPageInfoJSON jason；包含RoleApply的信息，以及账号信息数组
     * @return:
     */
    Result addApply(String newPageInfoJSON);

    /**
     * 根据角色申请编号查询详情信息
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/9 17:04
     * @param applyCode 角色申请编号
     * @return: json 包括roleApply，RoleApplyAccountForm数组（包含RoleApplyAccount账号的信息）
     */
    String getRoleApplyInfoByApplyCode(String applyCode);

    /**
     * 更新角色申请详情表
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/10 11:26
     * @param newPageInfoJSON 包含roleApply的基本信息，还有RoleApplyAccountForm的数组
     * @return:
     */
    Result updateApply(String newPageInfoJSON);


    int deleteAccount(String applyCode, String account);

    /**
     * 生成角色申请编号
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/11 10:58
     * @return: 角色申请编号
     */
    String generateApplyCode();

    /**
     * 根据查询条件获取导出的数据
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/12 8:29
     * @param roleApplyForm 查询条件
     * @return:
     */
    List<RoleApply> getExportData(RoleApplySearchForm roleApplyForm);

    /**
     * 批量删除角色申请数据
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/12 16:31
     * @param deleteItems 要删除的多个数据
     * @return: 数据库返回结果
     */
    int deleteRoleApplys(String deleteItems);
    /**
     * 批量提交审核
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/12 17:24
     * @param approveItems
     * @return:
     */
    int approveRoleApplys(String approveItems);


    /**
     * 获取角色数据集合
     * @param roleName 角色名称
     * @param pageNum 当前页面
     * @param pageSize 每页条数
     * @return 当前页数据集合
     */
    PageResult getValidRoleList(String roleName,Integer pageNum, Integer pageSize);

    /**
     * 通过role对象获取业务线等角色相关信息
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/20 16:48
     * @param roleJSON role的全部信息
     * @return: 
     */
    Result getRoleInfoByRole(String roleJSON);

    /**
     * 根据传入的账号，获取当前数据库中的账号信息，如果账号相同则返回账号信息，
     * 如果不同则返回状态码702及相应的msg。
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/21 9:24
     * @param accounts 传入的账号
     * @return:
     */
    Result getAccountsInfo(List<AccountResult> accounts);
}
