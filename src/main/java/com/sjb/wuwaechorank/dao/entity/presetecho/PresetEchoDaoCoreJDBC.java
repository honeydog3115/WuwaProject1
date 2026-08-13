package com.sjb.wuwaechorank.dao.entity.presetecho;


import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.entity.PresetEcho;

@Repository
public class PresetEchoDaoCoreJDBC implements PresetEchoDaoCore{
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<PresetEcho> rowMapper = BeanPropertyRowMapper.newInstance(PresetEcho.class);

    public PresetEchoDaoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PresetEcho> getAllByPresetId(int presetId){
        return this.jdbcTemplate.query("SELECT * FROM presetecho WHERE presetid = ?", rowMapper, presetId);
    }
}
