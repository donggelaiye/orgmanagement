package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.entity.form.AccountForm;
import com.ucarinc.orgmanagement.entity.form.AccountModifyForm;
import com.ucarinc.orgmanagement.entity.form.AccountSearchForm;
import com.ucarinc.orgmanagement.entity.form.AccountSelectForm;
import com.ucarinc.orgmanagement.entity.vo.AccountResult;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.utils.PageUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description:账号管理Service
 * @author 高若瑶
 */
public interface IAccountService {


    /**
     * 新建账号
     */
    int addAccount(AccountModifyForm account);
    /**
     * description:修改账号
     *
     * @param account<Account>
     * @return int
     */
//    int modifyAccount(Account account);

    @Transactional
    void modifyAccount(AccountModifyForm account);

    /**
     * description:删除账号
     *
     * @param loginAccount<String>
     * @return int
     * @date 2019/8/5 9:49
     */
    int deleteAccount(String loginAccount);

    /**
     * description:冻结账号
     *
     * @param loginAccount<String>
     * @return int
     * @date 2019/8/5 9:49
     */
    int freezeAccount(String loginAccount);

    /**
     * description:解冻账号
     *
     * @param loginAccount<String>
     * @return int
     * @date 2019/8/5 9:50
     */
    int unfreezeAccount(String loginAccount);


    /**
     * description:重置密码
     *
     * @param loginAccount<String>
     * @return void
     */
    void resetAccountPassword(String loginAccount);

    /**
     * description:拉取查询列表
     *
     * @param accountSearchForm<AccountSearchForm>
     * @return PageResult
     */
    PageResult getAccountList(Integer pageNum, Integer pageSize, AccountSearchForm accountSearchForm);

    String getAccountPassword(String loginAccount);

    /**
     * description：判断是否账号唯一
     */
    int IsLoginOnly (String loginAccount);

    AccountSelectForm getAccountSelectByLogin(String loginAccount);

    AccountResult getAccount(String loginAccount);

    //更新历史记录时用于建account（删除、冻结、解冻）
    Account getAccountByLogin(String loginAccount);

    boolean updatePassword(String accountLogin,String accountPassword);

    List<AccountResult> exportAccountList(AccountSearchForm accountSearchForm);

    /**
        * description:<获得有效的账号列表>
        * @author 赖家美（jiamei.lai@ucarinc.com）
        * @date 2019/8/21 11:18
        * @params [accountForm]
        * @return java.util.List<com.ucarinc.orgmanagement.entity.vo.AccountResult> <返回值说明>
    */
    PageResult getValidAccountList(Integer pageNum, Integer pageSize, AccountSearchForm accountSearchForm);
}
