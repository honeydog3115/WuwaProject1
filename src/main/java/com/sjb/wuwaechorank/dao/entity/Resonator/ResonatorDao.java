package com.sjb.wuwaechorank.dao.entity.resonator;

import com.sjb.wuwaechorank.customannotation.DaoInterface;
import com.sjb.wuwaechorank.dao.crud.CrudDao;
import com.sjb.wuwaechorank.entity.Resonator;

@DaoInterface
public interface ResonatorDao extends CrudDao<Resonator>, ResonatorCoreDao {
    
}
