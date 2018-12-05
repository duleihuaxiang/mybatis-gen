package com.chinaredstar.property.service;

import com.autogen.common.base.result.Page;
import com.autogen.common.base.result.Pagination;
import com.autogen.common.base.service.BaseService;
import com.chinaredstar.property.dao.PropertyEntity;
import java.util.List;

public interface PropertyService extends BaseService {

    Property getById(Long propertyId);

    Long create(Property obj);

    void update(Property obj);

    Long save(Property obj);

    List<Property> findByQuery(PropertyQuery query,Property property);

    Page<Property> findWithPage(PropertyQuery query,Property property);
    
    class PropertyQuery extends Pagination {
    }
    
    class Property extends PropertyEntity {
    }
}
