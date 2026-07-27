package com.sjb.wuwaechorank.dao.weapon;

import java.util.List;

import com.sjb.wuwaechorank.dto.Weapon;

public interface WeaponDao {
    void add(Weapon weapon);
    Weapon get(String name);
    List<Weapon> getAll();
    void delete(String name);
    void deleteAll();
    void update(Weapon weapon);  
} 