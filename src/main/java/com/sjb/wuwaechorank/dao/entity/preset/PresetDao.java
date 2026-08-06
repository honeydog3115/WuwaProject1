package com.sjb.wuwaechorank.dao.entity.preset;

import com.sjb.wuwaechorank.customannotation.DaoInterface;
import com.sjb.wuwaechorank.dao.crud.CrudDao;
import com.sjb.wuwaechorank.entity.Preset;

@DaoInterface
public interface PresetDao extends CrudDao<Preset>, PresetDaoCore{
    
}
