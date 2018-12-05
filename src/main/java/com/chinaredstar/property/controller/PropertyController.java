package com.chinaredstar.property.controller;


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
import io.swagger.annotations.ApiOperation;
import java.util.Map;

@RestController
@RequestMapping("/property")
public class PropertyController extends BaseController {

    @Autowired
    private PropertyService propertyService;

    @ApiOperation(value = "新增记录", notes = "新增记录，返回主键")
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

    @ApiOperation(value = "修改单条记录", notes = "根据主键修改")
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

    @ApiOperation(value = "删除单条记录", notes = "根据主键删除")
    @DeleteMapping("/{id}")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> delete(@PathVariable("id") final Long id) {
        PropertyReq obj = new PropertyReq();
        // obj.setOperator(getSession().getUserId());
        obj.setDeleted(1);
        update(id, obj);

        return resultMap(ResultCode.OK);
    }

    @ApiOperation(value = "get查询单条记录", notes = "根据主键查询")
    @GetMapping("/{id}")
    public Property get(@PathVariable("id") final Long id) {
        Property obj = propertyService.getById(id);
        return obj;
    }
    @ApiOperation(value = "get分页查询", notes = "支持分页条件和业务条件")
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

    @ApiOperation(value = "post分页查询", notes = "支持分页条件和业务条件")
    @PostMapping("/list")
    public Result<Page<Property>> findWithPage(
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
