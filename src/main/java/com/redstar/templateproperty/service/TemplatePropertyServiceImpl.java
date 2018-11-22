package com.redstar.templateproperty.service;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.autogen.common.base.service.BaseServiceImpl;
import com.autogen.common.base.result.Page;
import com.autogen.common.utils.BeanUtils;

import com.redstar.templateproperty.dao.TemplatePropertyEntity;
import com.redstar.templateproperty.dao.TemplatePropertyEntityExample;
import com.redstar.templateproperty.dao.TemplatePropertyEntityExample.Criteria;
import com.redstar.templateproperty.mapper.TemplatePropertyEntityMapper;


@Service
public class TemplatePropertyServiceImpl extends BaseServiceImpl implements TemplatePropertyService {
	private static final String TRACKING_TYPE = "TemplateProperty";

    @Autowired
    private TemplatePropertyEntityMapper templatePropertyEntityMapper;

	@Override
	public TemplateProperty getById(Long templatePropertyId) {
		if(templatePropertyId == null){
			return null;
		}
		TemplatePropertyEntity entity = this.templatePropertyEntityMapper.selectByPrimaryKey(templatePropertyId);
		if (entity != null ) {
			return BeanUtils.copyProperties(entity, TemplateProperty.class);
		}

		return null;
	}

	@Override
	public Long create(TemplateProperty obj) {
		logger.debug("Creating TemplateProperty: {}", obj);

		TemplatePropertyEntity entity = BeanUtils.copyProperties(obj, TemplatePropertyEntity.class);
		//entity.setStatus(Status.NORMAL);
		//entity.setCreateBy(obj.getOperator());
		entity.setCreateTime(new Date());
		//entity.setUpdateBy(obj.getOperator());
		entity.setUpdateTime(new Date());

		this.templatePropertyEntityMapper.insertSelective(entity);
		obj = this.getById(entity.getTemplatePropertyId());

		//obj.setTemplatePropertyId(entity.getTemplatePropertyId());

		logger.info("Created TemplateProperty: {}", obj);

		return obj.getTemplatePropertyId();
	}

	@Override
	public void update(TemplateProperty obj) {
		logger.debug("Updating TemplateProperty: {}", obj);

		TemplatePropertyEntity entity = BeanUtils.copyProperties(obj, TemplatePropertyEntity.class);
		//entity.setUpdateBy(obj.getOperator());
		entity.setUpdateTime(new Date());
		this.templatePropertyEntityMapper.updateByPrimaryKeySelective(entity);

		logger.info("Updated TemplateProperty: {}", obj);
	}

	@Override
	public Long save(TemplateProperty obj) {
		logger.debug("Saving TemplateProperty: {}", obj);

		if (obj.getTemplatePropertyId() == null) {
			this.create(obj);
		} else {
			this.update(obj);
		}

		return obj.getTemplatePropertyId();
	}

	@Override
    public List<TemplateProperty> findByQuery(TemplatePropertyQuery query) {
        TemplatePropertyEntityExample example = new TemplatePropertyEntityExample();
        TemplatePropertyEntityExample.Criteria criteria = example.createCriteria();
        //criteria.andStatusEqualTo(Status.NORMAL);
        example.setOrderByClause("last_update DESC");

        List<TemplatePropertyEntity> list = this.templatePropertyEntityMapper.selectByExample(example);

        return BeanUtils.copyListProperties(list, TemplateProperty.class);
    }

	@Override
	public Page<TemplateProperty> find(TemplatePropertyQuery query) {
		Page<TemplateProperty> page = new Page<TemplateProperty>(query);
		TemplatePropertyEntityExample example = new TemplatePropertyEntityExample();
		TemplatePropertyEntityExample.Criteria criteria = example.createCriteria();
		//criteria.andStatusEqualTo(Status.NORMAL);
		example.setOrderByClause("last_update DESC");
		page.setTotal(Long.valueOf(this.templatePropertyEntityMapper.countByExample(example)).intValue());
		if (page.getTotal() > query.getOffset()) {
			List<TemplatePropertyEntity> list = this.templatePropertyEntityMapper.selectByExampleWithRowbounds(example, this.toRowBounds(query));
			page.setItems(BeanUtils.copyListProperties(list, TemplateProperty.class));
		}
		
		return page;
	}
}
