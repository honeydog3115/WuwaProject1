package com.sjb.wuwaechorank.dao.entity.resonatorecho;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ResonatorEchoCoreJDBC implements ResonatorEchoCore {
    private final JdbcTemplate jdbcTemplate;
    
    public ResonatorEchoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
