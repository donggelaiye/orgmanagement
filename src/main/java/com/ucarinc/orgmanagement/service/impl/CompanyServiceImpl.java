package com.ucarinc.orgmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.ucarinc.orgmanagement.dao.CompanyMapper;
import com.ucarinc.orgmanagement.entity.Company;
import com.ucarinc.orgmanagement.entity.example.CompanyExample;
import com.ucarinc.orgmanagement.entity.form.CompanyForm;
import com.ucarinc.orgmanagement.entity.vo.CompanyResult;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.service.ICompanyService;
import com.ucarinc.orgmanagement.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.util.*;

/**
 * @program: orgmanagement
 * @description: 公司模块服务类
 * @author: Wu.tengfei
 * @create: Create in 11:21 2019/8/5
 **/
@Service
public class CompanyServiceImpl implements ICompanyService {


    @Autowired
    private CompanyMapper companyMapper;
    private Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);
    private PicUtil picUtil = new PicUtil();
    /**
     * @author wtf 
     * @date 2019/8/8 10:17 
     * @description  返回公司表中和当前部门id相关联的公司
     * @param deptId
     * @param pageNum
     * @param pageSize
     * @return com.ucarinc.orgmanagement.entity.vo.PageResult
    */
    @Override
    public PageResult getCompanyByDeptId(String deptId,Integer pageNum,Integer pageSize) {
        if(StringUtil.isNotEmpty(deptId)){
            Page page = PageHelper.startPage(pageNum, pageSize);
            companyMapper.selectByDeptId(deptId);
            PageResult pageResult = PageUtil.getPageResult(page);
            log.info("PageResult："+pageResult);
            return pageResult;
        }
        return null;
    }

    /**
     * @author wtf 
     * @date 2019/8/8 10:16 
     * @description 返回公司表中和当前部门id无关的公司
     * @param deptId 部门id
     * @param pageNum 当前页码 
     * @param pageSize 每页总数
     * @return com.ucarinc.orgmanagement.entity.vo.PageResult
    */
    @Override
    public PageResult getCompanyNotInDepId(String deptId, Integer pageNum, Integer pageSize) {
        if(StringUtil.isNotEmpty(deptId)){
            Page page = PageHelper.startPage(pageNum, pageSize);
            companyMapper.selectNotInDeptId(deptId);
            PageResult pageResult = PageUtil.getPageResult(page);
            log.info("PageResult："+pageResult);
            return pageResult;
        }
        return null;
    }
        
    /**
     * @author wtf 
     * @date 2019/8/8 10:15 
     * @description  根据公司查询条件返回分页后的公司数据  
     * @param companyForm 公司查询条件表单
     * @param pageNum 当前页码
     * @param pageSize 每页总数
     * @return com.ucarinc.orgmanagement.entity.vo.PageResult
    */
    @Override
    public PageResult getCompanyList(CompanyForm companyForm, Integer pageNum, Integer pageSize) {
        CompanyExample companyExample = new CompanyExample();
        CompanyExample.Criteria criteria = companyExample.createCriteria();

        if (StringUtil.isNotEmpty(companyForm.getCoCreditCode())){
            criteria.andCoCreditCodeLike("%" + companyForm.getCoCreditCode() + "%");
        }
        if (StringUtil.isNotEmpty(companyForm.getCoName())){
            criteria.andCoNameLike("%" + companyForm.getCoName() + "%");
        }
        if (null != companyForm.getCoNature()){
            criteria.andCoNatureEqualTo(companyForm.getCoNature());
        }
        if (null != companyForm.getCoType()){
            criteria.andCoTypeEqualTo(companyForm.getCoType());
        }
        if (null != companyForm.getStatus()){
            criteria.andStatusEqualTo(companyForm.getStatus());
        }
        if (null != companyForm.getStartTime()){
            criteria.andModifyTimeGreaterThan(companyForm.getStartTime());
        }
        if (null != companyForm.getEndTime()){
            criteria.andModifyTimeLessThan(companyForm.getEndTime());
        }
        criteria.andIsDeleteEqualTo(Byte.valueOf("0"));
        companyExample.setOrderByClause("id desc");
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Company> companies = companyMapper.selectByExample(companyExample);
        for (Company company:companies){
            this.improveComInfo(company);
        }
        PageResult pageResult = PageUtil.getPageResult(page);
        System.out.println(pageResult.getPageData());
        return pageResult;
    }

    /**
     * @author wtf
     * @description 根据统一社会信用代码查询指定公司
     * @date 2019/8/8 10:20 
     * @param coCreditCode 统一社会信用代码
     * @return com.ucarinc.orgmanagement.entity.Company
    */
    @Override
    public CompanyResult getCompanyByCoCreditCode(String coCreditCode) {
        CompanyExample companyExample = new CompanyExample();
        CompanyExample.Criteria criteria = companyExample.createCriteria();
        List<Map<String,String>> fileList = new ArrayList<>();
        CompanyResult companyResult = new CompanyResult();
        if (StringUtil.isNotEmpty(coCreditCode)){
            criteria.andCoCreditCodeEqualTo(coCreditCode);
        }
        List<Company> list = companyMapper.selectByExample(companyExample);
        if (list.size() > 0){
            Company company = list.get(0);
            if (StringUtil.isNotEmpty(list.get(0).getBusinessLicense())){
                List<String> picUrls = Arrays.asList(list.get(0).getBusinessLicense().split(","));
                for (String url:picUrls){
                    List<String> urlList = Arrays.asList(url.split("/"));
                    String picName = urlList.get(urlList.size()-1);
                    Map<String,String> map = new HashMap<>();
                    map.put("name",picName);
                    map.put("url",url);
                    fileList.add(map);
                }
                System.out.println(fileList);
                companyResult.setFileList(fileList);
            }
            company.setModifyEmpName(ComUtil.getEmpStr(company.getModifyEmp()));
            company.setCreateEmpName(ComUtil.getEmpStr(company.getCreateEmp()));
            companyResult.setCompany(company);
            System.out.println(company);
            return companyResult;
        } else {
            return null;
        }
    }

    /**
     * @author wtf
     * @description  根据统一社会信用代码删除指定公司
     * @date 2019/8/8 10:21
     * @param coCreditCode 统一社会信用代码
     * @return boolean 成功为true，失败为false
    */
    @Override
    public boolean deleteCompanyByCoCreditCode(String coCreditCode) {
        CompanyExample companyExample = new CompanyExample();
        CompanyExample.Criteria criteria = companyExample.createCriteria();

        if (StringUtil.isNotEmpty(coCreditCode)){
            criteria.andCoCreditCodeEqualTo(coCreditCode);
        }

        List<Company> list = companyMapper.selectByExample(companyExample);
        Company company = list.get(0);
        company.setIsDelete(Byte.valueOf("1"));

        if(companyMapper.updateByExample(company,companyExample)>=1){
            return true;
        }else {
            return false;
        }
    }

    /**
     * @author wtf
     * @description  新增公司
     * @date 2019/8/8 10:24
     * @param company 公司bean
     * @param fileData base64文件链表
     * @param fileName 图片名称
     * @return boolean 成功为true，失败为false
    */
    @Override
    public boolean addCompanyByExample(Company company, List<String> fileData, List<String> fileName) {
        CompanyExample companyExample = new CompanyExample();
        CompanyExample.Criteria criteria = companyExample.createCriteria();

        if (StringUtil.isNotEmpty(company.getCoCreditCode())){
            criteria.andCoCreditCodeEqualTo(company.getCoCreditCode());
        }
        List<Company> list = companyMapper.selectByExample(companyExample);
        if (list.size() > 0){
            return false;
        }
        if (StringUtil.isNotEmpty(company.getCoName())){
            criteria.andCoNameEqualTo(company.getCoName());
        }
        if (null != company.getCreateTime()){
            try {
                company.setCreateTime(TimeUtil.toDate(company.getCreateTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (null != company.getModifyTime()){
            try {
                company.setModifyTime(TimeUtil.toDate(company.getModifyTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<String> picUrl = picUtil.toPic(fileData,fileName,company.getCoCreditCode());
        if (null != picUrl && picUrl.size() > 0){
            String str = new String();
            for (String string:picUrl){
                str += string + ",";
            }
            str = str.substring(0,str.length()-1);
            company.setBusinessLicense(str);
        }
        company.setModifyEmp(ShiroUtil.getActiveEmpId());
        company.setCreateEmp(ShiroUtil.getActiveEmpId());
        company.setModifyTime(new Date());
        company.setCreateTime(new Date());
        log.info("company:"+company);
        if (companyMapper.insert(company) >= 1){
            return true;
        }else {
            return false;
        }
    }

    /**
     * @author wtf
     * @description  更新公司信息
     * @date 2019/8/8 10:25
     * @param company 需要更新的公司
     * @param fileData 需要更新的base64文件链表，如果为null则默认不更新，不为null则默认覆盖原来的文件
     * @param fileName 需要更新的图片名字，如果为null则默认不更新，不为null则默认覆盖原来的文件
     * @return boolean 成功为true，失败为false
    */
    @Override
    public boolean updateCompanyByCoCreditCode(Company company,List<String> fileData, List<String> fileName, List<String> fileOldName, List<String> fileOldUrl) {
        CompanyExample companyExample = new CompanyExample();
        CompanyExample.Criteria criteria = companyExample.createCriteria();
        if (StringUtil.isNotEmpty(company.getCoCreditCode())){
            criteria.andCoCreditCodeEqualTo(company.getCoCreditCode());
        }
        List<Company> list = companyMapper.selectByExample(companyExample);
        Company company1 = list.get(0);
        String str = "";
        List<String> picUrl = picUtil.toPic(fileData,fileName,company.getCoCreditCode());
        if (null != picUrl && picUrl.size() > 0){
            for (String string:picUrl){
                str += string + ",";
            }
        }
        if (fileOldUrl.size() > 0){
            for (String oldUrl:fileOldUrl){
                str +=  oldUrl+ ",";
            }
            str = str.substring(0,str.length()-1);
        }
        if (StringUtil.isEmpty(str)){
            company.setBusinessLicense("");
        }else {
            List<String> lists = Arrays.asList(str.split(","));
            System.out.println(lists);
            HashSet hashSet = new HashSet(lists);
            lists = new ArrayList<>();
            lists.addAll(hashSet);
            str = "";
            for (String url:lists){
                str += url + ",";
            }
            str = str.substring(0,str.length()-1);
            company.setBusinessLicense(str);
        }
        company.setModifyTime(new Date());
        log.info("company:"+company);
        if (companyMapper.updateByExample(company,companyExample) > 0){
            return true;
        } else {
            return false;
        }
    }

    /***
     * @author wtf
     * @description  通过查询条件获取公司链表（不分页）
     * @date 2019/8/17 15:38
     * @param companyForm 查询条件
     * @return java.util.List<com.ucarinc.orgmanagement.entity.Company>
    */
    @Override
    public List<Company> getCompanyList(CompanyForm companyForm) {
        CompanyExample companyExample = new CompanyExample();
        CompanyExample.Criteria criteria = companyExample.createCriteria();
        if (StringUtil.isNotEmpty(companyForm.getCoCreditCode())){
            criteria.andCoCreditCodeLike("%" + companyForm.getCoCreditCode() + "%");
        }
        if (StringUtil.isNotEmpty(companyForm.getCoName())){
            criteria.andCoNameLike("%" + companyForm.getCoName() + "%");
        }
        if (null != companyForm.getCoNature()){
            criteria.andCoNatureEqualTo(companyForm.getCoNature());
        }
        if (null != companyForm.getCoType()){
            criteria.andCoTypeEqualTo(companyForm.getCoType());
        }
        if (null != companyForm.getStatus()){
            criteria.andStatusEqualTo(companyForm.getStatus());
        }
        if (null != companyForm.getStartTime()){
            criteria.andModifyTimeGreaterThan(companyForm.getStartTime());
        }
        if (null != companyForm.getEndTime()){
            criteria.andModifyTimeLessThan(companyForm.getEndTime());
        }
        criteria.andIsDeleteEqualTo(Byte.valueOf("0"));
        List<Company> companies = companyMapper.selectByExample(companyExample);
        for (Company company:companies){
            this.improveComInfo(company);
        }
        return companies;
    }

    /***
     * @author wtf
     * @description  修改人命名修改
     * @date 2019/8/17 15:37
     * @param company 需要修改的bean
     * @return void
    */
    public void  improveComInfo(Company company) {
        if (null != company) {
            //获取修改员工姓名
            if (null != company.getModifyEmp()) {
                Long empId = company.getModifyEmp();
                company.setModifyEmpName(ComUtil.getEmpStr(empId));
            }
        }
    }
}
