package com.sjb.wuwaechorank.dao.entity.resonatorecho;

import java.beans.BeanProperty;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.entity.ResonatorEcho;

@Repository
public class ResonatorEchoDaoCoreJDBC implements ResonatorEchoDaoCore {
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<ResonatorEcho> rowMapper = BeanPropertyRowMapper.newInstance(ResonatorEcho.class);

    public ResonatorEchoDaoCoreJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ResonatorEcho> getResonatorEchos(int presetId) {
        return this.jdbcTemplate.query(
            "SELECT r.id, r.echoid, r.mainstatid, r.substatid1, r.substatid2, r.substatid3, r.substatid4, r.substatid5, r.score FROM presetecho p INNER JOIN resonatorecho r ON p.resonatorechoid = r.id WHERE p.preseid = ?", 
            rowMapper, presetId);
    }
}
