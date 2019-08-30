package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.Province;
import com.ucarinc.orgmanagement.entity.form.AreaForm;
import com.ucarinc.orgmanagement.entity.vo.PageResult;

import java.util.List;

/**
 * @program: orgmanagement
 * @description:
 * @author: Wu.tengfei
 * @create: Create in 13:41 2019/8/1
 **/
public interface IProvinceSerive {
    /**
     * 获取省份数据集合
     * @param pageNum 当前页面
     * @param pageSize 每页条数
     * @return 当前页数据集合
     */
    public PageResult getProvinceList(Integer pageNum, Integer pageSize, AreaForm areaForm);
    public boolean deleteProvinceById(String interCode);
    public List<Province> getProvinceList(AreaForm areaForm);
}
