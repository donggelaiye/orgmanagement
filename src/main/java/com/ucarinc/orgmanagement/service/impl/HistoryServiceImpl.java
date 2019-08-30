package com.ucarinc.orgmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.ucarinc.orgmanagement.dao.HistoryMapper;
import com.ucarinc.orgmanagement.dao.LoginHistoryMapper;
import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.entity.History;
import com.ucarinc.orgmanagement.entity.LoginHistory;
import com.ucarinc.orgmanagement.entity.vo.AccountResult;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.service.IHistoryService;
import com.ucarinc.orgmanagement.utils.ComUtil;
import com.ucarinc.orgmanagement.utils.PageUtil;
import com.ucarinc.orgmanagement.utils.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * description:历史记录Service接口实现类
 */
@Service
public class HistoryServiceImpl implements IHistoryService {

    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private LoginHistoryMapper loginHistoryMapper;

    @Override
    public PageResult selectAllHistory(Integer pageNum, Integer pageSize)
    {
        //使用 PageHelper插件进行分页
        Page page = PageHelper.startPage(pageNum, pageSize);
        historyMapper.selectAllHistory();
        for (Object object : page) {
            History history = (History) object;
            this.improveAccInfo(history);
        }
        return PageUtil.getPageResult(page);
    }

    @Override
    public int addAccHistory(Account account, int operate)
    {
        History history = new History();
        history.setModifyEmp(ShiroUtil.getActiveEmpId());
        if(null != account.getEmpId()){
            history.setEmpId(account.getEmpId());
        }
        if(null != account.getDataPermissionId()){
            history.setDataPermission(account.getDataPermissionId());
        }
        if(null != account.getStatus()){
            history.setStatus(account.getStatus());
        }

        switch (operate)
        {
            case 0://新建
                history.setOperateType((byte) 0);
                break;
            case 1://修改
                history.setOperateType((byte) 1);
                break;
            case 2://删除
                history.setOperateType((byte) 2);
                break;
            case 3://冻结
                history.setOperateType((byte) 3);
                break;
            case 4://解冻
                history.setOperateType((byte) 4);
                break;
            case 5://密码重置
                history.setOperateType((byte) 5);
                break;
            case 6://分配权限
                history.setOperateType((byte) 6);
                break;
            case 7://分配角色权限
                history.setOperateType((byte) 7);
                break;
            case 8://分配菜单权限
                history.setOperateType((byte) 8);
                break;
            case 9://员工删除
                history.setOperateType((byte) 9);
                break;
            case 10://员工离职
                history.setOperateType((byte) 10);
                break;
            default:
        }
        return historyMapper.insertSelective(history);

    }

    @Override
    public boolean insertFirstTimeLoginInfo(String loginAccount, String createEmpId) {
        if (null != loginAccount && null != createEmpId){
            if (loginHistoryMapper.insertFirstTimeLoginInfo(loginAccount,createEmpId) > 0){
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public LoginHistory getLoginHistoryById(String loginAccount) {
        if (StringUtil.isNotEmpty(loginAccount)){
            return loginHistoryMapper.selectByLoginAccount(loginAccount);
        }else {
            return null;
        }
    }

    /**
     * 对员工信息进行完善
     *
     * @param history
     */
    public void improveAccInfo(History history) {
            //获取修改员工姓名
            if (null != history.getModifyEmp()) {
                Long empId = history.getModifyEmp();
                history.setModifyEmpName(ComUtil.getEmpStr(empId));
            }
        }

}
