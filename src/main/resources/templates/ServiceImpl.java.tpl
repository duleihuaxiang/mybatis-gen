package #package.service#;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.autogen.common.base.service.BaseServiceImpl;
import com.autogen.common.base.result.Page;
import com.autogen.common.utils.BeanUtils;

import #package.dao#.dao.#domain.className#Entity;
import #package.dao#.dao.#domain.className#EntityExample;
import #package.dao#.dao.#domain.className#EntityExample.Criteria;
import #package.dao#.mapper.#domain.className#EntityMapper;


@Service
public class #domain.className#ServiceImpl extends BaseServiceImpl implements #domain.className#Service {
	private static final String TRACKING_TYPE = "#domain.className#";

    @Autowired
    private #domain.className#EntityMapper #domain.objName#EntityMapper;

	@Override
	public #domain.className# getById(Long #primaryKeyField#) {
		if(#primaryKeyField# == null){
			return null;
		}
		#domain.className#Entity entity = this.#domain.objName#EntityMapper.selectByPrimaryKey(#primaryKeyField#);
		if (entity != null ) {
			return BeanUtils.copyProperties(entity, #domain.className#.class);
		}

		return null;
	}

	@Override
	public Long create(#domain.className# obj) {
		logger.debug("Creating #domain.className#: {}", obj);

		#domain.className#Entity entity = BeanUtils.copyProperties(obj, #domain.className#Entity.class);
		//entity.setStatus(Status.NORMAL);
		//entity.setCreateBy(obj.getOperator());
		entity.setCreateTime(new Date());
		//entity.setUpdateBy(obj.getOperator());
		entity.setUpdateTime(new Date());

		this.#domain.objName#EntityMapper.insertSelective(entity);
		obj = this.getById(entity.get#domain.className#Id());

		//obj.#primaryKeyField.setMethod#(entity.#primaryKeyField.getMethod#());

		logger.info("Created #domain.className#: {}", obj);

		return obj.#primaryKeyField.getMethod#();
	}

	@Override
	public void update(#domain.className# obj) {
		logger.debug("Updating #domain.className#: {}", obj);

		#domain.className#Entity entity = BeanUtils.copyProperties(obj, #domain.className#Entity.class);
		//entity.setUpdateBy(obj.getOperator());
		entity.setUpdateTime(new Date());
		this.#domain.objName#EntityMapper.updateByPrimaryKeySelective(entity);

		logger.info("Updated #domain.className#: {}", obj);
	}

	@Override
	public Long save(#domain.className# obj) {
		logger.debug("Saving #domain.className#: {}", obj);

		if (obj.#primaryKeyField.getMethod#() == null) {
			this.create(obj);
		} else {
			this.update(obj);
		}

		return obj.#primaryKeyField.getMethod#();
	}

	@Override
    public List<#domain.className#> findByQuery(#domain.className#Query query, #domain.className# #domain.objName#) {
        #domain.className#EntityExample example = new #domain.className#EntityExample();
        #domain.className#EntityExample.Criteria criteria = example.createCriteria();
        //criteria.andStatusEqualTo(Status.NORMAL);
        example.setOrderByClause("last_update DESC");

        List<#domain.className#Entity> list = this.#domain.objName#EntityMapper.selectByExample(example);

        return BeanUtils.copyListProperties(list, #domain.className#.class);
    }

	@Override
	public Page<#domain.className#> findWithPage(#domain.className#Query query,#domain.className# #domain.objName#) {
		Page<#domain.className#> page = new Page<#domain.className#>(query);
		#domain.className#EntityExample example = new #domain.className#EntityExample();
		#domain.className#EntityExample.Criteria criteria = example.createCriteria();
		//criteria.andStatusEqualTo(Status.NORMAL);
		example.setOrderByClause("last_update DESC");
		page.setTotal(Long.valueOf(this.#domain.objName#EntityMapper.countByExample(example)).intValue());
		if (page.getTotal() > query.getOffset()) {
			List<#domain.className#Entity> list = this.#domain.objName#EntityMapper.selectByExampleWithRowbounds(example, this.toRowBounds(query));
			page.setItems(BeanUtils.copyListProperties(list, #domain.className#.class));
		}
		
		return page;
	}
}
