package com.sjb.wuwaechorank.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseDaoImpl implements BaseDao {
    protected final JdbcTemplate jdbcTemplate;
    protected abstract String tableName();
    
    public BaseDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getCount(){
        return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName(), Integer.class);
    }

    public void init(){
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        this.jdbcTemplate.execute("TRUNCATE TABLE " + tableName());
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }
}
