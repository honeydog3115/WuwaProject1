package com.sjb.wuwaechorank.dao.attribute;

import java.sql.JDBCType;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.dao.BaseCrud;
import com.sjb.wuwaechorank.entity.Attribute;

@Repository
public class AttributeDaoJDBC extends BaseCrud implements AttributeDao {
    public AttributeDaoJDBC(JdbcTemplate jdbcTemplate){
        super(jdbcTemplate);
    }
    private final RowMapper<Attribute> attributeRowMapper =
        (rs, rowNum) -> {
            Attribute attribute = new Attribute();
            attribute.setId(rs.getInt("id"));
            attribute.setName(rs.getString("name"));
            attribute.setImagePath(rs.getString("imagepath"));
            return attribute;
        };

    public void add(Attribute attribute){
        super.add(attribute);
    }
    // @Override
    // public Attribute get(int id) {
    //     return this.jdbcTemplate.queryForObject("SELECT * FROM attribute WHERE id = ?", this.attributeRowMapper, id);
    // }
    
    // @Override
    // public List<Attribute> getAll() {
    //     return this.jdbcTemplate.query("SELECT * FROM attribute", this.attributeRowMapper);
    // }

    // @Override
    // public void delete(int id) {
    //     this.jdbcTemplate.update("DELETE FROM attribute WHERE id = ?", id);
    // }

    // @Override
    // public void deleteAll() {
    //     this.jdbcTemplate.update("DELETE FROM attribute");
    // }

    // @Override
    // public void update(int id, Attribute attribute) {
    //     this.jdbcTemplate.update("UPDATE attribute SET name = ?, imagePath = ? WHERE id = ?", attribute.getName(), attribute.getImagePath(), id);
    // }
    
    // @Override
    // public int getCount(){
    //     return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM attribute", Integer.class);
    // }
    @Override
    public void init() {
        super.init();
    }
}

