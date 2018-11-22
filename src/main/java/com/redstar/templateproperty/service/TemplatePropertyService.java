package com.redstar.templateproperty.service;

import com.autogen.common.base.result.Page;
import com.autogen.common.base.result.Pagination;
import com.autogen.common.base.service.BaseService;
import com.redstar.templateproperty.dao.TemplatePropertyEntity;
import java.util.List;

public interface TemplatePropertyService extends BaseService {

    TemplateProperty getById(Long templatePropertyId);

    Long create(TemplateProperty obj);

    void update(TemplateProperty obj);

    Long save(TemplateProperty obj);

    List<TemplateProperty> findByQuery(TemplatePropertyQuery query);

    Page<TemplateProperty> find(TemplatePropertyQuery query);
    
    class TemplatePropertyQuery extends Pagination {
    }
    
    class TemplateProperty extends TemplatePropertyEntity {
    }
}
