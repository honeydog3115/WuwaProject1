package com.sjb.wuwaechorank.dao.entity.weapon;

import com.sjb.wuwaechorank.customannotation.DaoInterface;
import com.sjb.wuwaechorank.dao.crud.CrudDao;
import com.sjb.wuwaechorank.entity.Weapon;

@DaoInterface
public interface WeaponDao extends CrudDao<Weapon>, WeaponDaoCore {
} 