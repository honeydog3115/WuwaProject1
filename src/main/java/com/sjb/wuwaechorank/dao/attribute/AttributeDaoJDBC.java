package com.sjb.wuwaechorank.dao.attribute;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.dao.BaseDaoJDBC;
import com.sjb.wuwaechorank.entity.Attribute;

@Repository
public class AttributeDaoJDBC extends BaseDaoJDBC<Attribute, Integer> implements AttributeDao { 

    public AttributeDaoJDBC(JdbcTemplate jdbcTemplate){
        super(jdbcTemplate);
    }
}

