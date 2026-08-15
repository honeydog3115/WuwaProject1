package com.sjb.wuwaechorank.controller;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.sjb.wuwaechorank.entity.Weapon;
import com.sjb.wuwaechorank.service.weapon.WeaponService;
import com.sjb.wuwaechorank.util.ControllerTestUtil;

@WebMvcTest(WeaponController.class)
@Import(ControllerTestUtil.class)
public class WeaponControllerTest {
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    ControllerTestUtil controllerTestUtil;

    @MockitoBean
    WeaponService weaponService;

    Weapon weapon1;
    Weapon weapon2;
    Weapon weapon3;
    List<Weapon> weapons;

    @BeforeEach 
    void setUp(){
        this.weapon1 = new Weapon(1, "직검", "asdf/qwer/a.jpg");
        this.weapon2 = new Weapon(2, "권갑", "asdf/qwer/b.jpg");
        this.weapon3 = new Weapon(3, "권총", "asdf/qwer/c.jpg");

        this.weapons = List.of(this.weapon1, this.weapon2, this.weapon3);
    }

    @Test
    void getAllWeapons(){
        given(this.weaponService.getAllWeapons()).willReturn(weapons);

        controllerTestUtil.validateListTypeResponse(mockMvc, "/weapon", Weapon.class, weapons);
    }

}
