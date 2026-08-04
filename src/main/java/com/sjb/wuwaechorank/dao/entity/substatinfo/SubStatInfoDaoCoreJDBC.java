package com.sjb.wuwaechorank.dao.entity.substatinfo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SubStatInfoDaoCoreJDBC implements SubStatInfoDaoCore {
    private final JdbcTemplate jdbcTemplate;
    public SubStatInfoDaoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    
}