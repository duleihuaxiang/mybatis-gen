package com.redstar.template.service;

import com.autogen.common.base.result.Page;
import com.autogen.common.base.result.Pagination;
import com.autogen.common.base.service.BaseService;
import com.redstar.template.dao.TemplateEntity;
import java.util.List;

public interface TemplateService extends BaseService {

    Template getById(Long templateId);

    Long create(Template obj);

    void update(Template obj);

    Long save(Template obj);

    List<Template> findByQuery(TemplateQuery query);

    Page<Template> find(TemplateQuery query);
    
    class TemplateQuery extends Pagination {
    }
    
    class Template extends TemplateEntity {
    }
}
