package com.chinaredstar.property.controller;

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

import com.chinaredstar.property.vo.req.PropertyReq;

import com.chinaredstar.property.service.PropertyService;
import com.chinaredstar.property.service.PropertyService.Property;
import com.chinaredstar.property.service.PropertyService.PropertyQuery;
import java.util.HashMap;


@RestController
@RequestMapping("/property")
public class PropertyController extends BaseController {

    @Autowired
    private PropertyService propertyService;
    
    @PostMapping("")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> create(@RequestBody final PropertyReq propertyReq) {
        // create
        // obj.setOperator(getSession().getUserId());
        Property obj =  new Property();
        BeanUtils.copyProperties(propertyReq,obj);
        Long id = propertyService.create(obj);

        return resultMap(ResultCode.OK, "propertyId", id);
    }

    @PutMapping("/{id}")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> update(@PathVariable("id") final Long id, @RequestBody PropertyReq propertyReq) {
        // obj.setOperator(getSession().getUserId());
        Property obj =  new Property();
        BeanUtils.copyProperties(propertyReq,obj);
        obj.setPropertyId(id);
        propertyService.update(obj);

        return resultMap(ResultCode.OK);
    }

    @DeleteMapping("/{id}")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> delete(@PathVariable("id") final Long id) {
        PropertyReq obj = new PropertyReq();
        // obj.setOperator(getSession().getUserId());
        // obj.setStatus(Status.DELETED);
        update(id, obj);

        return resultMap(ResultCode.OK);
    }


    @GetMapping("/{id}")
    public Property get(@PathVariable("id") final Long id) {
        Property obj = propertyService.getById(id);
        return obj;
    }

    @GetMapping("")
    public Result<Page<Property>> findByQueryWithPage(
        @RequestParam(value = "offset", required = false) Integer offset,
        @RequestParam(value = "limit", required = false) Integer limit,
        @RequestParam(value = "page", required = false) Integer pageNum
    ) {
        //-- 设置分页条件
        PropertyQuery query = new PropertyQuery();
    	query.setOffset(offset);
    	query.setLimit(limit);
    	query.setPage(pageNum);
        //-- 设置查询条件
    	Property property = new Property();
    	Page<Property> page = propertyService.findWithPage(query,property);
        return new Result<Page<Property>>(ResultCode.OK, page);
    }


    @PostMapping("/list")
    public Result<Page<Property>> find(
       @RequestBody final PropertyReq propertyReq
    ) {
        //-- 设置分页条件 在 propertyReq 增加属性offset,limit,pageNum
        PropertyQuery query = new PropertyQuery();
    	query.setOffset(propertyReq.getOffset());
    	query.setLimit(propertyReq.getLimit());
    	query.setPage(propertyReq.getPageNum());
    	//-- 设置查询条件
    	Property property = new Property();

    	Page<Property> page = propertyService.findWithPage(query,property);
        return new Result<Page<Property>>(ResultCode.OK, page);
    }

}
