package com.redstar.templateproperty.controller;

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

import com.redstar.templateproperty.vo.req.TemplatePropertyReq;

import com.redstar.templateproperty.service.TemplatePropertyService;
import com.redstar.templateproperty.service.TemplatePropertyService.TemplateProperty;
import com.redstar.templateproperty.service.TemplatePropertyService.TemplatePropertyQuery;
import java.util.HashMap;


@RestController
@RequestMapping("/templateProperty")
public class TemplatePropertyController extends BaseController {

    @Autowired
    private TemplatePropertyService templatePropertyService;
    
    @PostMapping("")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> create(@RequestBody final TemplatePropertyReq brand) {
        // create
        // obj.setOperator(getSession().getUserId());
        TemplateProperty obj =  new TemplateProperty();
        BeanUtils.copyProperties(brand,obj);
        Long id = templatePropertyService.create(obj);

        return resultMap(ResultCode.OK, "templatePropertyId", id);
    }

    @PutMapping("/{id}")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> update(@PathVariable("id") final Long id, @RequestBody TemplatePropertyReq brand) {
        // obj.setOperator(getSession().getUserId());
        TemplateProperty obj =  new TemplateProperty();
        BeanUtils.copyProperties(brand,obj);
        obj.setTemplatePropertyId(id);
        templatePropertyService.update(obj);

        return resultMap(ResultCode.OK);
    }

    @DeleteMapping("/{id}")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> delete(@PathVariable("id") final Long id) {
        TemplatePropertyReq obj = new TemplatePropertyReq();
        // obj.setOperator(getSession().getUserId());
        // obj.setStatus(Status.DELETED);
        update(id, obj);

        return resultMap(ResultCode.OK);
    }


    @GetMapping("/{id}")
    public TemplateProperty get(@PathVariable("id") final Long id) {
        TemplateProperty obj = templatePropertyService.getById(id);
        return obj;
    }

    @GetMapping("")
    public Result<Page<TemplateProperty>> find(
        @RequestParam(value = "offset", required = false) Integer offset,
        @RequestParam(value = "limit", required = false) Integer limit,
        @RequestParam(value = "page", required = false) Integer pageNum
    ) {
        TemplatePropertyQuery query = new TemplatePropertyQuery();
    	query.setOffset(offset);
    	query.setLimit(limit);
    	query.setPage(pageNum);

    	Page<TemplateProperty> page = templatePropertyService.find(query);
        return new Result<Page<TemplateProperty>>(ResultCode.OK, page);
    }

}
