package com.sjb.wuwaechorank.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sjb.wuwaechorank.dao.entity.echosubstatinfo.EchoSubStatInfoDao;
import com.sjb.wuwaechorank.entity.Echo;
import com.sjb.wuwaechorank.entity.EchoSubStatInfo;
import com.sjb.wuwaechorank.entity.ResonatorEcho;
import com.sjb.wuwaechorank.entity.SubStat;
import com.sjb.wuwaechorank.entity.SubStatInfo;
import com.sjb.wuwaechorank.util.DaoJDBCUtil;
import com.sjb.wuwaechorank.util.DaoTestUtil;
import com.sjb.wuwaechorank.util.TestFixture;

@SpringBootTest
public class EchoSubStatInfoDaoTest extends BaseDaoTest{
    private final static String TABLE_NAME = "echosubstatinfo";

    @Autowired
    EchoSubStatInfoDao echoSubStatInfoDao;

    @Autowired
    DaoJDBCUtil daoJDBCUtil;

    @Autowired
    TestFixture testFixture;

    EchoSubStatInfo echoSubStatInfo1;    
    EchoSubStatInfo echoSubStatInfo2;
    EchoSubStatInfo echoSubStatInfo3;

    SubStatInfo subStatInfo1;
    SubStatInfo subStatInfo2;
    SubStatInfo subStatInfo3;
    
    ResonatorEcho resonatorEcho1;
    @BeforeEach
    void setUp(){
        this.testFixture.createReferenceEntity(EchoSubStatInfo.class);
        this.daoJDBCUtil.setTestFixture(testFixture);
        this.daoJDBCUtil.initTables(TABLE_NAME);
        this.daoJDBCUtil.initReferenceTables();

        this.echoSubStatInfo1 = new EchoSubStatInfo(1, 1, 1);
        this.echoSubStatInfo2 = new EchoSubStatInfo(2, 1, 2);
        this.echoSubStatInfo3 = new EchoSubStatInfo(3, 2, 3);

        this.subStatInfo1 = new SubStatInfo(1, 1, "10%", "10%");
        this.subStatInfo2 = new SubStatInfo(2, 1, "10%", "10%");
        this.subStatInfo3 = new SubStatInfo(3, 1, "10%", "10%");

        this.resonatorEcho1 = new ResonatorEcho(2, 1, 1, 50);
        
        this.daoJDBCUtil.addRefEntity(resonatorEcho1);
        this.daoJDBCUtil.addRefEntity(subStatInfo2);
        this.daoJDBCUtil.addRefEntity(subStatInfo3);
    }

    @Test
    void addAndGet(){
        this.echoSubStatInfoDao.add(echoSubStatInfo1);
        EchoSubStatInfo echoSubStatInfo = this.echoSubStatInfoDao.get(1);
        assertThat(echoSubStatInfo).usingRecursiveComparison().isEqualTo(echoSubStatInfo1);
    }

    @Test
    void getAllAndGetCount(){
        this.echoSubStatInfoDao.add(echoSubStatInfo2);
        this.echoSubStatInfoDao.add(echoSubStatInfo1);
        this.echoSubStatInfoDao.add(echoSubStatInfo3);
        
        assertThat(this.echoSubStatInfoDao.getAll().size()).isEqualTo(this.echoSubStatInfoDao.getCount());
    }
    
    @Test
    void delete(){
        this.echoSubStatInfoDao.add(echoSubStatInfo1);
        this.echoSubStatInfoDao.delete(1);

        assertThat(this.echoSubStatInfoDao.getCount()).isEqualTo(0);
    }

    @Test
    void update(){
        this.echoSubStatInfoDao.add(echoSubStatInfo1);
        EchoSubStatInfo echoSubStatInfo = new EchoSubStatInfo(1, 1, 2);
        this.echoSubStatInfoDao.update(1, echoSubStatInfo);

        assertThat(this.echoSubStatInfoDao.get(1)).usingRecursiveComparison().isEqualTo(echoSubStatInfo);
    }

    @Test
    void foreignKeyConstraintFail(){
        this.echoSubStatInfo1.setResonatorEchoId(3);
        DaoTestUtil.foreignKeyConstraintViolationTest(()->this.echoSubStatInfoDao.add(echoSubStatInfo1));
        this.echoSubStatInfo2.setSubStatInfoId(4);
        DaoTestUtil.foreignKeyConstraintViolationTest(()->this.echoSubStatInfoDao.add(echoSubStatInfo2));
    }

    @ParameterizedTest(name = "{0} 삭제 테스트 진행")
    @ValueSource(classes = {
        SubStatInfo.class,
        SubStat.class,
        ResonatorEcho.class,
        Echo.class
    })
    void deleteCascade(Class<?> refEntiyClass){
        this.echoSubStatInfoDao.add(echoSubStatInfo1);
        this.daoJDBCUtil.deleteRefEntity(refEntiyClass);
        assertThat(this.echoSubStatInfoDao.getCount());
    }

    @Test
    void getIdsByResonatorEchoId (){
        this.echoSubStatInfoDao.add(echoSubStatInfo1);
        this.echoSubStatInfoDao.add(echoSubStatInfo2);
        this.echoSubStatInfoDao.add(echoSubStatInfo3);

        List<Integer> ids = this.echoSubStatInfoDao.getSubStatInfoIdsByResonatorEchoId(resonatorEcho1.getId());

        assertThat(ids).isEqualTo(List.of(3));
    }
}   
