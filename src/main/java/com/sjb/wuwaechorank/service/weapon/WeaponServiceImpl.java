package com.sjb.wuwaechorank.service.weapon;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.weapon.WeaponDao;
import com.sjb.wuwaechorank.entity.Weapon;

// 공명자 무기 관련 서비스 구현체
@Service
public class WeaponServiceImpl implements WeaponService{
    private WeaponDao weaponDao;

    public WeaponServiceImpl(WeaponDao weaponDao){
        this.weaponDao = weaponDao;
    }

    @Override
    public List<Weapon> getAllWeapons() {
        List<Weapon> weapons = this.weaponDao.getAll();
        return weapons;
    }
}
