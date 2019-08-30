package com.ucarinc.orgmanagement.controller;

import com.ucarinc.orgmanagement.entity.vo.ResourceResult;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.service.IResourceService;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/resource")
public class ResourceController {

    @Autowired
    private IResourceService resourceService;
    @CrossOrigin
    @RequestMapping(value = "/tree",method = RequestMethod.GET)
    public Result selectResource(){
        List list = new ArrayList();
        list.add(resourceService.selectResource());
        return ResultUtil.success(list);
    }

}
