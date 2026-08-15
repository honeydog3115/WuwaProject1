package com.sjb.wuwaechorank.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjb.wuwaechorank.entity.Weapon;
import com.sjb.wuwaechorank.service.weapon.WeaponService;


@RestController
public class WeaponController {
    private WeaponService weaponService;

    public WeaponController(WeaponService weaponService){
        this.weaponService = weaponService;
    }

    @GetMapping("/weapon")
    public List<Weapon> getWeapons() {
        return weaponService.getAllWeapons();
    }
    
}
