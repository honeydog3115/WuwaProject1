package com.sjb.wuwaechorank.dao.crud;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sjb.wuwaechorank.dao.crud.sqlgenerator.SqlBuilder;
import com.sjb.wuwaechorank.dao.crud.sqlgenerator.SqlParamBuilder;

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
    /** 
     * @param entity
     */
    @Override
    public void add(T entity) {
        this.jdbcTemplate.update(sqlBuilder.insert(clazz), sqlParamBuilder.insert(entity));
    }
    /** 
     * @param primaryKey
     * @return T
     */
    @Override
    public T get(Object primaryKey) {
        return this.jdbcTemplate.queryForObject(sqlBuilder.select(clazz), this.rowMapper, primaryKey);
    }
    /** 
     * @return List<T>
     */
    @Override
    public List<T> getAll() {
        return this.jdbcTemplate.query(sqlBuilder.selectAll(clazz), this.rowMapper);
    }
    /** 
     * @param primaryKey
     */
    @Override
    public void delete(Object primaryKey) {
        this.jdbcTemplate.update(sqlBuilder.delete(clazz), primaryKey);
    }
    /** 
     * @return int
     */
    @Override
    public int getCount() {
        return this.jdbcTemplate.queryForObject(sqlBuilder.count(clazz), Integer.class);
    }
    /** 
     * @param primaryKey
     * @param entity
     */
    @Override
    public void update(Object primaryKey, T entity) {
        this.jdbcTemplate.update(sqlBuilder.update(clazz), sqlParamBuilder.update(entity, primaryKey));
    }
}
