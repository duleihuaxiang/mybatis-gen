package #package.voReq#;


import java.io.Serializable;
import #package.dao#.dao.#domain.className#Entity;

/**
 * #domain.className# 请求参数模板
 *
 * =========================================
 * <p>
 * Contributors :
 * Mybatis auto generator
 */
public class #domain.className#Req extends #domain.className#Entity implements Serializable {

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
