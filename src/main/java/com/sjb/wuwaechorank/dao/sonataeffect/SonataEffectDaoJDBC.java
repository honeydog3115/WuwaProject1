package com.sjb.wuwaechorank.dao.sonataeffect;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.entity.SonataEffect;

@Repository
public class SonataEffectDaoJDBC implements SonataEffectDao {
    private final JdbcTemplate jdbcTemplate;

    public SonataEffectDaoJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<SonataEffect> sonataEffectRowMapper = (rs, rowNum) -> {
        SonataEffect sonataEffect = new SonataEffect();
        sonataEffect.setId(rs.getInt("id"));
        sonataEffect.setName(rs.getString("name"));
        sonataEffect.setImagePath(rs.getString("ImagePath"));
        return sonataEffect;
    };
    @Override
    public void add(SonataEffect sonataEffect) {
        this.jdbcTemplate.update("INSERT INTO sonataEffect(name, imagePath) VALUES(?, ?)", sonataEffect.getName(), sonataEffect.getImagePath());
    }
    @Override
    public SonataEffect get(int id) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM sonataEffect WHERE id = ?", this.sonataEffectRowMapper, id);
    }
    @Override
    public List<SonataEffect> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM sonataEffect", this.sonataEffectRowMapper);
    }
    @Override
    public void delete(int id) {
        this.jdbcTemplate.update("DELETE FROM sonataEffect WHERE id = ?", id);
    }
    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM sonataEffect");
    }
    @Override
    public void update(int id, SonataEffect sonataEffect) {
        this.jdbcTemplate.update("UPDATE sonataEffect SET name = ?, imagePath = ? WHERE id = ?", sonataEffect.getName(), sonataEffect.getImagePath(), id);
    }
    @Override
    public int getCount() {
        return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM sonataEffect", Integer.class);
    }
    @Override
    public void init() {
        this.jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");
        this.jdbcTemplate.update("TRUNCATE TABLE sonataEffect");
        this.jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
    }
}
