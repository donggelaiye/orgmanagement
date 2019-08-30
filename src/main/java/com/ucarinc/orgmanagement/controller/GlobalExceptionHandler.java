package com.ucarinc.orgmanagement.controller;

import com.ucarinc.orgmanagement.common.ResultEnum;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.entity.exception.GlobalException;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * description: 通用异常处理器(监测被@RequestMapping修饰的方法)
 * @author 作者 （邮箱）
 * @date yyyy-MM-dd HH:mm:ss
 * @version 1.0
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
    * description:处理shiro抛出的UnauthorizedException异常(未授权异常)
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/12 15:17
    * @return  com.ucarinc.orgmanagement.entity.vo.Result
    */

    @ExceptionHandler(UnauthorizedException.class)
    public Result UnauthorizedExceptionHandler(HttpServletRequest request, UnauthorizedException e){
            logger.error("未授权访问被拒绝:\n 异常原因:{}\n 异常位置:{} ",e.getMessage(),e.getStackTrace());
            return ResultUtil.error(ResultEnum.FORBIDDEN.getCode(),ResultEnum.FORBIDDEN.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public Result defaultExceptionHandler(HttpServletRequest request, Exception e){
        if(e instanceof GlobalException) {
            logger.warn("出现自定义异常:\n 异常原因:{}\n 异常位置:{} ",e.getMessage(),e.getStackTrace());
            GlobalException globalException = (GlobalException) e;
            return ResultUtil.error(globalException.getCode(),globalException.getMessage());
        }else{
            logger.error("出现系统异常:\n 异常原因:{}\n 异常位置:{} ",e.getMessage(),e.getStackTrace());
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());
        }
    }

}
