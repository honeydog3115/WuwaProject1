package com.sjb.wuwaechorank.dao.entity.presetecho;

import com.sjb.wuwaechorank.customannotation.DaoInterface;
import com.sjb.wuwaechorank.dao.crud.CrudDao;
import com.sjb.wuwaechorank.entity.PresetEcho;

@DaoInterface
public interface PresetEchoDao extends CrudDao<PresetEcho>, PresetEchoDaoCore {
    
}
