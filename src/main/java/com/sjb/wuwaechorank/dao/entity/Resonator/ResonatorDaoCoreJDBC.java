package com.sjb.wuwaechorank.dao.entity.resonator;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ResonatorDaoCoreJDBC implements ResonatorDaoCore{
    private final JdbcTemplate jdbcTemplate;

    public ResonatorDaoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
