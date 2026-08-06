package com.sjb.wuwaechorank.dao.entity.user;

import com.sjb.wuwaechorank.customannotation.DaoInterface;
import com.sjb.wuwaechorank.dao.crud.CrudDao;
import com.sjb.wuwaechorank.entity.User;

@DaoInterface
public interface UserDao extends CrudDao<User>, UserDaoCore{
    
}
