package com.ucarinc.orgmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.ucarinc.orgmanagement.common.ResultEnum;
import com.ucarinc.orgmanagement.dao.EmployeeMapper;
import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.entity.Employee;
import com.ucarinc.orgmanagement.entity.exception.GlobalException;
import com.ucarinc.orgmanagement.entity.form.EmpSearchForm;
import com.ucarinc.orgmanagement.entity.vo.EmployeeResult;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.service.IEmployeeService;
import com.ucarinc.orgmanagement.service.IHistoryService;
import com.ucarinc.orgmanagement.utils.ComUtil;
import com.ucarinc.orgmanagement.utils.PageUtil;
import com.ucarinc.orgmanagement.utils.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: 员工服务实现类
 * @author jinbin.chen （jinbin.chen@ucarinc.com）
 * @date 2019-08-02 10:21:00
 * @version 1.0
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private IHistoryService historyService;

    @Override
    public EmployeeResult getEmployeeById(String empId) {
        EmployeeResult employee = employeeMapper.getEmployeeById(empId);
        if(null != employee){
            this.improveEmpInfo(employee);
        }
        return employee;
    }

    @Override
    public int insertEmployee(Employee employee) {
        Long empId = ShiroUtil.getActiveEmpId();
        employee.setCreateEmp(empId);
        employee.setModifyEmp(empId);
        // 插入数据前判断是否存在
        if(0 != employeeMapper.getEmployeeCountById(employee.getEmpId())){
            throw new GlobalException(ResultEnum.ERROR_INSERT_EMP.getCode(),ResultEnum.ERROR_INSERT_EMP.getMsg());
        }
        return employeeMapper.insertEmployee(employee);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteEmployeeById(String empId) {
        // 删除数据前判断是否存在
        if(0 == employeeMapper.getCountById(empId)){
            throw new GlobalException(ResultEnum.ERROR_NULL_EMP.getCode(),ResultEnum.ERROR_NULL_EMP.getMsg());
        }

        employeeMapper.deleteEmployeeById(empId);
        employeeMapper.deleteAccountByEmpId(empId);

        //删除员工后，添加账号历史记录
        Account account = employeeMapper.getAccountByEmpId(empId);
        if(null == account){
            account = new Account();
            account.setEmpId(empId);
        }
        historyService.addAccHistory(account,9);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateEmployeeById(Employee employee) {
        // 修改数据前判断是否存在
        if(0 == employeeMapper.getCountById(employee.getOldEmpId())){
            throw new GlobalException(ResultEnum.ERROR_NULL_EMP.getCode(),ResultEnum.ERROR_NULL_EMP.getMsg());
        }

        employee.setModifyEmp(ShiroUtil.getActiveEmpId());
        employeeMapper.updateEmployeeById(employee);

        // 修改员工后，判断是否修改对应账号密保邮箱
        if(null != employee.getEmpEmail()){
            employeeMapper.updateAccountEmail(employee.getEmpId(), employee.getEmpEmail());
        }

        String newEmpId = employee.getEmpId();
        String oldEmpId = employee.getOldEmpId();
        String newEmpName = employee.getEmpName();
        String oldEmpName = employee.getOldEmpName();
        String newDeptName = employee.getEmpName();
        String oldDeptName = employee.getOldEmpDeptName();

        if(!newEmpId.equals(oldEmpId)){
            Map<String,String> map = new HashMap<String,String>();
            map.put("newEmpId",newEmpId);
            map.put("oldEmpId",oldEmpId);

            employeeMapper.updateAccountEmpId(map);
            employeeMapper.updateDeptEmpId(map);
            employeeMapper.updateOperateEmpId(map);
            employeeMapper.updateRoleEmpId(map);
            employeeMapper.updateRoleApplyEmpId(map);
            employeeMapper.updateRoleAccountEmpId(map);
        }

        if(!newEmpName.equals(oldEmpName)){
            Map map = new HashMap(3);
            map.put("newEmpId",newEmpId);
            map.put("newEmpName",newEmpName);

            employeeMapper.updateDeptEmpName(map);
            employeeMapper.updateRoleEmpName(map);
            employeeMapper.updateRoleApplyEmpName(map);
            employeeMapper.updateRoleAccountEmpName(map);
        }

        if(!newDeptName.equals(oldDeptName)){
            Map map = new HashMap(3);
            map.put("newDeptName",newDeptName);
            map.put("newEmpId",newEmpId);

            employeeMapper.updateRoleApplyDeptName(map);
            employeeMapper.updateRoleAccountDeptName(map);
        }

        return 1;
    }

    @Override
    public PageResult getEmployeeList(Integer pageNum, Integer pageSize, EmpSearchForm empSearchForm) {
        //使用 PageHelper插件进行分页
        Page page = PageHelper.startPage(pageNum, pageSize);
        employeeMapper.getEmployeeList(empSearchForm);
        for(Object object : page){
            EmployeeResult employee = (EmployeeResult) object;
        }
        return PageUtil.getPageResult(page);
    }

    @Override
    public int checkEmployeeId(String empId) {
        return employeeMapper.getEmployeeCountById(empId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateDimission(String empId) {
        // 修改数据前判断是否存在
        if(0 == employeeMapper.getCountById(empId)){
            throw new GlobalException(ResultEnum.ERROR_NULL_EMP.getCode(),ResultEnum.ERROR_NULL_EMP.getMsg());
        }

        // 修改数据前判断是否已离职
        if(1 == employeeMapper.getDimissionByEmpId(empId)){
            throw new GlobalException(ResultEnum.ERROR_IS_DIMISSION.getCode(),ResultEnum.ERROR_IS_DIMISSION.getMsg());
        }

        employeeMapper.updateDimission(empId);
        employeeMapper.deleteAccountByEmpId(empId);

        // 离职员工成功后，添加账号历史记录
        Account account = employeeMapper.getAccountByEmpId(empId);
        if(null == account){
            account = new Account();
            account.setEmpId(empId);
        }
        historyService.addAccHistory(account,10);

        return 1;
    }

    @Override
    public int updateReDimission(String empId) {
        // 修改数据前判断是否存在
        if(0 == employeeMapper.getCountById(empId)){
            throw new GlobalException(ResultEnum.ERROR_NULL_EMP.getCode(),ResultEnum.ERROR_NULL_EMP.getMsg());
        }

        // 修改数据前判断是否已恢复
        if(0 == employeeMapper.getDimissionByEmpId(empId)){
            throw new GlobalException(ResultEnum.ERROR_IS_RECOVERY.getCode(),ResultEnum.ERROR_IS_RECOVERY.getMsg());
        }

        employeeMapper.updateReDimission(empId);
        employeeMapper.updateAccountUnBund(empId);

        return 1;
    }

    @Override
    public int updateEmployeeDept(String empId, String empDepart) {
        // 修改数据前判断是否存在
        if(0 == employeeMapper.getCountById(empId)){
            throw new GlobalException(ResultEnum.ERROR_NULL_EMP.getCode(),ResultEnum.ERROR_NULL_EMP.getMsg());
        }

        return employeeMapper.updateEmployeeDept(empId, empDepart);
    }

    @Override
    public EmployeeResult getEmployeeByAccount(String accountLogin) {
        if(null == accountLogin || "".equals(accountLogin)){
            return null;
        }
        EmployeeResult employeeResult = employeeMapper.getEmployeeByAccount(accountLogin);
        if(null != employeeResult){
            this.improveEmpInfo(employeeResult);
        }
        return employeeResult;
    }

    /**
     *  获取无关联账号员工数据
     * @param pageNum 当前页面
     * @param pageSize 每页条数
     * @param empSearchForm 搜索条件表单
     * @return 员工数据集
     */
    @Override
    public PageResult getEmployeeListOnly(Integer pageNum, Integer pageSize, EmpSearchForm empSearchForm){
        Page page = PageHelper.startPage(pageNum, pageSize);
        employeeMapper.getEmployeeListOnly(empSearchForm);
        return PageUtil.getPageResult(page);
    }

    @Override
    public List<EmployeeResult> getEmployeeListAll(EmpSearchForm empSearchForm) {
        List<EmployeeResult> employeeList = employeeMapper.getEmployeeList(empSearchForm);
        for(Object object : employeeList){
            EmployeeResult employee = (EmployeeResult) object;
            this.improveEmpInfo(employee);
        }
        return employeeList;
    }

    /**
     * 对员工信息进行完善
     *
     * @param employee 员工对象
     */
    public void  improveEmpInfo(EmployeeResult employee){
        if(null != employee){
            //获取创建员工姓名
            if(null != employee.getCreateEmp()){
                Long empId = employee.getCreateEmp();
                employee.setCreateEmpName(ComUtil.getEmpStr(empId));
            }

            //获取修改员工姓名
            if(null != employee.getModifyEmp()){
                Long empId = employee.getModifyEmp();
                employee.setModifyEmpName(ComUtil.getEmpStr(empId));
            }
        }
    }


}
