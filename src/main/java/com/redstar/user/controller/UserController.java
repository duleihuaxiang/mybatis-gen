package com.redstar.user.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.autogen.common.base.controller.BaseController;
import com.autogen.common.base.result.Page;
import com.autogen.common.base.result.Result;
import com.autogen.common.base.result.ResultCode;
import com.autogen.common.utils.BeanUtils;

import com.redstar.user.vo.req.UserReq;

import com.redstar.user.service.UserService;
import com.redstar.user.service.UserService.User;
import com.redstar.user.service.UserService.UserQuery;
import java.util.HashMap;


@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    
    @PostMapping("")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> create(@RequestBody final UserReq brand) {
        // create
        // obj.setOperator(getSession().getUserId());
        User obj =  new User();
        BeanUtils.copyProperties(brand,obj);
        Long id = userService.create(obj);

        return resultMap(ResultCode.OK, "userId", id);
    }

    @PutMapping("/{id}")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> update(@PathVariable("id") final Long id, @RequestBody UserReq brand) {
        // obj.setOperator(getSession().getUserId());
        User obj =  new User();
        BeanUtils.copyProperties(brand,obj);
        obj.setUserId(id);
        userService.update(obj);

        return resultMap(ResultCode.OK);
    }

    @DeleteMapping("/{id}")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> delete(@PathVariable("id") final Long id) {
        UserReq obj = new UserReq();
        // obj.setOperator(getSession().getUserId());
        // obj.setStatus(Status.DELETED);
        update(id, obj);

        return resultMap(ResultCode.OK);
    }


    @GetMapping("/{id}")
    public User get(@PathVariable("id") final Long id) {
        User obj = userService.getById(id);
        return obj;
    }

    @GetMapping("")
    public Result<Page<User>> find(
        @RequestParam(value = "offset", required = false) Integer offset,
        @RequestParam(value = "limit", required = false) Integer limit,
        @RequestParam(value = "page", required = false) Integer pageNum
    ) {
        UserQuery query = new UserQuery();
    	query.setOffset(offset);
    	query.setLimit(limit);
    	query.setPage(pageNum);

    	Page<User> page = userService.find(query);
        return new Result<Page<User>>(ResultCode.OK, page);
    }

}
