package #package.service#;

import com.autogen.common.base.result.Page;
import com.autogen.common.base.result.Pagination;
import com.autogen.common.base.service.BaseService;
import #package.dao#.dao.#domain.className#Entity;
import java.util.List;

public interface #domain.className#Service extends BaseService {

    #domain.className# getById(Long #primaryKeyField#);

    Long create(#domain.className# obj);

    void update(#domain.className# obj);

    Long save(#domain.className# obj);

    List<#domain.className#> findByQuery(#domain.className#Query query,#domain.className# #domain.objName#);

    Page<#domain.className#> findWithPage(#domain.className#Query query,#domain.className# #domain.objName#);
    
    class #domain.className#Query extends Pagination {
    }
    
    class #domain.className# extends #domain.className#Entity {
    }
}
