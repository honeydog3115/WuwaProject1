package com.sjb.wuwaechorank.dao.entity.weapon;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WeaponDaoCoreJDBC implements WeaponDaoCore {
    private final JdbcTemplate jdbcTemplate;

    public WeaponDaoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
