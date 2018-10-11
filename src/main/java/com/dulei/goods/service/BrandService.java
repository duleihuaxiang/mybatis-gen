package com.dulei.goods.service;

import com.autogen.common.base.result.Page;
import com.autogen.common.base.result.Pagination;
import com.autogen.common.base.service.BaseService;
import com.dulei.goods.dao.BrandEntity;
import java.util.List;

public interface BrandService extends BaseService {

    Brand getById(Long brandId);

    Long create(Brand obj);

    void update(Brand obj);

    Long save(Brand obj);

    List<Brand> findByQuery(BrandQuery query);

    Page<Brand> find(BrandQuery query);
    
    class BrandQuery extends Pagination {
    }
    
    class Brand extends BrandEntity {
    }
}
