package com.ucarinc.orgmanagement.entity.vo;/**
 * @ProjectName: orgmanagement
 * @Package: com.ucarinc.orgmanagement.entity.vo
 * @ClassName: CompanyResult
 * @Author: wtf
 * @Description:
 * @Date: 2019/8/14 14:41
 * @Version: 1.0
 */

import com.ucarinc.orgmanagement.entity.Company;

import java.util.List;
import java.util.Map;

/**
 * @author wtf
 * @date 2019/8/14 14:41
 */
public class CompanyResult{
    private Company company;
    private List<Map<String,String>> fileList;

    @Override
    public String toString() {
        return "CompanyResult{" +
                "company=" + company +
                ", fileList=" + fileList +
                '}';
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Map<String, String>> getFileList() {
        return fileList;
    }

    public void setFileList(List<Map<String, String>> fileList) {
        this.fileList = fileList;
    }
}
