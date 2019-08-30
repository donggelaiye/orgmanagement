package com.ucarinc.orgmanagement.entity.vo;

import java.util.ArrayList;
import java.util.List;

public class ResourceResult {

    private Long id;
    private String label;
    private List<ResourceResult> children = new ArrayList<ResourceResult>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ResourceResult> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceResult> children) {
        this.children = children;
    }

    public void add(ResourceResult r){
        children.add(r);
    }
}
