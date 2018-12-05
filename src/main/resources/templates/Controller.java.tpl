package #package.web#;


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
import io.swagger.annotations.ApiOperation;
import java.util.Map;

@RestController
@RequestMapping("#uriPath#")
public class #domain.className#Controller extends BaseController {

    @Autowired
    private #domain.className#Service #domain.objName#Service;

    @ApiOperation(value = "新增记录", notes = "新增记录，返回主键")
    @PostMapping("")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> create(@RequestBody final #domain.className#Req #domain.objName#Req) {
        // create
        // obj.setOperator(getSession().getUserId());
        #domain.className# obj =  new #domain.className#();
        BeanUtils.copyProperties(#domain.objName#Req,obj);
        Long id = #domain.objName#Service.create(obj);

        return resultMap(ResultCode.OK, "#primaryKeyField#", id);
    }

    @ApiOperation(value = "修改单条记录", notes = "根据主键修改")
    @PutMapping("/{id}")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> update(@PathVariable("id") final Long id, @RequestBody #domain.className#Req #domain.objName#Req) {
        // obj.setOperator(getSession().getUserId());
        #domain.className# obj =  new #domain.className#();
        BeanUtils.copyProperties(#domain.objName#Req,obj);
        obj.set#domain.className#Id(id);
        #domain.objName#Service.update(obj);

        return resultMap(ResultCode.OK);
    }

    @ApiOperation(value = "删除单条记录", notes = "根据主键删除")
    @DeleteMapping("/{id}")
     @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> delete(@PathVariable("id") final Long id) {
        #domain.className#Req obj = new #domain.className#Req();
        // obj.setOperator(getSession().getUserId());
        obj.setDeleted(1);
        update(id, obj);

        return resultMap(ResultCode.OK);
    }

    @ApiOperation(value = "get查询单条记录", notes = "根据主键查询")
    @GetMapping("/{id}")
    public #domain.className# get(@PathVariable("id") final Long id) {
        #domain.className# obj = #domain.objName#Service.getById(id);
        return obj;
    }
    @ApiOperation(value = "get分页查询", notes = "支持分页条件和业务条件")
    @GetMapping("")
    public Result<Page<#domain.className#>> findByQueryWithPage(
        @RequestParam(value = "offset", required = false) Integer offset,
        @RequestParam(value = "limit", required = false) Integer limit,
        @RequestParam(value = "page", required = false) Integer pageNum
    ) {
        //-- 设置分页条件
        #domain.className#Query query = new #domain.className#Query();
    	query.setOffset(offset);
    	query.setLimit(limit);
    	query.setPage(pageNum);
        //-- 设置查询条件
    	#domain.className# #domain.objName# = new #domain.className#();
    	Page<#domain.className#> page = #domain.objName#Service.findWithPage(query,#domain.objName#);
        return new Result<Page<#domain.className#>>(ResultCode.OK, page);
    }

    @ApiOperation(value = "post分页查询", notes = "支持分页条件和业务条件")
    @PostMapping("/list")
    public Result<Page<#domain.className#>> findWithPage(
       @RequestBody final #domain.className#Req #domain.objName#Req
    ) {
        //-- 设置分页条件 在 #domain.objName#Req 增加属性offset,limit,pageNum
        #domain.className#Query query = new #domain.className#Query();
    	query.setOffset(#domain.objName#Req.getOffset());
    	query.setLimit(#domain.objName#Req.getLimit());
    	query.setPage(#domain.objName#Req.getPageNum());
    	//-- 设置查询条件
    	#domain.className# #domain.objName# = new #domain.className#();

    	Page<#domain.className#> page = #domain.objName#Service.findWithPage(query,#domain.objName#);
        return new Result<Page<#domain.className#>>(ResultCode.OK, page);
    }

}
