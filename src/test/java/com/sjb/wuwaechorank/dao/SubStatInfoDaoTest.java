package com.sjb.wuwaechorank.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.sjb.wuwaechorank.dao.entity.substat.SubStatDao;
import com.sjb.wuwaechorank.dao.entity.substatinfo.SubStatInfoDao;
import com.sjb.wuwaechorank.entity.SubStat;
import com.sjb.wuwaechorank.entity.SubStatInfo;
import com.sjb.wuwaechorank.util.DaoSqlErrorCode;
import com.sjb.wuwaechorank.util.DaoJDBCUtil;

@SpringBootTest
public class SubStatInfoDaoTest {
    private static final String TABLE_NAME = "substatinfo";
    private static final String REFERENCE_TABLE_NAME = "substat";

    @Autowired
    DaoJDBCUtil daoTestUtil;

    @Autowired
    SubStatInfoDao subStatInfoDao;

    @Autowired
    SubStatDao subStatDao;

    SubStatInfo subStatInfo1;
    SubStatInfo subStatInfo2;
    SubStatInfo subStatInfo3;
    SubStatInfo subStatInfo4;
    SubStatInfo subStatInfo5;
    SubStatInfo subStatInfo6;
    SubStatInfo subStatInfo7;
    SubStatInfo subStatInfo8;

    SubStat subStat1;
    SubStat subStat2;
    SubStat subStat3;
    SubStat subStat4;
    SubStat subStat5;


    @BeforeEach
    void setUp(){
        daoTestUtil.initTables(TABLE_NAME);
        daoTestUtil.initTables(REFERENCE_TABLE_NAME);

        this.subStat1 = new SubStat(1, "체력%");
        this.subStat2 = new SubStat(2, "크리티컬확률");
        this.subStat3 = new SubStat(3, "크리티컬피해");
        this.subStat4 = new SubStat(4, "일반공격피해");
        this.subStat5 = new SubStat(5, "공명효율");

        this.subStatInfo1 = SubStatInfo.builder().id(1).SubStatId(1).value("10%").build();
        this.subStatInfo2 = SubStatInfo.builder().id(2).SubStatId(1).value("20%").build();
        this.subStatInfo3 = SubStatInfo.builder().id(3).SubStatId(1).value("30%").build();
        this.subStatInfo4 = SubStatInfo.builder().id(4).SubStatId(1).value("40%").build();
        this.subStatInfo5 = SubStatInfo.builder().id(5).SubStatId(2).value("10%").build();
        this.subStatInfo6 = SubStatInfo.builder().id(6).SubStatId(3).value("10%").build();
        this.subStatInfo7 = SubStatInfo.builder().id(7).SubStatId(4).value("10%").build();
        this.subStatInfo8 = SubStatInfo.builder().id(8).SubStatId(5).value("10%").build();
        subStat1 = new SubStat(1, "체력%");
        this.subStatDao.add(subStat1);
        this.subStatDao.add(subStat2);
        this.subStatDao.add(subStat3);
        this.subStatDao.add(subStat4);
        this.subStatDao.add(subStat5);
    }

    @Test
    void addAndGet(){        
        this.subStatInfoDao.add(subStatInfo1);
        
        SubStatInfo subStatInfo = this.subStatInfoDao.get(subStatInfo1.getId());
        assertThat(subStatInfo).usingRecursiveComparison().isEqualTo(subStatInfo1);
    }
    
    @Test
    void getAll(){
        this.subStatInfoDao.add(subStatInfo1);
        this.subStatInfoDao.add(subStatInfo2);
        this.subStatInfoDao.add(subStatInfo3);

        List<SubStatInfo> subStatInfos = this.subStatInfoDao.getAll();
        assertEquals(3, subStatInfos.size());
    }
    
    @Test
    void delete(){
        this.subStatInfoDao.add(subStatInfo1);
        this.subStatInfoDao.delete(subStatInfo1.getId());
        assertEquals(0, this.subStatInfoDao.getCount());
    }

    @Test
    void update(){
        this.subStatInfoDao.add(subStatInfo1);
        SubStatInfo subStatInfo = new SubStatInfo(1, 1,"22.2%", "33.3%");
        this.subStatInfoDao.update(subStatInfo1.getId(), subStatInfo);
        SubStatInfo subStatInfoUpdated = this.subStatInfoDao.get(1);
        assertEquals(subStatInfo.getValue(), subStatInfoUpdated.getValue());
        assertEquals(subStatInfo.getChance(), subStatInfoUpdated.getChance());
    }
    
    @Test
    void foreignKeyConstraintFail(){
        SubStatInfo subStatInfo = new SubStatInfo(1,2,"22.2%", "33.3%");
        
        try {
            this.subStatInfoDao.add(subStatInfo);            
        } catch (DataIntegrityViolationException e) {
            assertEquals("SQLIntegrityConstraintViolationException", e.getMostSpecificCause().getClass().getSimpleName());
            SQLIntegrityConstraintViolationException sqlException = (SQLIntegrityConstraintViolationException)e.getCause();
            assertEquals(DaoSqlErrorCode.FOREIGN_KEY_CONSTRAINT_FAIL, sqlException.getErrorCode());
        }
    }
    
    @Test
    void cascadeDelete(){
        this.subStatInfoDao.add(subStatInfo1);
        this.subStatDao.delete(1);
        assertEquals(0, this.subStatInfoDao.getCount()); 
    }

    @Test
    void getAllBySubStatId(){
        this.subStatInfoDao.add(subStatInfo1);
        this.subStatInfoDao.add(subStatInfo2);
        this.subStatInfoDao.add(subStatInfo3);
        List<SubStatInfo> subStatInfos = this.subStatInfoDao.getAllBySubStatId(1);
        assertThat(subStatInfos.size()).isEqualTo(3);
    }
    
    @Test
    void getAllBySubStatIdIn(){
        this.subStatInfoDao.add(subStatInfo1);
        this.subStatInfoDao.add(subStatInfo2);
        this.subStatInfoDao.add(subStatInfo3);
        this.subStatInfoDao.add(subStatInfo4);
        this.subStatInfoDao.add(subStatInfo5);
        this.subStatInfoDao.add(subStatInfo6);
        this.subStatInfoDao.add(subStatInfo7);
        this.subStatInfoDao.add(subStatInfo8);

        List<Integer> subStatIds = List.of(1,2,3,4,5);
        List<SubStatInfo> subStatInfos =  this.subStatInfoDao.getAllBySubStatIdIn(subStatIds);

        List<SubStatInfo> expected = List.of(subStatInfo1, subStatInfo2, subStatInfo3, subStatInfo4, subStatInfo5, subStatInfo6, subStatInfo7, subStatInfo8);

        assertThat(subStatInfos).usingRecursiveComparison().isEqualTo(expected);
    }
}
