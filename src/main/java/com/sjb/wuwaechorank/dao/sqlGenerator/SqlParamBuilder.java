package com.sjb.wuwaechorank.dao.sqlGenerator;

public interface SqlParamBuilder {
    Object[] insert(Object entity);
    Object[] update(Object entity, Object primaryKey);
}