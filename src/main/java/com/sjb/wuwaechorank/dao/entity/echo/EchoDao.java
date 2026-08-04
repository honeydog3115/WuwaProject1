package com.sjb.wuwaechorank.dao.entity.echo;

import com.sjb.wuwaechorank.customannotation.DaoInterface;
import com.sjb.wuwaechorank.dao.crud.CrudDao;
import com.sjb.wuwaechorank.entity.Echo;

@DaoInterface
public interface EchoDao extends CrudDao<Echo>, EchoDaoCore {
    
}
