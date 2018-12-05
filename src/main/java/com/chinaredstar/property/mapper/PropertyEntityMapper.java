package com.chinaredstar.property.mapper;

import com.chinaredstar.property.dao.PropertyEntity;
import com.chinaredstar.property.dao.PropertyEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PropertyEntityMapper {
    long countByExample(PropertyEntityExample example);

    int deleteByExample(PropertyEntityExample example);

    int deleteByPrimaryKey(Long propertyId);

    int insert(PropertyEntity record);

    int insertSelective(PropertyEntity record);

    List<PropertyEntity> selectByExampleWithRowbounds(PropertyEntityExample example, RowBounds rowBounds);

    List<PropertyEntity> selectByExample(PropertyEntityExample example);

    PropertyEntity selectByPrimaryKey(Long propertyId);

    int updateByExampleSelective(@Param("record") PropertyEntity record, @Param("example") PropertyEntityExample example);

    int updateByExample(@Param("record") PropertyEntity record, @Param("example") PropertyEntityExample example);

    int updateByPrimaryKeySelective(PropertyEntity record);

    int updateByPrimaryKey(PropertyEntity record);
}