package com.sjb.wuwaechorank.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sjb.wuwaechorank.dao.mainstat.MainStatDao;
import com.sjb.wuwaechorank.entity.MainStat;

@SpringBootTest
public class MainStatDaoTest {
    @Autowired
    MainStatDao mainStatDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    MainStat mainstat1;
    MainStat mainstat2;
    MainStat mainstat3;

    @BeforeEach
    void setUp(){
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        jdbcTemplate.execute("TRUNCATE TABLE mainstat");
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
        
        this.mainstat1 = new MainStat(1, "공격력", "30.5", "asdf/qwer/a.jpg");
        this.mainstat2 = new MainStat(2, "방어력", "10", "asdf/qwer/b.jpg");
        this.mainstat3 = new MainStat(3, "체력", "25", "asdf/qwer/c.jpg");
    }

    @Test
    void addAndGet(){
        this.mainStatDao.add(mainstat1);

        MainStat mainStat = this.mainStatDao.get(1);
        assertEquals(1, mainStat.getId());
        assertEquals("공격력", mainStat.getName());
        assertEquals("30.5", mainStat.getValue());
        assertEquals("asdf/qwer/a.jpg", mainStat.getImagePath());
    }

    @Test
    void getAll(){
        this.mainStatDao.add(mainstat1);
        this.mainStatDao.add(mainstat2);
        this.mainStatDao.add(mainstat3);

        List<MainStat> mainStats = this.mainStatDao.getAll();

        assertEquals(3, mainStats.size());
    }
    
    @Test
    void deleteAndGetCount(){
        this.mainStatDao.add(mainstat1);
        this.mainStatDao.delete(1);

        assertEquals(0, this.mainStatDao.getCount());
    }

    @Test
    void deleteAll(){
        this.mainStatDao.add(mainstat1);
        this.mainStatDao.add(mainstat2);
        this.mainStatDao.add(mainstat3);
        this.mainStatDao.deleteAll();

        assertEquals(0, this.mainStatDao.getCount());
    }
    
    @Test
    void update(){
        this.mainStatDao.add(mainstat1);
        
        MainStat mainStat = new MainStat(mainstat1.getId(), "크리티컬", "10.2", "asdf/qwer/d.jpg");
        this.mainStatDao.update(1, mainStat);
        mainStat = this.mainStatDao.get(1);

        assertEquals(1, mainStat.getId());
        assertEquals("크리티컬", mainStat.getName());
        assertEquals("10.2", mainStat.getValue());
        assertEquals("asdf/qwer/d.jpg", mainStat.getImagePath());
    }

    @Test
    void nameDuplicate(){
        assertThrows(DuplicateKeyException.class, ()-> {
            this.mainStatDao.add(mainstat1);
            this.mainStatDao.add(mainstat1);
        });
    }
}
