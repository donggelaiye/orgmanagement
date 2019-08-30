package com.ucarinc.orgmanagement.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 部门树
 * @author daijianzhao
 * @version 1.0
 * @date 2019-08-12 01:26
 */
public class DeptTree{
    private Department dept;

    private List<DeptTree> children;

    public DeptTree(){
        children=new ArrayList<>();
    }
    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public List<DeptTree> getChildren() {
        return children;
    }
    public void setChildren(List<DeptTree> children) {
        this.children = children;
    }
}
