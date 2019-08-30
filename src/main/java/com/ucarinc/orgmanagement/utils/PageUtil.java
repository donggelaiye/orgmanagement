package com.ucarinc.orgmanagement.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ucarinc.orgmanagement.entity.vo.PageResult;

/**
 * description: 生成分页数据的工具类
 * @author 作者 （邮箱）
 * @date yyyy-MM-dd HH:mm:ss
 * @version 1.0
 */
public class PageUtil {

    /**
     * 生成分页结果封装类
     * @param page 本页数据
     * @return PageResult类
     */
    public static PageResult getPageResult(Page page){
        PageInfo pageInfo = PageInfo.of(page);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setPageData(page);
        return pageResult;
    }
}
