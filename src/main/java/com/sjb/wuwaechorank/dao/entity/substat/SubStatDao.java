package com.sjb.wuwaechorank.dao.entity.substat;

import com.sjb.wuwaechorank.customannotation.DaoInterface;
import com.sjb.wuwaechorank.dao.crud.CrudDao;
import com.sjb.wuwaechorank.entity.SubStat;

@DaoInterface
public interface SubStatDao extends CrudDao<SubStat>, SubStatDaoCore {
    
}
