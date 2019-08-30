package com.ucarinc.orgmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucarinc.orgmanagement.dao.AccountPermissionMapper;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.service.IAccountPermissionService;
import com.ucarinc.orgmanagement.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ucarinc.orgmanagement.entity.AccountPermission;


import java.util.List;


@Service
public class AccountPermissionImpl implements IAccountPermissionService {

    @Autowired
    private AccountPermissionMapper accountPermissionMapper;

    @Autowired
    private ResourceServiceImpl resourceServiceImpl;
    private static Integer index = 1;

    /**
     * describe主要对获得的权限树进行拼接，然后存入MAP中，得出完整的权限名称
     * @param accountPermissions
     * @return accountPermissions
     */
    public List<AccountPermission> getPermissionName(List<AccountPermission> accountPermissions) {
        if (index == 1) {
            resourceServiceImpl.getResourceResultMap();
            System.out.println(ResourceServiceImpl.map);
            index++;
        }
        //权限名称拼接
        for (int i = 0; i < accountPermissions.size(); i++) {
            if (accountPermissions.get(i).getPermissionId() != null) {
                String permissionId = String.valueOf(accountPermissions.get(i).getPermissionId());
                StringBuffer stringBuffer = new StringBuffer();
                String[] split = permissionId.split(";");
                for (int j = 0; j < split.length; j++) {
                    if(ResourceServiceImpl.map.get(Long.parseLong(split[j])) == null){

                    }
                    stringBuffer.append(ResourceServiceImpl.map.get(Long.parseLong(split[j])) + ";");
                }
                accountPermissions.get(i).setPermissionName(stringBuffer.substring(0, stringBuffer.length() - 1));
            }
        }
        return accountPermissions;
    }

    @Override
    public PageResult getAccountPermissionList(AccountPermission accountPermission, Integer pageNum, Integer pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<AccountPermission> accountPermissions = getPermissionName(accountPermissionMapper.getAccountPermissionList(accountPermission));

       /* List<AccountPermission> accountPermissions = accountPermissionMapper.getAccountPermissionList(accountPermission);
        for (int i = 0; i < page.size(); i++) {
            AccountPermission accountPermission1 = (AccountPermission) page.get(i);
            StringBuilder sb = new StringBuilder();
            if (accountPermission1.getResourceId() != null) {
                String resourceId = String.valueOf(accountPermission1.getResourceId());
                String[] split = resourceId.split(";");
                for (int k = 0; k < split.length; ++k) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int j = 1; j <= split[k].length(); j += 2) {
                        String id = split[k].substring(0, j);
                        String resourceName = accountPermissionMapper.getPermissionNameByResourceId(id);
                        stringBuilder.append(resourceName).append('-');
                    }
                    String curName = (stringBuilder.substring(0, stringBuilder.length() - 1));
                    sb.append(curName).append(";");
                }
                accountPermissions.get(i).setPermissionName(sb.substring(0, sb.length() - 1));
            }
        }*/
        return PageUtil.getPageResult(page);
    }

    @Override
    public List<AccountPermission> accountPermissionList(AccountPermission accountPermission) {
        List<AccountPermission> accountPermissions = getPermissionName(accountPermissionMapper.getAccountPermissionList(accountPermission));
        return accountPermissions;
    }
}
