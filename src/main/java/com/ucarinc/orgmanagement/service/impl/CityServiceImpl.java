package com.ucarinc.orgmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.ucarinc.orgmanagement.controller.EmployeeController;
import com.ucarinc.orgmanagement.dao.CityMapper;
import com.ucarinc.orgmanagement.entity.City;
import com.ucarinc.orgmanagement.entity.example.CityExample;
import com.ucarinc.orgmanagement.entity.form.AreaForm;
import com.ucarinc.orgmanagement.entity.vo.CityListResult;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.service.ICitySerivce;
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
 * @description: 城市模块服务类
 * @author: Wu.tengfei
 * @create: Create in 13:41 2019/8/1
 **/
@Service
public class CityServiceImpl implements ICitySerivce {


    @Autowired
    private CityMapper cityMapper;
    private Logger log = LoggerFactory.getLogger(CityServiceImpl.class);
    /**
     * @author wtf
     * @description  根据查询条件返回城市链表
     * @date 2019/8/8 10:29 
     * @param pageNum 当前页数
     * @param pageSize 每页总数
     * @param areaForm 区域模块查询条件
     * @return com.ucarinc.orgmanagement.entity.vo.PageResult 分页后的城市链表及总数
    */
    @Override
    public PageResult getCityList(Integer pageNum, Integer pageSize, AreaForm areaForm) {
        CityExample cityExample = new CityExample();
        CityExample.Criteria criteria = cityExample.createCriteria();

        if (StringUtil.isNotEmpty(areaForm.getInterCode())){
            criteria.andInterCodeLike("%"+areaForm.getInterCode()+"%");
        }
        if (StringUtil.isNotEmpty(areaForm.getProvinceName())){
            criteria.andProvinceNameLike("%"+areaForm.getProvinceName()+"%");
        }
        if (StringUtil.isNotEmpty(areaForm.getCityName())){
            criteria.andCityNameLike("%"+areaForm.getCityName()+"%");
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
        List<City> cities = cityMapper.selectByExample(cityExample);
        for (City city : cities){
            this.improveComInfo(city);
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
     * @description  根据国际代码删除指定城市信息
     * @date 2019/8/8 10:29 
     * @param interCode 国际代码
     * @return boolean 成功为true，失败为false
    */
    @Override
    public boolean deleteCityById(String interCode) {

        CityExample cityExample = new CityExample();
        CityExample.Criteria criteria = cityExample.createCriteria();

        if (StringUtil.isNotEmpty(interCode)){
            criteria.andInterCodeEqualTo(interCode);
        }

        List<City> list = cityMapper.selectByExample(cityExample);
        City city = list.get(0);
        city.setIsDelete(Byte.valueOf("1"));

        if(cityMapper.updateByExample(city,cityExample)>=1){
            return true;
        }else {
            return false;
        }
    }

    /**
     * @author wtf
     * @description  返回所有城市信息
     * @date 2019/8/8 10:29 
     * @param 
     * @return java.util.List<com.ucarinc.orgmanagement.entity.City>
    */
    @Override
    public List<City> getCityList() {
        CityExample cityExample = new CityExample();
        CityExample.Criteria criteria = cityExample.createCriteria();
        List<City> list = cityMapper.selectByExample(cityExample);
        if (null != list){
            return list;
        }
        return null;
    }

    @Override
    public List<CityListResult> getCityListResult() {
        CityExample cityExample = new CityExample();
        CityExample.Criteria criteria = cityExample.createCriteria();
        List<City> list = cityMapper.selectByExample(cityExample);
        List<CityListResult> cityListResults=new ArrayList<>();
        CityListResult cityResult=null;
        if (null != list){
            for (City city : list) {
                cityResult=new CityListResult();
                cityResult.setValue(city.getInterCode());
                cityResult.setName(city.getCityName());
                cityResult.setType(1);
                cityListResults.add(cityResult);
            }
            return cityListResults;
        }
        return null;

    }

    @Override
    public List<City> getCityList(AreaForm areaForm) {
        CityExample cityExample = new CityExample();
        CityExample.Criteria criteria = cityExample.createCriteria();

        if (StringUtil.isNotEmpty(areaForm.getInterCode())){
            criteria.andInterCodeLike("%"+areaForm.getInterCode()+"%");
        }
        if (StringUtil.isNotEmpty(areaForm.getProvinceName())){
            criteria.andProvinceNameLike("%"+areaForm.getProvinceName()+"%");
        }
        if (StringUtil.isNotEmpty(areaForm.getCityName())){
            criteria.andCityNameLike("%"+areaForm.getCityName()+"%");
        }
        if (null != areaForm.getStatus()){
            criteria.andStatusEqualTo(areaForm.getStatus());
        }
        criteria.andIsDeleteEqualTo(Byte.valueOf("0"));
        return cityMapper.selectByExample(cityExample);
    }
    public void  improveComInfo(City city) {
        if (null != city) {
            //获取修改员工姓名
            if (null != city.getModifyEmp()) {
                Long empId = city.getModifyEmp();
                city.setModifyEmpName(ComUtil.getEmpStr(empId));
            }
        }
    }
}
