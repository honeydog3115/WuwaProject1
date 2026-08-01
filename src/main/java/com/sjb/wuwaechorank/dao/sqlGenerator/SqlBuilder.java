package com.sjb.wuwaechorank.dao.sqlGenerator;

public interface SqlBuilder {
    String insert(Class<?> clazz);
    String select(Class<?> clazz);
    String selectAll(Class<?> clazz);
    String delete(Class<?> clazz);
    String count(Class<?> clazz);
    String update(Class<?> clazz);
}
