package com.ucarinc.orgmanagement.entity.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 部门树输出的结果类
 *
 * @author 赖家美 （2477637880@qq.com）
 * @version 1.0
 * @date 2019-08-08 9:36:00
 */
public class TreeResult implements Serializable {

    private String id;
    private String label;
    private String parentId;
    private Boolean disabled;
    private List<TreeResult> children = new ArrayList<TreeResult>();

    public TreeResult() {
        this.disabled = false;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<TreeResult> getChildren() {
        return children;
    }

    public void setChildren(List<TreeResult> children) {
        this.children = children;
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    public void add(TreeResult t) {
        children.add(t);
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "TreeResult{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}
