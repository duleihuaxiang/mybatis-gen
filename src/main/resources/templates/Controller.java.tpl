package #package.web#;

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

import #package.dao#.vo.req.#domain.className#Req;

import #package.service#.#domain.className#Service;
import #package.service#.#domain.className#Service.#domain.className#;
import #package.service#.#domain.className#Service.#domain.className#Query;
import java.util.HashMap;


@RestController
@RequestMapping("#uriPath#")
public class #domain.className#Controller extends BaseController {

    @Autowired
    private #domain.className#Service #domain.objName#Service;
    
    @PostMapping("")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> create(@RequestBody final #domain.className#Req brand) {
        // create
        // obj.setOperator(getSession().getUserId());
        #domain.className# obj =  new #domain.className#();
        BeanUtils.copyProperties(brand,obj);
        Long id = #domain.objName#Service.create(obj);

        return resultMap(ResultCode.OK, "#primaryKeyField#", id);
    }

    @PutMapping("/{id}")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> update(@PathVariable("id") final Long id, @RequestBody #domain.className#Req brand) {
        // obj.setOperator(getSession().getUserId());
        #domain.className# obj =  new #domain.className#();
        BeanUtils.copyProperties(brand,obj);
        obj.set#domain.className#Id(id);
        #domain.objName#Service.update(obj);

        return resultMap(ResultCode.OK);
    }

    @DeleteMapping("/{id}")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> delete(@PathVariable("id") final Long id) {
        #domain.className#Req obj = new #domain.className#Req();
        // obj.setOperator(getSession().getUserId());
        // obj.setStatus(Status.DELETED);
        update(id, obj);

        return resultMap(ResultCode.OK);
    }


    @GetMapping("/{id}")
    public #domain.className# get(@PathVariable("id") final Long id) {
        #domain.className# obj = #domain.objName#Service.getById(id);
        return obj;
    }

    @GetMapping("")
    public Result<Page<#domain.className#>> find(
        @RequestParam(value = "offset", required = false) Integer offset,
        @RequestParam(value = "limit", required = false) Integer limit,
        @RequestParam(value = "page", required = false) Integer pageNum
    ) {
        #domain.className#Query query = new #domain.className#Query();
    	query.setOffset(offset);
    	query.setLimit(limit);
    	query.setPage(pageNum);

    	Page<#domain.className#> page = #domain.objName#Service.find(query);
        return new Result<Page<#domain.className#>>(ResultCode.OK, page);
    }

}
