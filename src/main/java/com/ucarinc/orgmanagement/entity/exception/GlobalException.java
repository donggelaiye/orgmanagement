package com.ucarinc.orgmanagement.entity.exception;

/**
 * description: 自定义通用异常类
 * @author 作者 （邮箱）
 * @date yyyy-MM-dd HH:mm:ss
 * @version 1.0
 */
public class GlobalException extends RuntimeException{
    private int code;

    public GlobalException(int code, String message){
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
