package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.History;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * description:历史记录Mapper
 */
@Repository
@Mapper
public interface HistoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(History record);

    int insertSelective(History record);

    History selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(History record);

    int updateByPrimaryKey(History record);

    List<History> selectAllHistory();
}