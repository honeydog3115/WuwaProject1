package com.sjb.wuwaechorank.dao.attribute;

import java.util.List;

import com.sjb.wuwaechorank.dto.Attribute;

public interface AttributeDao {
    void add(Attribute attribute);
    Attribute get(String name);
    List<Attribute> getAll();
    void delete(String name);
    void deleteAll();
    void update(Attribute attribute);
}
