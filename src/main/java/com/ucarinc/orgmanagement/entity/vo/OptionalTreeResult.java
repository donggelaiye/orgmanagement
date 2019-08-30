package com.ucarinc.orgmanagement.entity.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OptionalTreeResult implements Serializable {

    private String id;
    private String label;
    private Boolean disabled;
    private List<OptionalTreeResult> children = new ArrayList<OptionalTreeResult>();
    public OptionalTreeResult(){
        this.disabled=false;
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

    public List<OptionalTreeResult> getChildren() {
        return children;
    }

    public void setChildren(List<OptionalTreeResult> children) {
        this.children = children;
    }

    public void add(OptionalTreeResult t){
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
        return "OptionalTreeResult{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", disabled=" + disabled +
                ", children=" + children +
                '}';
    }
}
