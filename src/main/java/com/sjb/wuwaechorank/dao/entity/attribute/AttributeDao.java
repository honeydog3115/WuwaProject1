package com.sjb.wuwaechorank.dao.entity.attribute;

import com.sjb.wuwaechorank.customannotation.DaoInterface;
import com.sjb.wuwaechorank.dao.crud.CrudDao;
import com.sjb.wuwaechorank.entity.Attribute;

@DaoInterface
public interface AttributeDao extends CrudDao<Attribute>, AttributeDaoCore {
}