package com.sjb.wuwaechorank.dao.entity.Resonator;

import com.sjb.wuwaechorank.customannotation.DaoInterface;
import com.sjb.wuwaechorank.dao.crud.CrudDao;
import com.sjb.wuwaechorank.entity.Resonator;

@DaoInterface
public interface ResonatorDao extends CrudDao<Resonator>, ResonatorCoreDao {
    
}
