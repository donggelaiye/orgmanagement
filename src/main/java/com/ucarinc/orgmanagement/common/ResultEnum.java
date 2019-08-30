package com.ucarinc.orgmanagement.common;

/**
 * description: 结果类型枚举类
 * @author 作者 （邮箱）
 * @date 2019-07-29 16:45:00
 * @version 1.0
 */
public enum ResultEnum {

    /**
    * description:无指定权限,拒绝访问
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/12 15:32
    */
    FORBIDDEN(403,"无权访问,当前账号权限或许已被修改,请尝试重新登录!"),

    ACCEPT(202,"请求已接受,但未处理!"),
    /**
     * 系统级异常
     */
    UNKNOWN_ERROR(500,"稍后重试"),
    /**
     * 请求成功类型
     */
    SUCCESS(200,"请求成功"),
    /**
     * 请求失败类型
     */
    ERROR(501,"请求失败"),

    /**
     *  员工插入错误，已存在员工ID
     */
    ERROR_INSERT_EMP(601,"添加失败，该员工ID已被使用，请更换员工ID"),

    /**
     *  员工操作错误，已不存在该员工
     */
    ERROR_NULL_EMP(602,"操作失败，该员工已经不存在"),

    /**
     *  员工离职失败
     */
    ERROR_IS_DIMISSION(603,"操作失败，该员工已经处于离职状态"),

    /**
     *  员工恢复离职失败
     */
    ERROR_IS_RECOVERY(604,"操作失败，该员工已经处于在职状态"),

    /**
     * 账号冻结错误，该账号已经被冻结
     */
    Error_FREEZE_ACC(603,"操作失败，该账号已经被冻结"),
    /**
     * 账号解冻错误，该账号已经被解冻
     */
    Error_UNFREEZE_ACC(603,"操作失败，该账号已经被解冻"),

    /**
     * 账号解冻错误，该账号已经被解冻
     */
    Error_DELETE_ACC(604,"操作失败，该账号已经被删除"),

    /**
     * 角色的相关信息发生了改变
     */
    ERROR_INVALID_ROLE(701,"该角色发生改变,请重新选择"),

    /**
     * 角色申请的相关信息发生了改变
     */
    ERROR_INVALID_ROLEAPPLY(703,"该角色申请发生改变,请重新操作");

    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
