package com.sjb.wuwaechorank.dao.weapon;

import java.util.List;

import com.sjb.wuwaechorank.entity.Weapon;

public interface WeaponDao {
    void add(Weapon weapon);
    Weapon get(int id);
    List<Weapon> getAll();
    void delete(int id);
    void deleteAll();
    void update(int id, Weapon weapon);
    int getCount();
    void init();
} 