package com.ucarinc.orgmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucarinc.orgmanagement.dao.RoleAccountDetailMapper;
import com.ucarinc.orgmanagement.entity.RoleAccountDetail;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.service.IRoleAccountDetailService;
import com.ucarinc.orgmanagement.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date 2019/8/7
 * @Time 16:04
 * @Description 角色账户明细业务实现类
 * @Author jiajin.chen@ucarinc.com
 */
@Service
public class RoleAccountDetailServiceImpl implements IRoleAccountDetailService {

    @Autowired
    private RoleAccountDetailMapper roleAccountDetailMapper;




    @Override
    public PageResult getRoleAccountList(Integer pageNum, Integer pageSize, RoleAccountDetail roleAccountDetail) {
        //使用 PageHelper插件进行分页
        Page page = PageHelper.startPage(pageNum, pageSize);
        roleAccountDetailMapper.getRoleAccountList(roleAccountDetail);
        return PageUtil.getPageResult(page);
    }

    @Override
    public List<RoleAccountDetail> getRoleAccountList(RoleAccountDetail roleAccountDetail) {
        return roleAccountDetailMapper.getRoleAccountList(roleAccountDetail);
    }
}
