package com.ucarinc.orgmanagement.service;

/**
 * description:shiroSession管理service接口
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-16 15:38
 */
public interface IShiroSessionService {

    /**
    * description:遍历session,杀死指定登录账号的session
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/22 15:24
    * @param loginAccount 登录账号
    * @return  void
    */
    void killSessionByLoginAccount(String loginAccount);
}
