package com.ucarinc.orgmanagement.utils;

import com.ucarinc.orgmanagement.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * description: 通用工具类
 * @author jinbin.chen （jinbin.chen@ucarinc.com）
 * @date 2019-08-13 16:03:00
 * @version 1.0
 */
@Component
public class ComUtil {

    private static EmployeeMapper employeeMapperUtil;

    @Autowired
    public void setEmployeeMapper(EmployeeMapper employeeMapper){
        ComUtil.employeeMapperUtil = employeeMapper;
    }

    /**
     * 通过员工ID获取新建人修改人字符串
     * @param id 员工ID
     * @return 账号（员工姓名）字符串
     */
    public static String getEmpStr(Long id){
        String empId = employeeMapperUtil.getEmpIdById(id);
        String empStr = "";
        if(null != empId){
            empStr = "(" + employeeMapperUtil.getEmpNameByEmpId(empId) + ")";

            //处理有无账号情况字符串拼接
            String accountLogin = employeeMapperUtil.getAccountLoginByEmpId(empId);
            if(null != accountLogin){
                empStr = accountLogin+ " " + empStr;
            }else{
                empStr = "无关联账号 " + empStr;
            }
        }
        return empStr;
    }

    /**
     * 通过账号获取关联员工编号
     * @param accountLogin 账号
     * @return 员工ID
     */
    public static Long getEmpIdByAccount(String accountLogin){
        return employeeMapperUtil.getEmpIdByAccount(accountLogin);
    }

}
