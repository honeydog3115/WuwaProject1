package com.sjb.wuwaechorank.dao.attribute;

import com.sjb.wuwaechorank.dao.CrudDao;
import com.sjb.wuwaechorank.dao.DaoInterface;
import com.sjb.wuwaechorank.entity.Attribute;

@DaoInterface
public interface AttributeDao extends CrudDao<Attribute>, AttributeDaoCore {
}