package com.sjb.wuwaechorank.dao.entity.substatinfo;

import com.sjb.wuwaechorank.customannotation.DaoInterface;
import com.sjb.wuwaechorank.dao.crud.CrudDao;
import com.sjb.wuwaechorank.entity.SubStatInfo;

@DaoInterface
public interface SubStatInfoDao extends CrudDao<SubStatInfo>, SubStatInfoDaoCore{
    
}
