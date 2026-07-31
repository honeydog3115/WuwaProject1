package com.sjb.wuwaechorank.dao;

import org.springframework.stereotype.Component;

@Component
public class BaseSqlGenerator implements SqlGenerator {
    SqlBuilder sqlBuilder;
    SqlParamBuilder sqlParamBuilder;

    public BaseSqlGenerator(SqlBuilder sqlBuilder, SqlParamBuilder sqlParamBuilder){
        this.sqlBuilder = sqlBuilder;
        this.sqlParamBuilder = sqlParamBuilder;
    }

    @Override
    public String select(Class<?> clazz) {
        return this.sqlBuilder.select(clazz);
    }
    @Override
    public String selectAll(Class<?> clazz) {
        return this.sqlBuilder.selectAll(clazz);
    }
    @Override
    public String delete(Class<?> clazz) {
        return this.sqlBuilder.delete(clazz);
    }
    @Override
    public SqlAndParam insert(Object entity) {
        SqlAndParam sqlAndParam = new SqlAndParam(this.sqlBuilder.insert(entity.getClass()), this.sqlParamBuilder.insert(entity));
        return sqlAndParam;
    }
    @Override
    public SqlAndParam update(Object entity, Object primaryKey) {
        SqlAndParam sqlAndParam = new SqlAndParam(this.sqlBuilder.update(entity.getClass()), this.sqlParamBuilder.update(entity, primaryKey));
        return sqlAndParam;
    }
    @Override
    public String getCount(Class<?> clazz) {
        return this.sqlBuilder.count(clazz);
    }
}
