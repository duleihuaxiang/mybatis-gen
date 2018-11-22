package com.redstar.templateproperty.mapper;

import com.redstar.templateproperty.dao.TemplatePropertyEntity;
import com.redstar.templateproperty.dao.TemplatePropertyEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TemplatePropertyEntityMapper {
    long countByExample(TemplatePropertyEntityExample example);

    int deleteByExample(TemplatePropertyEntityExample example);

    int deleteByPrimaryKey(Long templatePropertyId);

    int insert(TemplatePropertyEntity record);

    int insertSelective(TemplatePropertyEntity record);

    List<TemplatePropertyEntity> selectByExampleWithRowbounds(TemplatePropertyEntityExample example, RowBounds rowBounds);

    List<TemplatePropertyEntity> selectByExample(TemplatePropertyEntityExample example);

    TemplatePropertyEntity selectByPrimaryKey(Long templatePropertyId);

    int updateByExampleSelective(@Param("record") TemplatePropertyEntity record, @Param("example") TemplatePropertyEntityExample example);

    int updateByExample(@Param("record") TemplatePropertyEntity record, @Param("example") TemplatePropertyEntityExample example);

    int updateByPrimaryKeySelective(TemplatePropertyEntity record);

    int updateByPrimaryKey(TemplatePropertyEntity record);
}