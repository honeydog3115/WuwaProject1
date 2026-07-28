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
    public Attribute get(int id) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM attribute WHERE id = ?", this.attributeRowMapper, id);
    }
    
    @Override
    public List<Attribute> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM attribute", this.attributeRowMapper);
    }

    @Override
    public void delete(int id) {
        this.jdbcTemplate.update("DELETE FROM attribute WHERE id = ?", id);
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM attribute");
    }

    @Override
    public void update(int id, Attribute attribute) {
        this.jdbcTemplate.update("UPDATE attribute SET name = ?, imagePath = ? WHERE id = ?", attribute.getName(), attribute.getImagePath(), id);
    }
    
    @Override
    public int getCount(){
        return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM attribute", Integer.class);
    }

    public void init(){
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0;");
        this.jdbcTemplate.execute("TRUNCATE TABLE attribute;");
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1;");
    }
}

