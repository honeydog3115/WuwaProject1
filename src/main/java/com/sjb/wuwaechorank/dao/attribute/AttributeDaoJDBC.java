package com.sjb.wuwaechorank.dao.attribute;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.dto.Attribute;

@Repository
public class AttributeDaoJDBC implements AttributeDao {
    private final JdbcTemplate jdbcTemplate;
    
    public AttributeDaoJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Attribute> attributeRowMapper =
        (rs, rowNum) -> {
            Attribute attribute = new Attribute();
            attribute.setId(rs.getInt("id"));
            attribute.setName(rs.getString("name"));
            attribute.setImagePath(rs.getString("imagepath"));
            return attribute;
        };

    @Override
    public void add(Attribute attribute) {
        this.jdbcTemplate.update("INSERT INTO attribute(name, imagepath) VALUES (?, ?)", attribute.getName(), attribute.getImagePath());
    }

    @Override
    public Attribute get(String name) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM attribute WHERE name = ?", this.attributeRowMapper, name);
    }

    @Override
    public void delete(String name) {
        this.jdbcTemplate.update("DELETE FROM attribute WHERE name = ?", name);
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM attribute");
    }

    @Override
    public List<Attribute> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM attribute", this.attributeRowMapper);
    }

    @Override
    public void update(Attribute attribute) {
        this.jdbcTemplate.update("UPDATE attribute SET name = ?, imagePath = ? FROM  WHERE name = ?", attribute.getName(), attribute.getImagePath(), attribute.getName());
    }
}

