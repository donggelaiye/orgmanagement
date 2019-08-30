package com.ucarinc.orgmanagement.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucarinc.orgmanagement.dao.RoleApproveMapper;
import com.ucarinc.orgmanagement.entity.AccountRole;
import com.ucarinc.orgmanagement.entity.RoleApprove;
import com.ucarinc.orgmanagement.entity.form.RoleApproveForm;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.service.IRoleApproveService;
import com.ucarinc.orgmanagement.utils.PageUtil;
import com.ucarinc.orgmanagement.utils.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Date 2019/8/5
 * @Time 16:21
 * @Description 角色申请审批业务实现
 * @Author jiajin.chen@ucarinc.com
 */
@Service
public class RoleApproveServiceImpl implements IRoleApproveService {

    @Autowired
    private RoleApproveMapper roleApproveMapper;
    @Override
    public PageResult getRoleApproveList(Integer pageNum, Integer pageSize, RoleApproveForm roleApproveForm) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        String accountLogin=ShiroUtil.getActiceLoginAccount();
        roleApproveForm.setAccountLogin(accountLogin);
        roleApproveMapper.getRoleApproveList(roleApproveForm);
        return PageUtil.getPageResult(page);
    }

    @Override
    public int approveRefuse(RoleApprove roleApprove) {
        roleApproveMapper.approveRefuse(roleApprove);
        return 1;
    }

    @Override
    public int approveAgree(String approveJSON) {
        JSONObject jsonObject=JSONObject.parseObject(approveJSON);
        String roleApproveStr = (String) jsonObject.get("roleApproveAgree");
        RoleApprove roleApprove=JSONObject.parseObject(roleApproveStr,RoleApprove.class);
        //获取审批人
        String roleApproveName = roleApprove.getRoleApproveName();
        String accountName = ShiroUtil.getActiceLoginAccount();
        String approveAccountName = accountName + "(" +roleApproveName+ ")";
        roleApprove.setApproveAccountName(approveAccountName);
        Long empId = ShiroUtil.getActiveEmpId();
        Long roleId = roleApprove.getRoleId();
        //更新状态为审批通过
        roleApproveMapper.updateAgreeStatus(roleApprove);
        //遍历数组 对角色的相应账户进行添加或者删除
        JSONArray accountDataArray = jsonObject.getJSONArray("accountData");
        for(int i = 0;i<accountDataArray.size();i++){
            AccountRole accountRole=new AccountRole();
            accountRole.setRoleId(roleId);
            String accountLogin = (String)accountDataArray.getJSONObject(i).get("accountLogin");
            accountRole.setAccountLogin(accountLogin);
            accountRole.setCreateEmp(empId);
            accountRole.setModifyEmp(empId);
            accountRole.setIsDelete((byte) 0);
            Date date = new Date();
            accountRole.setCreateTime(date);
            accountRole.setModifyTime(date);
            //0为删除 1为添加
            System.out.println(accountDataArray.getJSONObject(i).get("operateType"));
            System.out.println(accountDataArray.getJSONObject(i).get("operateType").toString());
            if(accountDataArray.getJSONObject(i).get("operateType").toString().equals("0")){
                roleApproveMapper.deleteAgreeAccount(accountRole);

            }else if(accountDataArray.getJSONObject(i).get("operateType").toString().equals("1")){
                //判断账户角色表中是否有相应记录
                System.out.println(roleApproveMapper.getCount(accountRole));
                if(roleApproveMapper.getCount(accountRole)==0){
                    roleApproveMapper.insertAgreeAccount(accountRole);
                }else{
                    roleApproveMapper.updateAgreeAccount(accountRole);
                }

            }
        }
        return 1;
    }
}
