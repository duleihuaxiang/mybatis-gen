package com.dulei.goods.mapper;

import com.dulei.goods.dao.BrandEntity;
import com.dulei.goods.dao.BrandEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface BrandEntityMapper {
    long countByExample(BrandEntityExample example);

    int deleteByExample(BrandEntityExample example);

    int deleteByPrimaryKey(Long brandId);

    int insert(BrandEntity record);

    int insertSelective(BrandEntity record);

    List<BrandEntity> selectByExampleWithRowbounds(BrandEntityExample example, RowBounds rowBounds);

    List<BrandEntity> selectByExample(BrandEntityExample example);

    BrandEntity selectByPrimaryKey(Long brandId);

    int updateByExampleSelective(@Param("record") BrandEntity record, @Param("example") BrandEntityExample example);

    int updateByExample(@Param("record") BrandEntity record, @Param("example") BrandEntityExample example);

    int updateByPrimaryKeySelective(BrandEntity record);

    int updateByPrimaryKey(BrandEntity record);
}