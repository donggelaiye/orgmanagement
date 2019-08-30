package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.Company;
import com.ucarinc.orgmanagement.entity.form.CompanyForm;
import com.ucarinc.orgmanagement.entity.vo.CompanyResult;
import com.ucarinc.orgmanagement.entity.vo.PageResult;

import java.util.List;

/**
 * @program: orgmanagement
 * @description:
 * @author: Wu.tengfei
 * @create: Create in 11:19 2019/8/5
 **/
public interface ICompanyService {
    public PageResult getCompanyByDeptId(String deptId,Integer pageNum,Integer pageSize);
    public PageResult getCompanyNotInDepId(String deptId,Integer pageNum,Integer pageSize);
    public PageResult getCompanyList(CompanyForm companyForm,Integer pageNum,Integer pageSize);
    public CompanyResult getCompanyByCoCreditCode(String coCreditCode);
    public boolean deleteCompanyByCoCreditCode(String coCreditCode);
    public boolean addCompanyByExample(Company company, List<String> fileData, List<String> fileName);
    public boolean updateCompanyByCoCreditCode(Company company, List<String> fileData, List<String> fileName, List<String> fileOldName, List<String> fileOldUrl);
    public List<Company> getCompanyList(CompanyForm companyForm);
}
