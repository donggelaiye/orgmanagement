package com.ucarinc.orgmanagement.service;

import com.github.pagehelper.Page;
import com.ucarinc.orgmanagement.entity.City;
import com.ucarinc.orgmanagement.entity.form.AreaForm;
import com.ucarinc.orgmanagement.entity.vo.CityListResult;
import com.ucarinc.orgmanagement.entity.vo.PageResult;

import java.util.List;

/**
 * @program: orgmanagement
 * @description:
 * @author: Wu.tengfei
 * @create: Create in 13:40 2019/8/1
 **/
public interface ICitySerivce {
    public PageResult getCityList(Integer pageNum, Integer pageSize, AreaForm areaForm);
    public boolean deleteCityById(String interCode);
    public List<City> getCityList();
    public List<CityListResult> getCityListResult();
    public List<City> getCityList(AreaForm areaForm);
}
