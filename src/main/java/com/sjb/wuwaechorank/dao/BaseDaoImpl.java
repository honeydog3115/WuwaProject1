package com.sjb.wuwaechorank.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseDaoImpl implements BaseDao {
    protected final JdbcTemplate jdbcTemplate;
    
    public BaseDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getCount(){
        return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName(), Integer.class);
    }
}
