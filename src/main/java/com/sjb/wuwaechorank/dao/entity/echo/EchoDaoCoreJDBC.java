package com.sjb.wuwaechorank.dao.entity.echo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.sjb.wuwaechorank.entity.Echo;

import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


@Repository
public class EchoDaoCoreJDBC implements EchoDaoCore {
    private final JdbcTemplate jdbcTemplate;
    // private RowMapper<Echo> rowMapper = BeanPropertyRowMapper.newInstance(Echo.class);
    private final ObjectMapper objectMapper;

    public EchoDaoCoreJDBC(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    private final RowMapper<Echo> rowMapper = new RowMapper<Echo>() {
        @Override
        public Echo mapRow(ResultSet rs, int rowNum) throws SQLException {
            String sonataEffectIds = rs.getString("sonataEffectId");
            List<Integer> idList = null;

            if (sonataEffectIds != null) {
                try {
                    // JSON 문자열을 List<Integer>로 역직렬화
                    idList = objectMapper.readValue(sonataEffectIds, new TypeReference<List<Integer>>() {});
                } catch (JacksonException e) {
                    throw new RuntimeException("JSON 파싱 에러", e);
                }
            }

            return Echo.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .sonataEffectId(idList)
                    .cost(rs.getString("cost"))
                    .imagePath(rs.getString("imagepath"))
                    .build();
        }
    };

    @Override
    public int add(Echo entity) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(entity.getClass().getSimpleName())
                .usingGeneratedKeyColumns("id");
        Map<String, Object> param = new HashMap<>();
        // Echo echo = (Echo) entity;
        param.put("id", entity.getId());
        param.put("name", entity.getName());
        param.put("cost", entity.getCost());
        param.put("sonataEffectId", entity.getSonataEffectId());
        param.put("imagePath", entity.getImagePath());

        Number key = jdbcInsert.executeAndReturnKey(param);
        return key.intValue();
    }

    @Override
    public Echo get(Object primaryKey) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM echo WHERE id = ?", rowMapper, primaryKey);
    }

    @Override
    public List<Echo> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM echo",rowMapper);
    }
    
    @Override
    public void update(Object primaryKey, Echo entity) {
        // Echo echo = (Echo) entity;
        String sonataEffectJson = objectMapper.writeValueAsString(entity.getSonataEffectId());
        this.jdbcTemplate.update("UPDATE echo SET name = ?, sonataEffectId = ?, cost = ?, imagePath = ? WHERE id = ?", entity.getName(), sonataEffectJson, entity.getCost(), entity.getImagePath(), primaryKey);
    }


    @Override
    public List<Echo> getAllBySonataEffect(int id) {
        return this.jdbcTemplate.query("SELECT * FROM echo WHERE JSON_CONTAINS(sonataEffectId, ?)", rowMapper, String.valueOf(id));
    }
    
}