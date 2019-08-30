package com.ucarinc.orgmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucarinc.orgmanagement.dao.RoleResourceDetailMapper;
import com.ucarinc.orgmanagement.entity.form.RoleResourceForm;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.entity.vo.ResourceResult;
import com.ucarinc.orgmanagement.service.IRoleResourceDetailService;
import com.ucarinc.orgmanagement.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Date 2019/8/9
 * @Time 11:31
 * @Description
 * @Author jiajin.chen@ucarinc.com
 */
@Service
public class RoleResourceDetailServiceImpl implements IRoleResourceDetailService {
    @Autowired
    private RoleResourceDetailMapper roleResourceDetailMapper;

    @Autowired
    private ResourceServiceImpl resourceService;


    public List<RoleResourceForm> getNewForm(List<RoleResourceForm> roleResourceForms){
        //权限名称拼接
        for (int i = 0; i < roleResourceForms.size(); i++) {
            RoleResourceForm roleResourceForm1 = roleResourceForms.get(i);
            StringBuilder sb = new StringBuilder();
            if (roleResourceForm1.getResourceIdStr() != null) {
                String resourceId = String.valueOf(roleResourceForm1.getResourceIdStr());
                String[] split = resourceId.split(";");

                // 用遍历数的方法修改权限树的label
                ResourceResult resourceResult = resourceService.selectResource();
                // list用来存储权限的新型节点
                List<ResourceResult> list = new ArrayList();
                // stack用来遍历遍历之前得到的老型权限树
                Stack<ResourceResult> stack = new Stack<>();
                // r是树的根节点
                ResourceResult r = new ResourceResult();
                r.setId(resourceResult.getId());
                r.setLabel(resourceResult.getLabel());
                r.setChildren(resourceResult.getChildren());
                stack.push(r);
                while (!stack.isEmpty()){
                    ResourceResult result = stack.pop();
                    if (result.getChildren().size() > 0){
                        for(int m = 0; m < result.getChildren().size(); m++){
                            result.getChildren().get(m).setLabel(result.getLabel() + "-" + result.getChildren().get(m).getLabel());
                            stack.push(result.getChildren().get(m));
                        }
                    }
                    list.add(result);
                }
                for (int k = 0; k < split.length; ++k) {
                    for (int j = 0; j < list.size(); j++){
                        if (Long.valueOf(split[k]).equals(list.get(j).getId())){
                            sb.append(list.get(j).getLabel()).append(";");
                            break;
                        }
                    }
                }
                roleResourceForms.get(i).setPermissionName(sb.substring(0, sb.length() - 1));
            }
        }
        return roleResourceForms;
    }
    @Override
    public PageResult getRoleResourceList(Integer pageNum, Integer pageSize, RoleResourceForm roleResourceForm){
        //使用 PageHelper插件进行分页
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<RoleResourceForm> roleResourceForms = getNewForm(roleResourceDetailMapper.getRoleResourceList(roleResourceForm));
        return PageUtil.getPageResult(page);
    }


    @Override
    public List<RoleResourceForm> roleResourceList(RoleResourceForm roleResourceForm){
        return getNewForm(roleResourceDetailMapper.getRoleResourceList(roleResourceForm));
    }
}
