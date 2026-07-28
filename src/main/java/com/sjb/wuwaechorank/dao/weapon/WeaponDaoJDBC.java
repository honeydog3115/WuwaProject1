package com.sjb.wuwaechorank.dao.weapon;


import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.dto.Weapon;

@Repository
public class WeaponDaoJDBC implements WeaponDao {
    private final JdbcTemplate jdbcTemplate;

    public WeaponDaoJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Weapon> weaponRowMapper = (rs, rowNum) -> {
        Weapon weapon = new Weapon();
        weapon.setId(rs.getInt("id"));
        weapon.setName(rs.getString("name"));
        weapon.setImagePath(rs.getString("imagePath"));
        return weapon;
    };

    @Override
    public void add(Weapon weapon) {
        this.jdbcTemplate.update("INSERT INTO weapon(name, imagePath) VALUES(?, ?)", weapon.getName(), weapon.getImagePath());
    }
    @Override
    public Weapon get(int id) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM weapon WHERE id = ?", this.weaponRowMapper, id);
    }
    @Override
    public List<Weapon> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM weapon", this.weaponRowMapper);
    }
    @Override
    public void update(int id, Weapon weapon) {
        this.jdbcTemplate.update("UPDATE weapon SET name = ?, imagePath = ? WHERE id = ?", weapon.getName(), weapon.getImagePath(), id);
    }
    @Override
    public void delete(int id) {
        this.jdbcTemplate.update("DELETE FROM weapon WHERE id = ?", id);
    }
    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM weapon");
    }
    @Override
    public int getCount() {
        return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM weapon", Integer.class);
    }
    @Override
    public void init() {
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        this.jdbcTemplate.execute("TRUNCATE TABLE weapon");
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }
}
