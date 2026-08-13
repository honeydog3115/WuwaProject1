package com.sjb.wuwaechorank.dao.entity.resonatorecho;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ResonatorEchoDaoCoreJDBC implements ResonatorEchoDaoCore {
    private final JdbcTemplate jdbcTemplate;
    
    public ResonatorEchoDaoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
