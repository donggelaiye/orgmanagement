package com.ucarinc.orgmanagement.controller;

import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.service.IHistoryService;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * description:历史记录Controller
 *
 */

@RestController
@RequestMapping("/history")
@Slf4j
public class HistoryController {

    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private IHistoryService historyService;

    @RequestMapping("/management/selectAllHistory")
    @RequiresPermissions("account:selectAllHistory")
    //查询
    public Result selectAllHistory(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        logger.info("==调用获取历史记录分页方法==");
//        return ResultUtil.success(accountService.getAccountList(pageNum, pageSize));
        return ResultUtil.success(historyService.selectAllHistory(pageNum, pageSize));
    }
}
