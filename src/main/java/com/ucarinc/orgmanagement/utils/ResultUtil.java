package com.ucarinc.orgmanagement.utils;

import com.ucarinc.orgmanagement.common.ResultEnum;
import com.ucarinc.orgmanagement.entity.vo.Result;

/**
 * description: 生成Result的工具类
 * @author 作者 （邮箱）
 * @date yyyy-MM-dd HH:mm:ss
 * @version 1.0
 */
public class ResultUtil {

    /**
     * 生成错误类型的Result
     *
     * @param code 状态码
     * @param msg 消息
     * @return
     */
    public static Result error(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    /**
    * description:返回带code,msg,data的success
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/13 15:45
    * @param code, msg, obj
    * @return  com.ucarinc.orgmanagement.entity.vo.Result
    */

    public static Result success(int code, String msg,Object obj) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(obj);
        return result;
    }

    /**
     * 生成带数据的成功Result
     *
     * @param obj 数据
     * @return
     */
    public static Result success(Object obj) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(obj);
        return result;
    }

    /**
     * 生成不带数据的成功Result
     *
     * @return
     */
    public static Result success() {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(null);
        return result;
    }

}
