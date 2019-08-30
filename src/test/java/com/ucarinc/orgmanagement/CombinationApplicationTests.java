package com.ucarinc.orgmanagement;

import com.ucarinc.orgmanagement.common.Constant;
import com.ucarinc.orgmanagement.dao.*;
import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.entity.DeptPermission;
import com.ucarinc.orgmanagement.entity.vo.PermissionTreeResult;
import com.ucarinc.orgmanagement.entity.vo.TreeResult;
import com.ucarinc.orgmanagement.service.IDataPermissionService;
import com.ucarinc.orgmanagement.service.IDepartmentService;
import com.ucarinc.orgmanagement.utils.TreeUtil;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CombinationApplicationTests {
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Autowired
    private AccountResourceMapper accountResourceMapper;
    @Autowired
    private AccountRoleMapper accountRoleMapper;
    @Autowired
    private LoginHistoryMapper loginHistoryMapper;

    @Autowired
    @Qualifier("ehcacheSessionDao")
    SessionDAO sessionDao;

    @Autowired
    @Qualifier("systemPermissionTree")
    List<PermissionTreeResult> systemTree;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private DeptPermissionMapper deptPermissionMapper;

    @Autowired
    private IDataPermissionService dataPermissionService;
    @Test
    public void contextLoads() {
        Account account = new Account();
        account.setAccountLogin("admin");
        account.setDataPermissionId(Constant.DATA_PERMISSION_RECURSION);
        List<String> deptIdList = dataPermissionService.getAccountDataPermissionDeptIdList(account);
        System.out.println("666");
    }



    @Test
    public void delete(){
        List<String> admin = deptPermissionMapper.selectDeptIdListByLoginAccount("admin");
        System.out.println(admin);
    }
}
