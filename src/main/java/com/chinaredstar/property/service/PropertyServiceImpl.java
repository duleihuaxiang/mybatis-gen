package com.chinaredstar.property.service;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.autogen.common.base.service.BaseServiceImpl;
import com.autogen.common.base.result.Page;
import com.autogen.common.utils.BeanUtils;

import com.chinaredstar.property.dao.PropertyEntity;
import com.chinaredstar.property.dao.PropertyEntityExample;
import com.chinaredstar.property.dao.PropertyEntityExample.Criteria;
import com.chinaredstar.property.mapper.PropertyEntityMapper;


@Service
public class PropertyServiceImpl extends BaseServiceImpl implements PropertyService {
	private static final String TRACKING_TYPE = "Property";

    @Autowired
    private PropertyEntityMapper propertyEntityMapper;

	@Override
	public Property getById(Long propertyId) {
		if(propertyId == null){
			return null;
		}
		PropertyEntity entity = this.propertyEntityMapper.selectByPrimaryKey(propertyId);
		if (entity != null ) {
			return BeanUtils.copyProperties(entity, Property.class);
		}

		return null;
	}

	@Override
	public Long create(Property obj) {
		logger.debug("Creating Property: {}", obj);

		PropertyEntity entity = BeanUtils.copyProperties(obj, PropertyEntity.class);
		//entity.setStatus(Status.NORMAL);
		//entity.setCreateBy(obj.getOperator());
		entity.setCreateTime(new Date());
		//entity.setUpdateBy(obj.getOperator());
		entity.setUpdateTime(new Date());

		this.propertyEntityMapper.insertSelective(entity);
		obj = this.getById(entity.getPropertyId());

		//obj.setPropertyId(entity.getPropertyId());

		logger.info("Created Property: {}", obj);

		return obj.getPropertyId();
	}

	@Override
	public void update(Property obj) {
		logger.debug("Updating Property: {}", obj);

		PropertyEntity entity = BeanUtils.copyProperties(obj, PropertyEntity.class);
		//entity.setUpdateBy(obj.getOperator());
		entity.setUpdateTime(new Date());
		this.propertyEntityMapper.updateByPrimaryKeySelective(entity);

		logger.info("Updated Property: {}", obj);
	}

	@Override
	public Long save(Property obj) {
		logger.debug("Saving Property: {}", obj);

		if (obj.getPropertyId() == null) {
			this.create(obj);
		} else {
			this.update(obj);
		}

		return obj.getPropertyId();
	}

	@Override
    public List<Property> findByQuery(PropertyQuery query, Property property) {
        PropertyEntityExample example = new PropertyEntityExample();
        PropertyEntityExample.Criteria criteria = example.createCriteria();
        //criteria.andStatusEqualTo(Status.NORMAL);
        example.setOrderByClause("last_update DESC");

        List<PropertyEntity> list = this.propertyEntityMapper.selectByExample(example);

        return BeanUtils.copyListProperties(list, Property.class);
    }

	@Override
	public Page<Property> findWithPage(PropertyQuery query,Property property) {
		Page<Property> page = new Page<Property>(query);
		PropertyEntityExample example = new PropertyEntityExample();
		PropertyEntityExample.Criteria criteria = example.createCriteria();
		//criteria.andStatusEqualTo(Status.NORMAL);
		example.setOrderByClause("last_update DESC");
		page.setTotal(Long.valueOf(this.propertyEntityMapper.countByExample(example)).intValue());
		if (page.getTotal() > query.getOffset()) {
			List<PropertyEntity> list = this.propertyEntityMapper.selectByExampleWithRowbounds(example, this.toRowBounds(query));
			page.setItems(BeanUtils.copyListProperties(list, Property.class));
		}
		
		return page;
	}
}
