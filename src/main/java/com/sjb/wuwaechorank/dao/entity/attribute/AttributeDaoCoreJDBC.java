package com.sjb.wuwaechorank.dao.entity.attribute;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.entity.Attribute;

@Repository
public class AttributeDaoCoreJDBC implements AttributeDaoCore {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Attribute> rowMapper = BeanPropertyRowMapper.newInstance(Attribute.class);

    public AttributeDaoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    /** 
     * @param id
     * @return String
     */
    @Override
    public String getName(int id){
        return this.jdbcTemplate.queryForObject("SELECT name FROM attribute WHERE id = ?", rowMapper, id).getName();
    }
}

