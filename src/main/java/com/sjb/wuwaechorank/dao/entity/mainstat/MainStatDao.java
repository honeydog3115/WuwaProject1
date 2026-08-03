package com.sjb.wuwaechorank.dao.entity.mainstat;

import com.sjb.wuwaechorank.customannotation.DaoInterface;
import com.sjb.wuwaechorank.dao.crud.CrudDao;
import com.sjb.wuwaechorank.entity.MainStat;

@DaoInterface
public interface MainStatDao extends CrudDao<MainStat>, MainStatDaoCore {
}
