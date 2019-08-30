package com.ucarinc.orgmanagement.controller;

import com.ucarinc.orgmanagement.common.ResultEnum;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.service.IDataPermissionService;
import com.ucarinc.orgmanagement.service.ISecurityService;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * description:登录权限相关controller
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-06 9:42
 */
@Controller
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    private ISecurityService securityService;
    @Autowired
    private IDataPermissionService dataPermissionService;

    /**
    * description:用户登录接口
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/23 11:04
    * @param requestBody Map<String,object> @requestBody 转换的请求参数
    * @return  com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping("/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, Object> requestBody) {
        return securityService.login(requestBody);
    }

    /**
    * description:注销登录后转发接口
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/23 11:06
    * @return  com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping("/doneLogout")
    @ResponseBody
    public Result doneLogout() {
        return ResultUtil.success(ResultEnum.SUCCESS.getCode(), "注销登录成功!", null);
    }

    /**
    * description:判定指定Id的session是否存活
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/23 11:07
    * @param sessionId SessionId
    * @return  com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping("/isSessionAlive")
    @ResponseBody
    public Result isSessionAlive(@RequestBody String sessionId) {
        return securityService.isSessionAlive(sessionId);
    }

    /**
     * description:杀死指定Id的session
     * @author dihan.chen(dihan.chen@ucarinc.com)
     * @date 2019/8/23 11:07
     * @param sessionId SessionId
     * @return  com.ucarinc.orgmanagement.entity.vo.Result
     */
    @RequestMapping("/killSession")
    @ResponseBody
    public Result killSession(@RequestBody String sessionId) {
        return securityService.killSessionBySessionId(sessionId);
    }

    /**
    * description:未登录转发接口
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/23 11:08
    * @param request, response
    * @return  com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping("/unauthc")
    @ResponseBody
    public Result unauthc(HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.error(401, "未登录!");
    }

}
