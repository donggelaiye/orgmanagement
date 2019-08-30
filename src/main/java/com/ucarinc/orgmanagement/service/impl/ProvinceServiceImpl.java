package com.ucarinc.orgmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.ucarinc.orgmanagement.dao.ProvinceMapper;
import com.ucarinc.orgmanagement.entity.Company;
import com.ucarinc.orgmanagement.entity.Province;
import com.ucarinc.orgmanagement.entity.example.ProvinceExample;
import com.ucarinc.orgmanagement.entity.form.AreaForm;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.service.IProvinceSerive;
import com.ucarinc.orgmanagement.utils.ComUtil;
import com.ucarinc.orgmanagement.utils.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: orgmanagement
 * @description: 省份模块服务类
 * @author: Wu.tengfei
 * @create: Create in 13:42 2019/8/1
 **/
@Service
public class ProvinceServiceImpl implements IProvinceSerive {


    @Autowired
    private ProvinceMapper provinceMapper;

    private Logger log = LoggerFactory.getLogger(ProvinceServiceImpl.class);
    /**
     * @author wtf
     * @description  根据查询条件返回的省份链表
     * @date 2019/8/8 10:29 
     * @param pageNum 当前页数
     * @param pageSize 每页总数
     * @param areaForm 区域模块查询条件
     * @return com.ucarinc.orgmanagement.entity.vo.PageResult 返回分页后的省份链表和总数
    */
    @Override
    public PageResult getProvinceList(Integer pageNum, Integer pageSize, AreaForm areaForm){

        ProvinceExample provinceExample = new ProvinceExample();
        ProvinceExample.Criteria criteria = provinceExample.createCriteria();

        if (StringUtil.isNotEmpty(areaForm.getInterCode())){
            criteria.andInterCodeLike("%" + areaForm.getInterCode()+"%");
        }
        if (StringUtil.isNotEmpty(areaForm.getProvinceName())){
            criteria.andProvinceNameLike("%" + areaForm.getProvinceName()+"%");
        }
        if (null != areaForm.getStatus()){
            criteria.andStatusEqualTo(areaForm.getStatus());
        }
        criteria.andIsDeleteEqualTo(Byte.valueOf("0"));

        Page page = null;
        if (pageNum == 0 && pageSize == 0){
            page = PageHelper.startPage(1,-1);
        }else {
            page = PageHelper.startPage(pageNum, pageSize);
        }
        List<Province> provinces = provinceMapper.selectByExample(provinceExample);
        for (Province province : provinces){
            this.improveComInfo(province);
        }
        PageResult pageResult = PageUtil.getPageResult(page);
        log.info("PageResult："+pageResult);
        if(null != pageResult.getPageData()){
            return pageResult;
        }
        return null;
    }

    /**
     * @author wtf
     * @description  根据国际代码删除指定省份
     * @date 2019/8/8 10:29 
     * @param interCode 国际代码
     * @return boolean 成功为true，失败为false
    */
    @Override
    public boolean deleteProvinceById(String interCode) {

        ProvinceExample provinceExample = new ProvinceExample();
        ProvinceExample.Criteria criteria = provinceExample.createCriteria();

        if (StringUtil.isNotEmpty(interCode)){
            criteria.andInterCodeEqualTo(interCode);
        }
        List<Province> list = provinceMapper.selectByExample(provinceExample);
        Province province = list.get(0);
        province.setIsDelete(Byte.valueOf("1"));

        if (provinceMapper.updateByExample(province,provinceExample)>=1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Province> getProvinceList(AreaForm areaForm) {
        ProvinceExample provinceExample = new ProvinceExample();
        ProvinceExample.Criteria criteria = provinceExample.createCriteria();

        if (StringUtil.isNotEmpty(areaForm.getInterCode())){
            criteria.andInterCodeLike(areaForm.getInterCode()+"%");
        }
        if (StringUtil.isNotEmpty(areaForm.getProvinceName())){
            criteria.andProvinceNameLike(areaForm.getProvinceName()+"%");
        }
        if (null != areaForm.getStatus()){
            criteria.andStatusEqualTo(areaForm.getStatus());
        }
        criteria.andIsDeleteEqualTo(Byte.valueOf("0"));
        return provinceMapper.selectByExample(provinceExample);
    }

    public void  improveComInfo(Province province) {
        if (null != province) {
            //获取修改员工姓名
            if (null != province.getModifyEmp()) {
                Long empId = province.getModifyEmp();
                province.setModifyEmpName(ComUtil.getEmpStr(empId));
            }
        }
    }
}
