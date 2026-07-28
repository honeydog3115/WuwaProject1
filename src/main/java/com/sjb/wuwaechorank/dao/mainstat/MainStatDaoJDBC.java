package com.sjb.wuwaechorank.dao.mainstat;


import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.dao.BaseDaoImpl;
import com.sjb.wuwaechorank.dto.MainStat;

@Repository
public class MainStatDaoJDBC extends BaseDaoImpl implements MainStatDao {

    public MainStatDaoJDBC(JdbcTemplate jdbcTemplate){
        super(jdbcTemplate);
    }

    RowMapper<MainStat> mainStatRowMapper = (rs, rowNum) -> {
        MainStat mainStat = new MainStat();
        mainStat.setId(rs.getInt("id"));
        mainStat.setName(rs.getString("name"));
        mainStat.setValue(rs.getString("value"));
        mainStat.setImagePath(rs.getString("imagePath"));
        return mainStat;
    };

    @Override
    protected String tableName() {
        return "mainstat";
    }
    @Override
    public void add(MainStat mainStat) {
        this.jdbcTemplate.update(
            "INSERT INTO mainstat(name, value, imagePath) VALUES(?, ?, ?)", 
            mainStat.getName(), mainStat.getValue(), mainStat.getImagePath());
    }
    @Override
    public MainStat get(int id) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM mainstat WHERE id = ?", this.mainStatRowMapper , id);
    }
    @Override
    public List<MainStat> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM mainstat", this.mainStatRowMapper);
    }
    @Override
    public void delete(int id) {
        this.jdbcTemplate.update("DELETE FROM mainstat WHERE id = ?", id);
    }
    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM mainstat");
    }
    @Override
    public void update(int id, MainStat mainStat) {
        this.jdbcTemplate.update(
            "UPDATE mainstat SET name = ?, value = ?, imagePath = ? WHERE id = ?", 
            mainStat.getName(), mainStat.getValue(), mainStat.getImagePath(), id);
    }
}