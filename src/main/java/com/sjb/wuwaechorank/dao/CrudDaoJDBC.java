package com.sjb.wuwaechorank.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.dao.sqlGenerator.SqlBuilder;
import com.sjb.wuwaechorank.dao.sqlGenerator.SqlParamBuilder;

public class CrudDaoJDBC<T> implements CrudDao<T> {
    protected final JdbcTemplate jdbcTemplate;

    private final SqlBuilder sqlBuilder;
    private final SqlParamBuilder sqlParamBuilder;
    private final Class<T> clazz;
    private final BeanPropertyRowMapper<T> rowMapper;

    public CrudDaoJDBC(JdbcTemplate jdbcTemplate, SqlBuilder sqlBuilder, SqlParamBuilder sqlParamBuilder, Class<T> entityClass){
        this.jdbcTemplate = jdbcTemplate;
        this.sqlBuilder = sqlBuilder;
        this.sqlParamBuilder = sqlParamBuilder;
        this.clazz = entityClass;
        this.rowMapper = BeanPropertyRowMapper.newInstance(this.clazz);
    }
    @Override
    public void add(T entity) {
        this.jdbcTemplate.update(sqlBuilder.insert(clazz), sqlParamBuilder.insert(entity));
    }
    @Override
    public T get(Object primaryKey) {
        return this.jdbcTemplate.queryForObject(sqlBuilder.select(clazz), this.rowMapper, primaryKey);
    }
    @Override
    public List<T> getAll() {
        return this.jdbcTemplate.query(sqlBuilder.selectAll(clazz), this.rowMapper);
    }
    @Override
    public void delete(Object primaryKey) {
        this.jdbcTemplate.update(sqlBuilder.delete(clazz), primaryKey);
    }
    @Override
    public int getCount() {
        return this.jdbcTemplate.queryForObject(sqlBuilder.count(clazz), Integer.class);
    }
    @Override
    public void update(Object primaryKey, T entity) {
        this.jdbcTemplate.update(sqlBuilder.update(clazz), sqlParamBuilder.update(entity, primaryKey));
    }
}
