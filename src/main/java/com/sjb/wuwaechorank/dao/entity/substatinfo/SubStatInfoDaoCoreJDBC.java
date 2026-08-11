package com.sjb.wuwaechorank.dao.entity.substatinfo;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.entity.SubStatInfo;

@Repository
public class SubStatInfoDaoCoreJDBC implements SubStatInfoDaoCore {
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<SubStatInfo> rowMapper = BeanPropertyRowMapper.newInstance(SubStatInfo.class);

    public SubStatInfoDaoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<SubStatInfo> getAllBySubStatId(int subStatId) {
        return this.jdbcTemplate.query("SELECT * FROM substatinfo WHERE ORDER BY value", rowMapper, subStatId);
    }
}