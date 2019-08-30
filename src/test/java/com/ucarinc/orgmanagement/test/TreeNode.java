package com.ucarinc.orgmanagement.test;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 该文件说明
 *
 * @author daijianzhao
 * @version 1.0
 * @date 2019-08-04 11:36
 */
public class TreeNode {

    private String name;
    private int id;
    private int pid;
    private List<TreeNode> list;

    public void add(TreeNode node){
        this.list.add(node);
    }
    public TreeNode(String name,int id,int pid){
        this.name=name;
        this.id=id;
        this.pid=pid;
        list=new ArrayList<>();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<TreeNode> getList() {
        return list;
    }

    public void setList(List<TreeNode> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "\nTreeNode{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", pid=" + pid +
                ", \nlist=" + list +
                '}';
    }
}
