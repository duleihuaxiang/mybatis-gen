package com.dulei.goods.service;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.autogen.common.base.service.BaseServiceImpl;
import com.autogen.common.base.result.Page;
import com.autogen.common.utils.BeanUtils;

import com.dulei.goods.dao.BrandEntity;
import com.dulei.goods.dao.BrandEntityExample;
import com.dulei.goods.dao.BrandEntityExample.Criteria;
import com.dulei.goods.mapper.BrandEntityMapper;


@Service
public class BrandServiceImpl extends BaseServiceImpl implements BrandService {
	private static final String TRACKING_TYPE = "Brand";

    @Autowired
    private BrandEntityMapper brandEntityMapper;

	@Override
	public Brand getById(Long brandId) {
		if(brandId == null){
			return null;
		}
		BrandEntity entity = this.brandEntityMapper.selectByPrimaryKey(brandId);
		if (entity != null ) {
			return BeanUtils.copyProperties(entity, Brand.class);
		}

		return null;
	}

	@Override
	public Long create(Brand obj) {
		logger.debug("Creating Brand: {}", obj);

		BrandEntity entity = BeanUtils.copyProperties(obj, BrandEntity.class);
		//entity.setStatus(Status.NORMAL);
		//entity.setCreateBy(obj.getOperator());
		entity.setCreateTime(new Date());
		//entity.setUpdateBy(obj.getOperator());
		entity.setUpdateTime(new Date());

		this.brandEntityMapper.insertSelective(entity);
		obj.setBrandId(entity.getBrandId());

		logger.info("Created Brand: {}", obj);

		return obj.getBrandId();
	}

	@Override
	public void update(Brand obj) {
		logger.debug("Updating Brand: {}", obj);

		BrandEntity entity = BeanUtils.copyProperties(obj, BrandEntity.class);
		//entity.setUpdateBy(obj.getOperator());
		entity.setUpdateTime(new Date());
		this.brandEntityMapper.updateByPrimaryKeySelective(entity);

		logger.info("Updated Brand: {}", obj);
	}

	@Override
	public Long save(Brand obj) {
		logger.debug("Saving Brand: {}", obj);

		if (obj.getBrandId() == null) {
			this.create(obj);
		} else {
			this.update(obj);
		}

		return obj.getBrandId();
	}

	@Override
    public List<Brand> findByQuery(BrandQuery query) {
        BrandEntityExample example = new BrandEntityExample();
        BrandEntityExample.Criteria criteria = example.createCriteria();
        //criteria.andStatusEqualTo(Status.NORMAL);
        example.setOrderByClause("last_update DESC");

        List<BrandEntity> list = this.brandEntityMapper.selectByExample(example);

        return BeanUtils.copyListProperties(list, Brand.class);
    }

	@Override
	public Page<Brand> find(BrandQuery query) {
		Page<Brand> page = new Page<Brand>(query);
		BrandEntityExample example = new BrandEntityExample();
		BrandEntityExample.Criteria criteria = example.createCriteria();
		//criteria.andStatusEqualTo(Status.NORMAL);
		example.setOrderByClause("last_update DESC");
		page.setTotal(Long.valueOf(this.brandEntityMapper.countByExample(example)).intValue());
		if (page.getTotal() > query.getOffset()) {
			List<BrandEntity> list = this.brandEntityMapper.selectByExampleWithRowbounds(example, this.toRowBounds(query));
			page.setItems(BeanUtils.copyListProperties(list, Brand.class));
		}
		
		return page;
	}
}
