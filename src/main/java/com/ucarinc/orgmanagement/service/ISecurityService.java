package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.vo.Result;
import org.apache.shiro.subject.Subject;

import java.util.Map;

/**
 * description:
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-07 10:46
 */
public interface ISecurityService {

    /**
     * description:用户登录方法
     *
     * @param requestBody Map<String,object> @requestBody 转换的请求参数
     * @return com.ucarinc.orgmanagement.entity.vo.Result 包含用户菜单,用户权限,SessionId
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/8 13:56
     */
    Result login(Map<String, Object> requestBody);

    /**
    * description:判定session池中指定Id的session是否存活
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/20 8:57
    * @param sessionId sessionId
    * @return  Result
    */
    Result isSessionAlive(String sessionId);

    /**
     * description:杀死session池中指定Id的session
     * @author dihan.chen(dihan.chen@ucarinc.com)
     * @date 2019/8/20 8:57
     * @param sessionId sessionId
     * @return  Result
     */
    Result killSessionBySessionId(String sessionId);

    /**
     * description:获取当前登录用户的菜单,权限信息
     *
     * @param currentSubject Subject shiro管理的当前登录用户的subject对象
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/8 13:59
     */
    Map<String, Object> getActiveAccountMenuPermissionInfo(Subject currentSubject);


}
