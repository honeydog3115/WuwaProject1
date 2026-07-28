package com.sjb.wuwaechorank.dao.attribute;

import java.util.List;

import com.sjb.wuwaechorank.dto.Attribute;

public interface AttributeDao {
    void add(Attribute attribute);
    Attribute get(int id);
    List<Attribute> getAll();
    void delete(int id);
    void deleteAll();
    void update(int id, Attribute attribute);
    int getCount();
    void init();
}
