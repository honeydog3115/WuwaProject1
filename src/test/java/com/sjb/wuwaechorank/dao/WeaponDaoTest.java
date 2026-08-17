package com.sjb.wuwaechorank.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import com.sjb.wuwaechorank.dao.entity.weapon.WeaponDao;
import com.sjb.wuwaechorank.entity.Weapon;
import com.sjb.wuwaechorank.util.DaoJDBCUtil;

@SpringBootTest
public class WeaponDaoTest extends BaseDaoTest{
    private static final String TABLE_NAME = "weapon";

    @Autowired
    DaoJDBCUtil daoTestUtil;

    @Autowired
    private WeaponDao weaponDao;

    private Weapon weapon1;
    private Weapon weapon2;
    private Weapon weapon3;


    @BeforeEach
    void setUp(){
        daoTestUtil.initTables(TABLE_NAME);

        this.weapon1 = new Weapon(1, "직검", "asdf/qwer/a.jpg");
        this.weapon2 = new Weapon(2, "권갑", "asdf/qwer/b.jpg");
        this.weapon3 = new Weapon(3, "권총", "asdf/qwer/c.jpg");
    }

    @Test
    void addAndGet(){
        this.weaponDao.add(weapon1);

        Weapon weapon = this.weaponDao.get(weapon1.getId());
        assertEquals(1, weapon.getId());
        assertEquals("직검", weapon.getName());
        assertEquals("asdf/qwer/a.jpg", weapon.getImagePath());
    }

    @Test
    void getAll(){
        this.weaponDao.add(weapon1);
        this.weaponDao.add(weapon2);
        this.weaponDao.add(weapon3);

        List<Weapon> weapons = this.weaponDao.getAll();
        assertEquals(3, weapons.size());
    }

    @Test
    void delete(){
        this.weaponDao.add(weapon1);
        this.weaponDao.delete(weapon1.getId());
        assertEquals(0, this.weaponDao.getCount());
    }

    @Test
    void update(){
        this.weaponDao.add(weapon1);
        Weapon weapon = new Weapon(1, "대검", "asdf/qwer/d.jpg");
        this.weaponDao.update(weapon1.getId(), weapon);
        weapon = this.weaponDao.get(1);
        assertEquals("대검", weapon.getName());
        assertEquals("asdf/qwer/d.jpg", weapon.getImagePath());
    }
    
    @Test
    void nameDuplicate(){
        assertThrows(DuplicateKeyException.class, ()-> {
            this.weaponDao.add(weapon1);
            this.weaponDao.add(weapon1);
        });
    }
}
