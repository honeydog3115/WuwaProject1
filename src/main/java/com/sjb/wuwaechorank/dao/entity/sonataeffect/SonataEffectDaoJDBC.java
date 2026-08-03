package com.sjb.wuwaechorank.dao.entity.sonataeffect;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SonataEffectDaoJDBC implements SonataEffectDaoCore {
    private final JdbcTemplate jdbcTemplate;

    public SonataEffectDaoJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}