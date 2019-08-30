package com.ucarinc.orgmanagement.entity.vo;

import java.io.Serializable;

/**
 * description: 文件说明
 * @author 作者 （邮箱）
 * @date yyyy-MM-dd HH:mm:ss
 * @version 1.0
 */
public class PageResult implements Serializable {
    private long total;
    private Object pageData;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getPageData() {
        return pageData;
    }

    public void setPageData(Object pageData) {
        this.pageData = pageData;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                ", pageData=" + pageData +
                '}';
    }
}
