package com.sjb.wuwaechorank.dao;

public interface SqlParamBuilder {
    Object[] insert(Object entity);
    Object[] update(Object entity, Object primaryKey);
}