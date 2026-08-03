package com.sjb.wuwaechorank.dao.crud.sqlgenerator;

public interface SqlGenerator {
    SqlAndParam insert(Object entity);
    SqlAndParam update(Object entity, Object primaryKey);
    String select(Class<?> clazz);
    String selectAll(Class<?> clazz);
    String delete(Class<?> clazz);
    String getCount(Class<?> clazz);
}
