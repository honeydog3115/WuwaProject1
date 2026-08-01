package com.sjb.wuwaechorank.dao.attribute;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AttributeDaoCoreJDBC implements AttributeDaoCore {
    private final JdbcTemplate jdbcTemplate;
    public AttributeDaoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}

