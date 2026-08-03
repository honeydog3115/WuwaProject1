package com.sjb.wuwaechorank.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import com.sjb.wuwaechorank.dao.entity.substat.SubStatDao;
import com.sjb.wuwaechorank.entity.SubStat;
import com.sjb.wuwaechorank.util.test.DaoTestUtil;

@SpringBootTest
public class SubStatDaoTest {
    private static final String TABLE_NAME = "substat";

    @Autowired
    DaoTestUtil daoTestUtil;

    @Autowired
    SubStatDao subStatDao;

    SubStat subStat1;
    SubStat subStat2;
    SubStat subStat3;

    @BeforeEach
    void setUp(){
        daoTestUtil.initTable(TABLE_NAME);

        this.subStat1 = new SubStat(1, "체력%");
        this.subStat2 = new SubStat(2, "크리티컬 확률");
        this.subStat3 = new SubStat(3, "크리티컬 피해");
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

}
