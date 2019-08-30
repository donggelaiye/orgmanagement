package com.ucarinc.orgmanagement;

import com.ucarinc.orgmanagement.dao.ResourceMapper;
import com.ucarinc.orgmanagement.entity.Resource;
import com.ucarinc.orgmanagement.entity.vo.PermissionTreeResult;
import com.ucarinc.orgmanagement.utils.TreeUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 文件说明
 * @author 作者 （邮箱）y
 * @date yyyy-MM-dd HH:mm:ss
 * @version 1.0
 */
@SpringBootApplication
@MapperScan("com.ucarinc.orgmanagement.dao")
@EnableTransactionManagement
public class OrgmanagementApplication {

    @Autowired
    private ResourceMapper resourceMapper;

    public static void main(String[] args) {
        SpringApplication.run(OrgmanagementApplication.class, args);
    }

    @Bean(name = "systemPermissionTree")
    public List<PermissionTreeResult> generateSystemPermissionTree() {
        List<Resource> permissionList = resourceMapper.selectAllResource();
        List<PermissionTreeResult> permissionNodeList = new ArrayList<>();
        for (Resource resource : permissionList) {
            PermissionTreeResult node = new PermissionTreeResult();
            node.setId(resource.getResourceId().toString());
            node.setLabel(resource.getPermissionName());
            node.setPermissionName(resource.getPermissionName());
            node.setPermissionCode(resource.getPermissionCode());
            String parentId = resource.getParentId()==null?null:resource.getParentId().toString();
            node.setParentId(parentId);
            permissionNodeList.add(node);
        }
        List<PermissionTreeResult> tree = TreeUtil.buildTree(permissionNodeList);
        return tree;
    }
}
