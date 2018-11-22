package com.redstar.template.service;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.autogen.common.base.service.BaseServiceImpl;
import com.autogen.common.base.result.Page;
import com.autogen.common.utils.BeanUtils;

import com.redstar.template.dao.TemplateEntity;
import com.redstar.template.dao.TemplateEntityExample;
import com.redstar.template.dao.TemplateEntityExample.Criteria;
import com.redstar.template.mapper.TemplateEntityMapper;


@Service
public class TemplateServiceImpl extends BaseServiceImpl implements TemplateService {
	private static final String TRACKING_TYPE = "Template";

    @Autowired
    private TemplateEntityMapper templateEntityMapper;

	@Override
	public Template getById(Long templateId) {
		if(templateId == null){
			return null;
		}
		TemplateEntity entity = this.templateEntityMapper.selectByPrimaryKey(templateId);
		if (entity != null ) {
			return BeanUtils.copyProperties(entity, Template.class);
		}

		return null;
	}

	@Override
	public Long create(Template obj) {
		logger.debug("Creating Template: {}", obj);

		TemplateEntity entity = BeanUtils.copyProperties(obj, TemplateEntity.class);
		//entity.setStatus(Status.NORMAL);
		//entity.setCreateBy(obj.getOperator());
		entity.setCreateTime(new Date());
		//entity.setUpdateBy(obj.getOperator());
		entity.setUpdateTime(new Date());

		this.templateEntityMapper.insertSelective(entity);
		obj = this.getById(entity.getTemplateId());

		//obj.setTemplateId(entity.getTemplateId());

		logger.info("Created Template: {}", obj);

		return obj.getTemplateId();
	}

	@Override
	public void update(Template obj) {
		logger.debug("Updating Template: {}", obj);

		TemplateEntity entity = BeanUtils.copyProperties(obj, TemplateEntity.class);
		//entity.setUpdateBy(obj.getOperator());
		entity.setUpdateTime(new Date());
		this.templateEntityMapper.updateByPrimaryKeySelective(entity);

		logger.info("Updated Template: {}", obj);
	}

	@Override
	public Long save(Template obj) {
		logger.debug("Saving Template: {}", obj);

		if (obj.getTemplateId() == null) {
			this.create(obj);
		} else {
			this.update(obj);
		}

		return obj.getTemplateId();
	}

	@Override
    public List<Template> findByQuery(TemplateQuery query) {
        TemplateEntityExample example = new TemplateEntityExample();
        TemplateEntityExample.Criteria criteria = example.createCriteria();
        //criteria.andStatusEqualTo(Status.NORMAL);
        example.setOrderByClause("last_update DESC");

        List<TemplateEntity> list = this.templateEntityMapper.selectByExample(example);

        return BeanUtils.copyListProperties(list, Template.class);
    }

	@Override
	public Page<Template> find(TemplateQuery query) {
		Page<Template> page = new Page<Template>(query);
		TemplateEntityExample example = new TemplateEntityExample();
		TemplateEntityExample.Criteria criteria = example.createCriteria();
		//criteria.andStatusEqualTo(Status.NORMAL);
		example.setOrderByClause("last_update DESC");
		page.setTotal(Long.valueOf(this.templateEntityMapper.countByExample(example)).intValue());
		if (page.getTotal() > query.getOffset()) {
			List<TemplateEntity> list = this.templateEntityMapper.selectByExampleWithRowbounds(example, this.toRowBounds(query));
			page.setItems(BeanUtils.copyListProperties(list, Template.class));
		}
		
		return page;
	}
}
