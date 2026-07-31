package com.sjb.wuwaechorank.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseDaoJDBC<T, S> implements BaseDao<T, S> {
    protected final JdbcTemplate jdbcTemplate;
    private final SqlGenerator sqlGenerator;
    private final Class<T> clazz;
    private final BeanPropertyRowMapper<T> rowMapper;

    public BaseDaoJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        
        this.sqlGenerator = null;

        Type actualType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        @SuppressWarnings("unchecked")
        Class<T> tmpClass = (Class<T>) actualType;
        this.clazz = tmpClass;

        
        this.rowMapper = BeanPropertyRowMapper.newInstance(this.clazz);
    }

    @Override
    public void add(T entity) {

        this.jdbcTemplate.update(sqlBuilder.insert(clazz), sqlParamBuilder.insert(entity));
    }
    @Override
    public T get(S primaryKey) {
        return this.jdbcTemplate.queryForObject(sqlBuilder.select(clazz), this.rowMapper, primaryKey);
    }
    @Override
    public List<T> getAll() {
        return this.jdbcTemplate.query(sqlBuilder.selectAll(clazz), this.rowMapper);
    }
    @Override
    public void delete(S primaryKey) {
        this.jdbcTemplate.update(sqlBuilder.delete(clazz), primaryKey);
    }
    @Override
    public int getCount() {
        return this.jdbcTemplate.queryForObject(sqlBuilder.count(clazz), Integer.class);
    }
    @Override
    public void update(S primaryKey, T entity) {
        this.jdbcTemplate.update(sqlBuilder.update(clazz), sqlParamBuilder.update(entity, primaryKey));
    }
}
