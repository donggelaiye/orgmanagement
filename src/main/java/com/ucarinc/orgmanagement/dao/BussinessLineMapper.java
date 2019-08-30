package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.BussinessLine;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository(value = "BussinessLineMapper")
public interface BussinessLineMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BussinessLine record);

    int insertSelective(BussinessLine record);

    BussinessLine selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BussinessLine record);

    int updateByPrimaryKey(BussinessLine record);

    List<BussinessLine> getLineList();
}