package com.sjb.wuwaechorank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.sjb.wuwaechorank.dao.entity.weapon.WeaponDao;
import com.sjb.wuwaechorank.entity.Weapon;
import com.sjb.wuwaechorank.service.weapon.WeaponService;
import com.sjb.wuwaechorank.service.weapon.WeaponServiceImpl;

// WeaponService 테스트 클래스
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WeaponServiceTest {
    @Mock
    WeaponDao weaponDao;

    @InjectMocks
    WeaponService weaponService = new WeaponServiceImpl(weaponDao);

    Weapon weapon1;
    Weapon weapon2;
    Weapon weapon3;

    @BeforeEach
    void setUp(){
        this.weapon1 = new Weapon(1, "직검", "asdf/qwer/a.jpg");
        this.weapon2 = new Weapon(2, "권갑", "asdf/qwer/b.jpg");
        this.weapon3 = new Weapon(3, "권총", "asdf/qwer/c.jpg");
    }

    // WeaponService getAllWeapons 테스트
    @Test
    void getAllWeapons(){
        List<Weapon> weapons = this.weaponDao.getAll();
        when(this.weaponDao.getAll()).thenReturn(weapons);
        assertThat(this.weaponService.getAllWeapons()).isEqualTo(weapons);
    }    
}
