package com.sjb.wuwaechorank.dao;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sjb.wuwaechorank.dao.entity.validstat.ValidStatDao;
import com.sjb.wuwaechorank.entity.ValidStat;
import com.sjb.wuwaechorank.util.DaoJDBCUtil;
import com.sjb.wuwaechorank.util.TestFixture;

@SpringBootTest
public class ValidStatDaoTest {
    private final static String TABLE_NAME = "validstat";

    @Autowired
    DaoJDBCUtil daoJDBCUtil;

    @Autowired
    ValidStatDao validStatDao;
    
    @Autowired
    TestFixture testFixture;

    ValidStat validStat1;
    ValidStat validStat2;
    ValidStat validStat3;

    @BeforeEach
    void setUp(){
        testFixture.createReferenceEntity(ValidStat.class);
        daoJDBCUtil.initTables(TABLE_NAME);

        validStat1 = new ValidStat(1, 1, 1, 1, 1, 1);
    }
}
