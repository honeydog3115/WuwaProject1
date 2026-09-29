package com.sjb.wuwaechorank.dao.entity.echo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    public List<Echo> getAllBySonataEffect(int id) {
        return this.jdbcTemplate.query("SELECT * FROM echo WHERE JSON_CONTAINS(sonataEffectId, ?)", rowMapper, String.valueOf(id));
    }
    
}