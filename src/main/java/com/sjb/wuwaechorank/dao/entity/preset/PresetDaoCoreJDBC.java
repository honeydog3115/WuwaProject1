package com.sjb.wuwaechorank.dao.entity.preset;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.entity.Preset;

@Repository
public class PresetDaoCoreJDBC implements PresetDaoCore {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Preset> rowMapper = BeanPropertyRowMapper.newInstance(Preset.class);
    
    public PresetDaoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Preset> getAllByUserId(int userId){
        return this.jdbcTemplate.query("SELECT id, name, bookmark FROM preset WHERE userId = ? ORDER BY id", rowMapper, userId);
    }
}
