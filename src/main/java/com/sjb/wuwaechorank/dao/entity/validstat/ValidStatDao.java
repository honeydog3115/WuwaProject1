package com.sjb.wuwaechorank.dao.entity.validstat;

import com.sjb.wuwaechorank.customannotation.DaoInterface;
import com.sjb.wuwaechorank.dao.crud.CrudDao;
import com.sjb.wuwaechorank.entity.ValidStat;

@DaoInterface
public interface ValidStatDao extends CrudDao<ValidStat>, ValidStatDaoCore{
    
}
