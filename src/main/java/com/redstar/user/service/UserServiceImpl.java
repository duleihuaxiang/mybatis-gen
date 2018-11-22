package com.redstar.user.service;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.autogen.common.base.service.BaseServiceImpl;
import com.autogen.common.base.result.Page;
import com.autogen.common.utils.BeanUtils;

import com.redstar.user.dao.UserEntity;
import com.redstar.user.dao.UserEntityExample;
import com.redstar.user.dao.UserEntityExample.Criteria;
import com.redstar.user.mapper.UserEntityMapper;


@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {
	private static final String TRACKING_TYPE = "User";

    @Autowired
    private UserEntityMapper userEntityMapper;

	@Override
	public User getById(Long userId) {
		if(userId == null){
			return null;
		}
		UserEntity entity = this.userEntityMapper.selectByPrimaryKey(userId);
		if (entity != null ) {
			return BeanUtils.copyProperties(entity, User.class);
		}

		return null;
	}

	@Override
	public Long create(User obj) {
		logger.debug("Creating User: {}", obj);

		UserEntity entity = BeanUtils.copyProperties(obj, UserEntity.class);
		//entity.setStatus(Status.NORMAL);
		//entity.setCreateBy(obj.getOperator());
		entity.setCreateTime(new Date());
		//entity.setUpdateBy(obj.getOperator());
		entity.setUpdateTime(new Date());

		this.userEntityMapper.insertSelective(entity);
		obj = this.getById(entity.getUserId());

		//obj.setUserId(entity.getUserId());

		logger.info("Created User: {}", obj);

		return obj.getUserId();
	}

	@Override
	public void update(User obj) {
		logger.debug("Updating User: {}", obj);

		UserEntity entity = BeanUtils.copyProperties(obj, UserEntity.class);
		//entity.setUpdateBy(obj.getOperator());
		entity.setUpdateTime(new Date());
		this.userEntityMapper.updateByPrimaryKeySelective(entity);

		logger.info("Updated User: {}", obj);
	}

	@Override
	public Long save(User obj) {
		logger.debug("Saving User: {}", obj);

		if (obj.getUserId() == null) {
			this.create(obj);
		} else {
			this.update(obj);
		}

		return obj.getUserId();
	}

	@Override
    public List<User> findByQuery(UserQuery query) {
        UserEntityExample example = new UserEntityExample();
        UserEntityExample.Criteria criteria = example.createCriteria();
        //criteria.andStatusEqualTo(Status.NORMAL);
        example.setOrderByClause("last_update DESC");

        List<UserEntity> list = this.userEntityMapper.selectByExample(example);

        return BeanUtils.copyListProperties(list, User.class);
    }

	@Override
	public Page<User> find(UserQuery query) {
		Page<User> page = new Page<User>(query);
		UserEntityExample example = new UserEntityExample();
		UserEntityExample.Criteria criteria = example.createCriteria();
		//criteria.andStatusEqualTo(Status.NORMAL);
		example.setOrderByClause("last_update DESC");
		page.setTotal(Long.valueOf(this.userEntityMapper.countByExample(example)).intValue());
		if (page.getTotal() > query.getOffset()) {
			List<UserEntity> list = this.userEntityMapper.selectByExampleWithRowbounds(example, this.toRowBounds(query));
			page.setItems(BeanUtils.copyListProperties(list, User.class));
		}
		
		return page;
	}
}
