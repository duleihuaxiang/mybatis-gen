package com.redstar.template.mapper;

import com.redstar.template.dao.TemplateEntity;
import com.redstar.template.dao.TemplateEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TemplateEntityMapper {
    long countByExample(TemplateEntityExample example);

    int deleteByExample(TemplateEntityExample example);

    int deleteByPrimaryKey(Long templateId);

    int insert(TemplateEntity record);

    int insertSelective(TemplateEntity record);

    List<TemplateEntity> selectByExampleWithRowbounds(TemplateEntityExample example, RowBounds rowBounds);

    List<TemplateEntity> selectByExample(TemplateEntityExample example);

    TemplateEntity selectByPrimaryKey(Long templateId);

    int updateByExampleSelective(@Param("record") TemplateEntity record, @Param("example") TemplateEntityExample example);

    int updateByExample(@Param("record") TemplateEntity record, @Param("example") TemplateEntityExample example);

    int updateByPrimaryKeySelective(TemplateEntity record);

    int updateByPrimaryKey(TemplateEntity record);
}