package com.ucarinc.orgmanagement.service.impl;

import com.ucarinc.orgmanagement.dao.ResourceMapper;
import com.ucarinc.orgmanagement.entity.Resource;
import com.ucarinc.orgmanagement.entity.vo.ResourceResult;
import com.ucarinc.orgmanagement.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ResourceServiceImpl implements IResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    static HashMap<Long, String> map;

    @Override
    public ResourceResult selectResource() {
        // resourceList是从数据库中获得的权限数据集合
        List<Resource> resourceList = new ArrayList<Resource>();
        resourceList = resourceMapper.selectResource();
        List<ResourceResult> list = new ArrayList<ResourceResult>();
        // 用来记录根节点的下标
        int flag = 1;
        for (int i = 0; i < resourceList.size(); i++) {
            ResourceResult r = new ResourceResult();
            r.setId(resourceList.get(i).getResourceId());
            r.setLabel(resourceList.get(i).getPermissionName());
            list.add(r);
            if (resourceList.get(i).getParentId() == null) {
                flag = i;
            }
        }
        for (int i = 0; i < resourceList.size(); i++) {

            for (int j = i + 1; j < resourceList.size(); j++) {
                if (resourceList.get(j).getParentId() != null && resourceList.get(j).getParentId().equals(resourceList.get(i).getResourceId())) {
                    list.get(i).add(list.get(j));
                }
            }
        }
        return list.get(flag);
    }

    void recursive(List<ResourceResult> list) {
        List<ResourceResult> one, all = new ArrayList<>();

    }

   static void getChilds(ResourceResult parent, String name) {

        Long id1 = parent.getId();
        String name1 = parent.getLabel();
        StringBuffer bf = new StringBuffer();
        bf = bf.append(name).append("-").append(name1);
        map.put(id1, bf.toString().substring(1, bf.length()));
        for (int i = 0; i < parent.getChildren().size(); i++) {
            getChilds(parent.getChildren().get(i), bf.toString());
        }
    }

    public void getResourceResultMap() {
        // resourceList是从数据库中获得的权限数据集合
        List<Resource> resourceList = resourceMapper.selectResource();
        List<ResourceResult> list = new ArrayList<ResourceResult>();
        // 用来记录根节点的下标
        int rootNode = 1;
        for (int i = 0; i < resourceList.size(); i++) {
            ResourceResult r = new ResourceResult();
            r.setId(resourceList.get(i).getResourceId());
            r.setLabel(resourceList.get(i).getPermissionName());
            list.add(r);
            if (resourceList.get(i).getParentId() == null) {
                rootNode = i;
            }
        }
        for (int i = 0; i < resourceList.size(); i++) {
            for (int j = i + 1; j < resourceList.size(); j++) {
                if (resourceList.get(j).getParentId() != null && resourceList.get(j).getParentId().equals(resourceList.get(i).getResourceId())) {
                    list.get(i).add(list.get(j));
                }
            }
        }
        StringBuffer name = new StringBuffer();
        if (map == null || map.size() < 1) {
            map = new HashMap<>();
            getChilds(list.get(rootNode), "");
        }
    }
}
