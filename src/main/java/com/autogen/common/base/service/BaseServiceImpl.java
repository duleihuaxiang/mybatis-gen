package com.autogen.common.base.service;


import com.autogen.common.base.result.Pagination;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BaseServiceImpl implements BaseService{
	
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init() throws Exception {
    }

    @Override
    public void destroy() {
    }

    protected RowBounds toRowBounds(Pagination pagination) {
        return new RowBounds(pagination.getOffset(), pagination.getLimit());
    }
}
