package com.dulei.goods.controller;

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

import com.dulei.goods.vo.req.BrandReq;

import com.dulei.goods.service.BrandService;
import com.dulei.goods.service.BrandService.Brand;
import com.dulei.goods.service.BrandService.BrandQuery;
import java.util.HashMap;


@RestController
@RequestMapping("/brand")
public class BrandController extends BaseController {

    @Autowired
    private BrandService brandService;
    
    @PostMapping("")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> create(@RequestBody final BrandReq brand) {
        // create
        // obj.setOperator(getSession().getUserId());
        Brand obj =  new Brand();
        BeanUtils.copyProperties(brand,obj);
        Long id = brandService.create(obj);

        return resultMap(ResultCode.OK, "brandId", id);
    }

    @PutMapping("/{id}")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> update(@PathVariable("id") final Long id, @RequestBody BrandReq brand) {
        // obj.setOperator(getSession().getUserId());
        Brand obj =  new Brand();
        BeanUtils.copyProperties(brand,obj);
        obj.setBrandId(id);
        brandService.update(obj);

        return resultMap(ResultCode.OK);
    }

    @DeleteMapping("/{id}")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> delete(@PathVariable("id") final Long id) {
        BrandReq obj = new BrandReq();
        // obj.setOperator(getSession().getUserId());
        // obj.setStatus(Status.DELETED);
        update(id, obj);

        return resultMap(ResultCode.OK);
    }


    @GetMapping("/{id}")
    public Brand get(@PathVariable("id") final Long id) {
        Brand obj = brandService.getById(id);
        return obj;
    }

    @GetMapping("")
    public Result<Page<Brand>> find(
        @RequestParam(value = "offset", required = false) Integer offset,
        @RequestParam(value = "limit", required = false) Integer limit,
        @RequestParam(value = "page", required = false) Integer pageNum
    ) {
        BrandQuery query = new BrandQuery();
    	query.setOffset(offset);
    	query.setLimit(limit);
    	query.setPage(pageNum);

    	Page<Brand> page = brandService.find(query);
        return new Result<Page<Brand>>(ResultCode.OK, page);
    }

}
