package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.entity.History;
import com.ucarinc.orgmanagement.entity.LoginHistory;
import com.ucarinc.orgmanagement.entity.vo.PageResult;

/**
 * description:历史记录Service
 */
public interface IHistoryService {
    /**
     * description:拉取查询列表
     *
     * @param pageNum<Integer>
     * @return PageResult
     */
    PageResult selectAllHistory(Integer pageNum, Integer pageSize);

    int addAccHistory(Account account,int operate);

    boolean insertFirstTimeLoginInfo(String loginAccount,String createEmpId);

    LoginHistory getLoginHistoryById(String loginAccount);
}
