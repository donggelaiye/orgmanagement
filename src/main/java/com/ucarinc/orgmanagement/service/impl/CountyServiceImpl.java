package com.ucarinc.orgmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.ucarinc.orgmanagement.dao.CountyMapper;
import com.ucarinc.orgmanagement.entity.County;
import com.ucarinc.orgmanagement.entity.Province;
import com.ucarinc.orgmanagement.entity.example.CountyExample;
import com.ucarinc.orgmanagement.entity.form.AreaForm;
import com.ucarinc.orgmanagement.entity.vo.CityListResult;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.service.ICountyService;
import com.ucarinc.orgmanagement.utils.ComUtil;
import com.ucarinc.orgmanagement.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: orgmanagement
 * @description: 区/县模块服务类
 * @author: Wu.tengfei
 * @create: Create in 13:42 2019/8/1
 **/
@Slf4j
@Service
public class CountyServiceImpl implements ICountyService {

    @Autowired
    private CountyMapper countyMapper;

    private Logger logger = LoggerFactory.getLogger(CountyServiceImpl.class);

    /**
     * @author wtf
     * @description  根据查询条件获取区/县链表
     * @date 2019/8/8 10:29
     * @param pageNum 当前页码
     * @param pageSize 每页总数
     * @param areaForm 区域模块查询条件
     * @return com.ucarinc.orgmanagement.entity.vo.PageResult 分页后的区/县链表及总数
    */
    @Override
    public PageResult getCountyList(Integer pageNum, Integer pageSize, AreaForm areaForm) {
        CountyExample countyExample = new CountyExample();
        CountyExample.Criteria criteria = countyExample.createCriteria();

        if (StringUtil.isNotEmpty(areaForm.getInterCode())){
            criteria.andInterCodeLike("%"+areaForm.getInterCode()+"%");
        }
        if (StringUtil.isNotEmpty(areaForm.getProvinceName())){
            criteria.andProvinceNameLike("%"+areaForm.getProvinceName()+"%");
        }
        if (StringUtil.isNotEmpty(areaForm.getCityName())){
            criteria.andCityNameLike("%"+areaForm.getCityName()+"%");
        }
        if (StringUtil.isNotEmpty(areaForm.getCountyName())){
            criteria.andCountyNameLike("%"+areaForm.getCountyName()+"%");
        }
        if (null != areaForm.getStatus()){
            criteria.andStatusEqualTo(areaForm.getStatus());
        }
        criteria.andIsDeleteEqualTo(Byte.valueOf("0"));
        Page page = null;
        if (pageNum == 0 && pageSize == 0){
            page = PageHelper.startPage(1, -1);
        }else {
            page = PageHelper.startPage(pageNum, pageSize);
        }
        List<County> counties = countyMapper.selectByExample(countyExample);
        for (County county :counties){
            this.improveComInfo(county);
        }
        PageResult pageResult = PageUtil.getPageResult(page);
        logger.info("PageResult："+pageResult);
        if(null != pageResult.getPageData()){
            return pageResult;
        }
        return null;
    }

    /**
     * @author wtf
     * @description  根据国际代码删除指定区/县
     * @date 2019/8/8 10:29
     * @param interCode 国际代码
     * @return boolean 成功为true，失败为false
    */
    @Override
    public boolean deleteCountyById(String interCode) {
        CountyExample countyExample = new CountyExample();
        CountyExample.Criteria criteria = countyExample.createCriteria();

        if (null != interCode){
            criteria.andInterCodeEqualTo(interCode);
        }
        List<County> list = countyMapper.selectByExample(countyExample);
        County county = list.get(0);
        county.setIsDelete(Byte.valueOf("1"));

        if (countyMapper.updateByExample(county,countyExample)>=1){
            return true;
        }else {
            return false;
        }
    }

    /**
     * @author wtf
     * @description  获取所有的区/县信息
     * @date 2019/8/8 10:29
     * @param
     * @return java.util.List<com.ucarinc.orgmanagement.entity.County>
    */
    @Override
    public List<County> getCountyList() {
        CountyExample countyExample = new CountyExample();
        CountyExample.Criteria criteria = countyExample.createCriteria();
        List<County> list = countyMapper.selectByExample(countyExample);
        if(null != list){
            return list;
        }
        return null;
    }

    @Override
    public List<County> getCountyList(AreaForm areaForm) {
        CountyExample countyExample = new CountyExample();
        CountyExample.Criteria criteria = countyExample.createCriteria();

        if (StringUtil.isNotEmpty(areaForm.getInterCode())){
            criteria.andInterCodeLike("%"+areaForm.getInterCode()+"%");
        }
        if (StringUtil.isNotEmpty(areaForm.getProvinceName())){
            criteria.andProvinceNameLike("%"+areaForm.getProvinceName()+"%");
        }
        if (StringUtil.isNotEmpty(areaForm.getCityName())){
            criteria.andCityNameLike("%"+areaForm.getCityName()+"%");
        }
        if (StringUtil.isNotEmpty(areaForm.getCountyName())){
            criteria.andCountyNameLike("%"+areaForm.getCountyName()+"%");
        }
        if (null != areaForm.getStatus()){
            criteria.andStatusEqualTo(areaForm.getStatus());
        }
        criteria.andIsDeleteEqualTo(Byte.valueOf("0"));
        return countyMapper.selectByExample(countyExample);
    }

    /**
     * @author daijianzhao
     * @description  获取所有的区/县信息
     * @date 2019/8/8 10:29
     * @param  （城市）信息列表
     */
    @Override
    public List<CityListResult> getCountyListResult() {
        List<County> countyList = countyMapper.getCountyList();
        List<CityListResult> list=new ArrayList<>();
        CityListResult cityResult=null;
        for (County county : countyList) {
            cityResult=new CityListResult();
            cityResult.setValue(county.getInterCode());
            cityResult.setName(county.getCountyName());
            cityResult.setType(2);
            list.add(cityResult);
        }
        return list;
    }

    public void  improveComInfo(County county) {
        if (null != county) {
            //获取修改员工姓名
            if (null != county.getModifyEmp()) {
                Long empId = county.getModifyEmp();
                county.setModifyEmpName(ComUtil.getEmpStr(empId));
            }
        }
    }

}
