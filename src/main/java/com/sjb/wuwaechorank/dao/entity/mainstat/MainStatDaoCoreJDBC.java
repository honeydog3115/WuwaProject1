package com.sjb.wuwaechorank.dao.entity.mainstat;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MainStatDaoCoreJDBC implements MainStatDaoCore {
    private final JdbcTemplate jdbcTemplate;
    
    public MainStatDaoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}