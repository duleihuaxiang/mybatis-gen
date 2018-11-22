package com.redstar.user.service;

import com.autogen.common.base.result.Page;
import com.autogen.common.base.result.Pagination;
import com.autogen.common.base.service.BaseService;
import com.redstar.user.dao.UserEntity;
import java.util.List;

public interface UserService extends BaseService {

    User getById(Long userId);

    Long create(User obj);

    void update(User obj);

    Long save(User obj);

    List<User> findByQuery(UserQuery query);

    Page<User> find(UserQuery query);
    
    class UserQuery extends Pagination {
    }
    
    class User extends UserEntity {
    }
}
