package com.sjb.wuwaechorank.dao.entity.substat;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.dto.SubStatDetailDto;
import com.sjb.wuwaechorank.entity.SubStat;

@Repository
public class SubStatDaoCoreJDBC implements SubStatDaoCore {
    private JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private RowMapper<SubStat> rowMapper = BeanPropertyRowMapper.newInstance(SubStat.class); 
    RowMapper<SubStatDetailDto> detailRowMapper = DataClassRowMapper.newInstance(SubStatDetailDto.class);

    public SubStatDaoCoreJDBC(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<SubStat> getByIds(List<Integer> ids) {
        if(ids == null || ids.isEmpty())
            return List.of();

        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("ids", ids);
        
        return this.namedParameterJdbcTemplate.query("SELECT * FROM substat WHERE id IN (:ids)", parameter, rowMapper);
    }

    @Override
    public List<SubStatDetailDto> getSubStatDetailsBysubStatInfoIds(List<Integer> subStatInfoIds) {
        if(subStatInfoIds == null || subStatInfoIds.isEmpty())
            return List.of();

        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("ids", subStatInfoIds);

        return this.namedParameterJdbcTemplate.query(
            "SELECT s.name as subStatName, si.value as subStatValue, si.chance as subStatChance " +
            "FROM substat s " + 
            "INNER JOIN substatinfo si ON s.id = si.substatid "+ 
            "WHERE si.id IN (:ids)", parameter, detailRowMapper);
    }
    
}
