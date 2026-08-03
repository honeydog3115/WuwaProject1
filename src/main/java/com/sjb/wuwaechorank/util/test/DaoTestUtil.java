package com.sjb.wuwaechorank.util.test;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DaoTestUtil {
    private final JdbcTemplate jdbcTemplate;

    public DaoTestUtil(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void initTable(String tableName){
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        this.jdbcTemplate.execute("TRUNCATE TABLE "+ tableName);
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }
}
