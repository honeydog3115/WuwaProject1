package com.sjb.wuwaechorank.dao.entity.echo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EchoDaoCoreJDBC implements EchoDaoCore {
    private final JdbcTemplate jdbcTemplate;

    public EchoDaoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}