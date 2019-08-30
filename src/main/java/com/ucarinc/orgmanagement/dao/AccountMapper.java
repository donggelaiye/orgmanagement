package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.Account;


import com.ucarinc.orgmanagement.entity.GlobalEntity;
import com.ucarinc.orgmanagement.entity.form.AccountForm;
import com.ucarinc.orgmanagement.entity.form.AccountSearchForm;
import com.ucarinc.orgmanagement.entity.form.AccountSelectForm;
import com.ucarinc.orgmanagement.entity.vo.AccountResult;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description: 账号dao接口类
 * @author 赖家美 （2477637880@qq.com）
 * @date 2019-08-08 9:31:00
 * @version 1.0
 */
@Repository(value = "AccountMapper")
public interface AccountMapper {
    /**
     * description:依照登录账号,逻辑删除
     *
     * @param loginAccount<String>
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/5 9:59
     */
    int logicalDeleteByLoginAccount(String loginAccount);

    int deleteByPrimaryKey(Long id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Long id);

    /**
     * description:依照登录账号查找账号表数据
     *
     * @param loginAccount<String>
     * @return com.ucarinc.orgmanagement.entity.Account
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/5 9:32
     */
    Account selectByLoginAccount(String loginAccount);

    int selectByLoginOnly(String loginAccount);

    int updateByPrimaryKeySelective(Account record);

    int updateByLoginAccount(Account record);

    /**
     * 获取账号数据集合
     * @return
     */
    List<AccountResult> getAccountList();

    int updateByPrimaryKey(Account record);

    int updateByIsDeleted(String loginAccount);

    int updateByUnfreeze(String loginAccount);

    int updateByFreeze(String loginAccount);

    /**
     * 获取账号数据集合
     * @return
     */
    List<AccountResult> getAccountList(AccountSearchForm accountSearchForm);

    String getAccountPassword(String loginAccount);

    AccountResult getAccount(String loginAccount);

    //更新历史记录时用于建account（删除、冻结、解冻）
    Account getAccountByLogin(String loginAccount);

    //查询所有员工id用于判断员工编号和账号唯一
     List<Long> getEmployeeListOnly();

    int updateAccountPassword(@Param("accountLogin") String accountLogin, @Param("accountPassword") String accountPassword);

    AccountSelectForm getAccountSelectByLogin(String loginAccount);

    int updateModifyEmp(Account account);
    
    /**
        * description:<获得有效的账号列表>
        * @author 赖家美（jiamei.lai@ucarinc.com）
        * @date 2019/8/21 11:13
        * @params [accountForm]
        * @return java.util.List<com.ucarinc.orgmanagement.entity.vo.AccountResult> <返回值说明>
    */
        List<AccountResult> getValidAccountList(AccountSearchForm accountSearchForm);

    //判断是否之前有冻结
    int selectFreeze(String loginAccount);

    //判断是否之前有解冻
    int selectUnfreeze(String loginAccount);

    //判断是否之前有删除
    int selectDelete(String loginAccount);

}
