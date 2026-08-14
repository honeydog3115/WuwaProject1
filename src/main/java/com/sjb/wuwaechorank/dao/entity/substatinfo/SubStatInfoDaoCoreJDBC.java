package com.sjb.wuwaechorank.dao.entity.substatinfo;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.entity.SubStatInfo;

@Repository
public class SubStatInfoDaoCoreJDBC implements SubStatInfoDaoCore {
    private final JdbcTemplate jdbcTemplate;
    // IN 구문에 파라미터를 넣으려면 NamedParameterJdbcTemplate이 필요
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private RowMapper<SubStatInfo> rowMapper = BeanPropertyRowMapper.newInstance(SubStatInfo.class);

    public SubStatInfoDaoCoreJDBC(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    @Override
    public List<SubStatInfo> getAllBySubStatId(int subStatId) {
        return this.jdbcTemplate.query("SELECT * FROM substatinfo WHERE substatid = ? ORDER BY value", rowMapper, subStatId);
    }

    @Override
    public List<SubStatInfo> getAllBySubStatIdIn(List<Integer> subStatIds) {
        if(subStatIds == null || subStatIds.isEmpty())
            return List.of();

        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("ids", subStatIds);
        
        return this.namedParameterJdbcTemplate.query("SELECT * FROM substatinfo WHERE substatid IN (:ids)", parameter, rowMapper);
    }
    
    @Override
    public List<SubStatInfo> getAllByEchoSubStatInfos(List<Integer> echoSubStatInfoIds) {
        if(echoSubStatInfoIds == null || echoSubStatInfoIds.isEmpty())
            return List.of();
        
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("ids", echoSubStatInfoIds);
        
        return this.namedParameterJdbcTemplate.query(
            "SELECT s.id, s.substatid, s.value, s.chance " +
            "FROM echosubstatinfo e "+
            "INNER JOIN substatinfo s ON e.substatinfoid = s.id WHERE e.id IN (:ids)", parameter, rowMapper);
    }

}