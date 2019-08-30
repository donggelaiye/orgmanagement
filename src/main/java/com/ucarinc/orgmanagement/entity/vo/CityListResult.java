package com.ucarinc.orgmanagement.entity.vo;

import java.io.Serializable;

/**
 * description: 该文件说明
 *
 * @author daijianzhao
 * @version 1.0
 * @date 2019-08-06 22:04
 */
public class CityListResult implements Serializable {
    private String value;
    private String name;
    private Integer type;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CityListResult{" +
                "value='" + value + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
