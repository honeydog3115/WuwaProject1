package com.sjb.wuwaechorank.dao.entity.echosubstatinfo;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EchoSubStatInfoDaoCoreJDBC implements EchoSubStatInfoDaoCore{
    private final JdbcTemplate jdbcTemplate;

    public EchoSubStatInfoDaoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Integer> getIdsByResonatorEchoId(int resonatorEchoId) {
        return this.jdbcTemplate.queryForList("SELECT id FROM echosubstatinfo WHERE resonatorId = ?", Integer.class, resonatorEchoId);
    }
}
