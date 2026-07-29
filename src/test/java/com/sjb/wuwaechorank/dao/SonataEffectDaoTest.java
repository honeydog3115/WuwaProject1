package com.sjb.wuwaechorank.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import com.sjb.wuwaechorank.dao.sonataeffect.SonataEffectDao;
import com.sjb.wuwaechorank.entity.SonataEffect;

@SpringBootTest
public class SonataEffectDaoTest {
    @Autowired
    SonataEffectDao sonataEffectDao;

    SonataEffect sontaEffect1;
    SonataEffect sontaEffect2;
    SonataEffect sontaEffect3;

    @BeforeEach
    void setUp(){
        this.sonataEffectDao.init();
        this.sontaEffect1 = new SonataEffect(1, "야밤의 서리", "asdf/qwer/a.jpg");
        this.sontaEffect2 = new SonataEffect(2, "솟구치는 용암", "asdf/qwer/b.jpg");
        this.sontaEffect3 = new SonataEffect(3, "울려퍼지는 뇌음", "asdf/qwer/c.jpg");
    }

    @Test
    void addAndGet(){
        this.sonataEffectDao.add(sontaEffect1);

        SonataEffect sonataEffect = this.sonataEffectDao.get(1);
        assertEquals(1, sonataEffect.getId());
        assertEquals("야밤의 서리", sonataEffect.getName());
        assertEquals("asdf/qwer/a.jpg", sonataEffect.getImagePath());
    }

    @Test
    void getAll(){
        this.sonataEffectDao.add(sontaEffect1);
        this.sonataEffectDao.add(sontaEffect2);
        this.sonataEffectDao.add(sontaEffect3);

        List<SonataEffect> sonataEffects = this.sonataEffectDao.getAll();

        assertEquals(3, sonataEffects.size());
    }
    
    @Test
    void deleteAndGetCount(){
        this.sonataEffectDao.add(sontaEffect1);
        this.sonataEffectDao.delete(1);

        assertEquals(0, this.sonataEffectDao.getCount());
    }

    @Test
    void deleteAll(){
        this.sonataEffectDao.add(sontaEffect1);
        this.sonataEffectDao.add(sontaEffect2);
        this.sonataEffectDao.add(sontaEffect3);
        this.sonataEffectDao.deleteAll();

        assertEquals(0, this.sonataEffectDao.getCount());
    }
    
    @Test
    void update(){
        this.sonataEffectDao.add(sontaEffect1);
        
        SonataEffect sonataEffect = new SonataEffect(sontaEffect1.getId(), "스쳐가는 바람", "asdf/qwer/d.jpg");
        this.sonataEffectDao.update(1, sonataEffect);
        sonataEffect = this.sonataEffectDao.get(1);

        assertEquals(1, sonataEffect.getId());
        assertEquals("스쳐가는 바람", sonataEffect.getName());
        assertEquals("asdf/qwer/d.jpg", sonataEffect.getImagePath());
    }

    @Test
    void nameDuplicate(){
        assertThrows(DuplicateKeyException.class, ()-> {
            this.sonataEffectDao.add(sontaEffect1);
            this.sonataEffectDao.add(sontaEffect1);
        });
    }
}
