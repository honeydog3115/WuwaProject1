package com.sjb.wuwaechorank.dao.crud.sqlgenerator;

import java.util.Map;

public interface SqlParamBuilder {
    Map<String,Object> insert(Object entity);
    Object[] update(Object entity, Object primaryKey);
}