package com.sjb.wuwaechorank.dao.entity.echo;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.entity.Echo;

@Repository
public class EchoDaoCoreJDBC implements EchoDaoCore {
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<Echo> rowMapper = BeanPropertyRowMapper.newInstance(Echo.class);

    public EchoDaoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Echo> getAllEchoBySonataEffect(int id) {
        return this.jdbcTemplate.query("SELECT * FROM echo WHERE sonataEffectId = ?", rowMapper, id);
    }
    
}