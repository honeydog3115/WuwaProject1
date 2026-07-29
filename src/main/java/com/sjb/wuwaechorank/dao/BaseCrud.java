package com.sjb.wuwaechorank.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.entity.Entity;

public class BaseCrud {
    private final JdbcTemplate jdbcTemplate;
    SqlBuilder sqlBuilder;
    SqlParameterBuilder sqlParameterBuilder;

    public BaseCrud(JdbcTemplate jdbcTemplate, SqlBuilder sqlBuilder, SqlParameterBuilder sqlParameterBuilder){
        this.jdbcTemplate = jdbcTemplate;
        this.sqlBuilder = sqlBuilder;
        this.sqlParameterBuilder = sqlParameterBuilder;
    }

    public <T extends Entity> void add(T entity){
        this.jdbcTemplate.update(sqlBuilder.insert(entity), sqlParameterBuilder.insert(entity));
    }

    public void init(){
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0;");
        this.jdbcTemplate.execute("TRUNCATE TABLE attribute;");
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1;");
    }
}
