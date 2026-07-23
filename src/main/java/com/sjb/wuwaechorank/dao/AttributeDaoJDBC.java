package com.sjb.wuwaechorank.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.dto.Attribute;

@Repository
public class AttributeDaoJDBC implements AttributeDao {
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Attribute> attributRowMapper = new RowMapper<Attribute>() {
        public Attribute mapRow(ResultSet rs, int rowNum) throws SQLException {
            Attribute attribute = new Attribute();
            attribute.setId(rs.getInt("id"));
            attribute.setName(rs.getString("name"));
            attribute.setImagePath(rs.getString("imagepath"));
            return attribute;
        };
    };

    void Attribute(){

    }

    @Override
    public void add() {
        this.jdbcTemplate.update("INSERT INTO attribute(name, imagepath) VALUES (?, ?)", "용융", "path");
    }

    @Override
    public Attribute get() {
        return this.jdbcTemplate.queryForObject("SELECT * FROM attribute", this.attributRowMapper);
    }

    @Override
    public void delete() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Attribute> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM attribute", this.attributRowMapper);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }
}

