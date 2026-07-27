package com.sjb.wuwaechorank.dao.weapon;


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

    private final RowMapper<Weapon> weaponAttribute = (rs, rowNum) -> {
        Weapon weapon = new Weapon();
        weapon.setId(rs.getInt("id"));
        weapon.setName(rs.getString("Name"));
        weapon.setImagePath(rs.getString("imagePath"));
        return weapon;
    };

    
    
    
}
