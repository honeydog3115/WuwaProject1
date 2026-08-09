package com.sjb.wuwaechorank.dao.entity.validstat;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ValidStatDaoCoreJDBC implements ValidStatDaoCore {
    private final JdbcTemplate jdbcTemplate;

    public ValidStatDaoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
