package com.sjb.wuwaechorank.dao.crud.sqlgenerator;

public interface SqlParamBuilder {
    Object[] insert(Object entity);
    Object[] update(Object entity, Object primaryKey);
}