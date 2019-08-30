package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.vo.ResourceResult;

public interface IResourceService {

    /**
     * 获取部门数据集合
     *
     *
     * @return 部门树数据集合
     */
    ResourceResult selectResource();

}
