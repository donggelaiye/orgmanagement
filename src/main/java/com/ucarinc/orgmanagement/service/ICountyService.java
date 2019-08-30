package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.County;
import com.ucarinc.orgmanagement.entity.form.AreaForm;
import com.ucarinc.orgmanagement.entity.vo.CityListResult;
import com.ucarinc.orgmanagement.entity.vo.PageResult;

import java.util.List;

/**
 * @program: orgmanagement
 * @description:
 * @author: Wu.tengfei
 * @create: Create in 13:41 2019/8/1
 **/
public interface ICountyService {
    public PageResult getCountyList(Integer pageNum, Integer pageSize, AreaForm areaForm);
    public boolean deleteCountyById(String interCode);

    public List<CityListResult> getCountyListResult();
    public List<County> getCountyList();
    public List<County> getCountyList(AreaForm areaForm);
}
