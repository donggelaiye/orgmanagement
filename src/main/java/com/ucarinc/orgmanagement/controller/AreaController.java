package com.ucarinc.orgmanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.ucarinc.orgmanagement.entity.form.AreaForm;
import com.ucarinc.orgmanagement.entity.vo.CityListResult;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.service.impl.CityServiceImpl;
import com.ucarinc.orgmanagement.service.impl.CountyServiceImpl;
import com.ucarinc.orgmanagement.service.impl.ProvinceServiceImpl;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: orgmanagement
 * @description: 行政区划管理控制类
 * @author: Wu.tengfei
 * @create: Create in 11:09 2019/8/1
 **/
@Controller
@RequestMapping(value = "/area")

public class AreaController {


    @Autowired
    private ProvinceServiceImpl provinceServiceImpl;
    @Autowired
    private CityServiceImpl cityServiceImpl;
    @Autowired
    private CountyServiceImpl countyServiceImpl;

    private Logger log = LoggerFactory.getLogger(AreaController.class);

    /**
     * @author wtf
     * @description  根据查询条件获取省份链表
     * @date 2019/8/8 11:03
     * @param request
     * @param pageNum 当前页码
     * @param pageSize 每页总数
     * @param areaForm 区域模块查询条件
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping(value = "/province/getProvinceList")
    @ResponseBody
    @RequiresPermissions("area:provinceList")
    public Result getProvinceList(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,@RequestParam("areaForm") String areaForm){
        AreaForm areaForm1 = JSONObject.parseObject(areaForm, AreaForm.class);
        log.info("province查询条件："+areaForm1);
        if (pageNum == 0 && pageSize == 0){
            return ResultUtil.success(provinceServiceImpl.getProvinceList(areaForm1));
        }else {
            return ResultUtil.success(provinceServiceImpl.getProvinceList(pageNum,pageSize,areaForm1));
        }

    }

    /**
     * @author wtf
     * @description  根据查询条件获取城市链表
     * @date 2019/8/8 11:04
     * @param request
     * @param pageNum 当前页码
     * @param pageSize 每页总数
     * @param areaForm 区域模块查询条件
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping(value = "/city/getCityList")
    @ResponseBody
    @RequiresPermissions("area:cityList")
    public Result getCityList(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,@RequestParam("areaForm") String areaForm){
        AreaForm areaForm1 = JSONObject.parseObject(areaForm, AreaForm.class);
        log.info("city查询条件："+areaForm1);
        if (pageNum == 0 && pageSize == 0){
            return ResultUtil.success(cityServiceImpl.getCityList(areaForm1));
        }else {
            return ResultUtil.success(cityServiceImpl.getCityList(pageNum,pageSize,areaForm1));
        }

    }

    /**
     * @author wtf
     * @description  根据查询条件获取区/县链表
     * @date 2019/8/8 11:04
     * @param request
     * @param pageNum 当前页码
     * @param pageSize 每页总数
     * @param areaForm 区域模块查询条件
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping(value = "/county/getCountyList")
    @ResponseBody
    @RequiresPermissions("area:countyList")
    public Result getCountyList(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,@RequestParam("areaForm") String areaForm){
        AreaForm areaForm1 = JSONObject.parseObject(areaForm, AreaForm.class);
        log.info("county查询条件："+areaForm1);
        if (pageNum == 0 && pageSize == 0){
            return ResultUtil.success(countyServiceImpl.getCountyList(areaForm1));
        }else {
            return ResultUtil.success(countyServiceImpl.getCountyList(pageNum,pageSize,areaForm1));
        }

    }

    /**
     * @author wtf
     * @description  根据国际代码删除指定省份
     * @date 2019/8/8 11:05
     * @param request
     * @param interCode 国际代码
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping(value = "/province/deleteProvinceById")
    @ResponseBody
    @RequiresPermissions("area:deleteProvince")
    public Result deleteProvinceById(HttpServletRequest request,@RequestParam("interCode") String interCode){
        log.info("province删除条件："+interCode);
        if(provinceServiceImpl.deleteProvinceById(interCode)){
            return ResultUtil.success();
        }else {
            return ResultUtil.error(501,"不存在该记录");
        }
    }

    /**
     * @author wtf
     * @description  根据国际代码删除指定城市
     * @date 2019/8/8 11:05
     * @param request
     * @param interCode 国际代码
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping(value = "/county/deleteCountyById")
    @ResponseBody
    @RequiresPermissions("area:deleteCity")
    public Result deleteCountyById(HttpServletRequest request,@RequestParam("interCode") String interCode){
        log.info("county删除条件："+interCode);
        if(countyServiceImpl.deleteCountyById(interCode)){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(501,"不存在该记录");
        }
    }

    /**
     * @author wtf
     * @description  根据国际代码删除指定区/县
     * @date 2019/8/8 11:06
     * @param request
     * @param interCode 国际代码
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping(value = "/city/deleteCityById")
    @ResponseBody
    @RequiresPermissions("area:deleteCounty")
    public Result deleteCityById(HttpServletRequest request,@RequestParam("interCode") String interCode){
        log.info("city删除条件："+interCode);
        if(cityServiceImpl.deleteCityById(interCode)){
            return ResultUtil.success();
        }else {
            return ResultUtil.error(501,"不存在该记录");
        }
    }

    @RequestMapping(value = "/city/getCityListToDept")
    @ResponseBody
    @RequiresPermissions("area:cityList")
    public Result getCityListToDept(){
        log.info("==获取城市列表方法==");
        List<CityListResult> cityList = cityServiceImpl.getCityListResult();
        List<CityListResult> countyListResult = countyServiceImpl.getCountyListResult();
        cityList.addAll(countyListResult);
        for (CityListResult result : cityList) {
            System.out.println(result);
        }
        return ResultUtil.success(cityList);
    }
}
