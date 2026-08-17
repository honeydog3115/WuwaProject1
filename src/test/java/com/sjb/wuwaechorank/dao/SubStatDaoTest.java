package com.sjb.wuwaechorank.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sjb.wuwaechorank.dao.entity.substat.SubStatDao;
import com.sjb.wuwaechorank.dao.entity.substatinfo.SubStatInfoDao;
import com.sjb.wuwaechorank.dto.SubStatDetailDto;
import com.sjb.wuwaechorank.entity.SubStat;
import com.sjb.wuwaechorank.entity.SubStatInfo;
import com.sjb.wuwaechorank.util.DaoJDBCUtil;

@SpringBootTest
public class SubStatDaoTest extends BaseDaoTest{
    private static final String TABLE_NAME = "substat";

    @Autowired
    DaoJDBCUtil daoTestUtil;

    @Autowired
    SubStatDao subStatDao;

    @Autowired
    SubStatInfoDao subStatInfoDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    SubStat subStat1;
    SubStat subStat2;
    SubStat subStat3;

    SubStatInfo subStatInfo1;
    SubStatInfo subStatInfo2;
    SubStatInfo subStatInfo3;

    @BeforeEach
    void setUp(){
        daoTestUtil.initTables(TABLE_NAME, "substatinfo");

        this.subStat1 = new SubStat(1, "체력%");
        this.subStat2 = new SubStat(2, "크리티컬 확률");
        this.subStat3 = new SubStat(3, "크리티컬 피해");

        this.subStatInfo1 = new SubStatInfo(1, 1, "10%", "10%");
        this.subStatInfo2 = new SubStatInfo(1, 2, "20%", "20%");
        this.subStatInfo3 = new SubStatInfo(1, 3, "30%", "30%");
    }

    @Test
    void addAndGet(){
        this.subStatDao.add(subStat1);
        
        SubStat subStat = this.subStatDao.get(1);
        assertEquals(subStat1.getName(), subStat.getName());
    }
    
    @Test
    void getAll(){
        this.subStatDao.add(subStat1);
        this.subStatDao.add(subStat2);
        this.subStatDao.add(subStat3);

        List<SubStat> stats = this.subStatDao.getAll();
        assertEquals(3, stats.size());
    }

    @Test
    void deleteAndGetCount(){
        this.subStatDao.add(subStat1);
        this.subStatDao.delete(subStat1.getId());
        assertEquals(0, this.subStatDao.getCount());
    }

    @Test
    void update(){
        this.subStatDao.add(subStat1);
        SubStat subStat = new SubStat(1, "공격력%");
        this.subStatDao.update(subStat1.getId(), subStat);
        subStat = this.subStatDao.get(1);

        assertEquals("공격력%", subStat.getName());
    }
    
    @Test
    void nameDuplicate(){
        assertThrows(DuplicateKeyException.class, ()-> {
            this.subStatDao.add(subStat1);
            this.subStatDao.add(subStat1);
        });
    }

    @Test
    void getByIds(){
        this.subStatDao.add(subStat1);
        this.subStatDao.add(subStat2);
        this.subStatDao.add(subStat3);
        List<SubStat> substats = this.subStatDao.getByIds(List.of(1,2,3));
        List<SubStat> expected = List.of(subStat1, subStat2, subStat3);
        assertThat(substats)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(expected);    
    }
    
    @Test
    void getSubStatDetailsBysubStatInfoIds(){
        this.subStatDao.add(subStat1);
        this.subStatDao.add(subStat2);
        this.subStatDao.add(subStat3);

        this.subStatInfoDao.add(subStatInfo1);
        this.subStatInfoDao.add(subStatInfo2);
        this.subStatInfoDao.add(subStatInfo3);

        List<SubStatDetailDto> subStatDetails = this.subStatDao.getSubStatDetailsBysubStatInfoIds(List.of(1,2,3));
        List<SubStat> subStats = List.of(this.subStat1, this.subStat2, this.subStat3);
        List<SubStatInfo> subStatInfos = List.of(this.subStatInfo1, this.subStatInfo2, this.subStatInfo3);
        List<SubStatDetailDto> expected = new ArrayList<>();
        for(int i = 0; i < subStats.size(); i++){
            expected.add(SubStatDetailDto.builder()
                .subStatName(subStats.get(i).getName())
                .subStatValue(subStatInfos.get(i).getValue())
                .subStatChance(subStatInfos.get(i).getChance())
                .build());
        }
        assertThat(subStatDetails)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expected);
    }
}

