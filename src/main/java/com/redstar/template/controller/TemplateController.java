package com.redstar.template.controller;

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

import com.redstar.template.vo.req.TemplateReq;

import com.redstar.template.service.TemplateService;
import com.redstar.template.service.TemplateService.Template;
import com.redstar.template.service.TemplateService.TemplateQuery;
import java.util.HashMap;


@RestController
@RequestMapping("/template")
public class TemplateController extends BaseController {

    @Autowired
    private TemplateService templateService;
    
    @PostMapping("")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> create(@RequestBody final TemplateReq brand) {
        // create
        // obj.setOperator(getSession().getUserId());
        Template obj =  new Template();
        BeanUtils.copyProperties(brand,obj);
        Long id = templateService.create(obj);

        return resultMap(ResultCode.OK, "templateId", id);
    }

    @PutMapping("/{id}")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> update(@PathVariable("id") final Long id, @RequestBody TemplateReq brand) {
        // obj.setOperator(getSession().getUserId());
        Template obj =  new Template();
        BeanUtils.copyProperties(brand,obj);
        obj.setTemplateId(id);
        templateService.update(obj);

        return resultMap(ResultCode.OK);
    }

    @DeleteMapping("/{id}")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> delete(@PathVariable("id") final Long id) {
        TemplateReq obj = new TemplateReq();
        // obj.setOperator(getSession().getUserId());
        // obj.setStatus(Status.DELETED);
        update(id, obj);

        return resultMap(ResultCode.OK);
    }


    @GetMapping("/{id}")
    public Template get(@PathVariable("id") final Long id) {
        Template obj = templateService.getById(id);
        return obj;
    }

    @GetMapping("")
    public Result<Page<Template>> find(
        @RequestParam(value = "offset", required = false) Integer offset,
        @RequestParam(value = "limit", required = false) Integer limit,
        @RequestParam(value = "page", required = false) Integer pageNum
    ) {
        TemplateQuery query = new TemplateQuery();
    	query.setOffset(offset);
    	query.setLimit(limit);
    	query.setPage(pageNum);

    	Page<Template> page = templateService.find(query);
        return new Result<Page<Template>>(ResultCode.OK, page);
    }

}
