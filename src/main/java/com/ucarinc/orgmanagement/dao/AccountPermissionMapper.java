package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.AccountPermission;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * description:账号功能权限Dao接口
 *
 * @author 陈东(dong.chen03@ucarinc.com)
 * @version 1.0
 * @date 2019/8/10 16:05
 */

@Repository(value = "AccountPermissionMapper")
public interface AccountPermissionMapper {
    List<AccountPermission> getAccountPermissionList(AccountPermission accountPermission);

    String getPermissionNameByResourceId(String resourceId);
}
