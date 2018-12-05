package com.chinaredstar.property.vo.req;


import java.io.Serializable;
import com.chinaredstar.property.dao.PropertyEntity;

/**
 * Property 请求参数模板
 *
 * =========================================
 * <p>
 * Contributors :
 * Mybatis auto generator
 */
public class PropertyReq extends PropertyEntity implements Serializable {

     private Integer offset = 0;
     private Integer limit = 16;
     private Integer pageNum = 0;


     public Integer getOffset() {
         return offset;
     }

     public void setOffset(Integer offset) {
         this.offset = offset;
     }

     public Integer getLimit() {
         return limit;
     }

     public void setLimit(Integer limit) {
         this.limit = limit;
     }

     public Integer getPageNum() {
         return pageNum;
     }

     public void setPageNum(Integer pageNum) {
         this.pageNum = pageNum;
     }
}
