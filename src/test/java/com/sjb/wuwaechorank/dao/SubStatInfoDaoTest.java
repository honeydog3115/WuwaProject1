package com.sjb.wuwaechorank.dao;

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
import com.sjb.wuwaechorank.util.test.DaoSqlErrorCode;
import com.sjb.wuwaechorank.util.test.DaoTestUtil;

@SpringBootTest
public class SubStatInfoDaoTest {
    private static final String TABLE_NAME = "substatinfo";
    private static final String REFERENCE_TABLE_NAME = "substat";

    @Autowired
    DaoTestUtil daoTestUtil;

    @Autowired
    SubStatInfoDao subStatInfoDao;

    @Autowired
    SubStatDao subStatDao;

    SubStatInfo subStatInfo1;
    SubStatInfo subStatInfo2;
    SubStatInfo subStatInfo3;

    SubStat subStat1;

    @BeforeEach
    void setUp(){
        daoTestUtil.initTables(TABLE_NAME);
        daoTestUtil.initTables(REFERENCE_TABLE_NAME);

        subStatInfo1 = new SubStatInfo(1, 1, "15.5%", "12.1%");
        subStatInfo2 = new SubStatInfo(2, 1, "12.0%", "23.7%");
        subStatInfo3 = new SubStatInfo(3, 1, "18.5%", "5.2%");
        subStat1 = new SubStat(1, "공격력%");
        this.subStatDao.add(subStat1);
    }

        @Test
    void addAndGet(){        
        this.subStatInfoDao.add(subStatInfo1);
        
        SubStatInfo subStatInfo = this.subStatInfoDao.get(subStatInfo1.getId());
        assertEquals(1, subStatInfo.getId());
        assertEquals("15.5%", subStatInfo.getValue());
        assertEquals("12.1%", subStatInfo.getChance());
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
}
