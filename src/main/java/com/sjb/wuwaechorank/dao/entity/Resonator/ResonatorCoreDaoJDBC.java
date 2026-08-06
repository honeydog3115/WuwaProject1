package com.sjb.wuwaechorank.dao.entity.Resonator;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ResonatorCoreDaoJDBC implements ResonatorCoreDao{
    private final JdbcTemplate jdbcTemplate;

    public ResonatorCoreDaoJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
