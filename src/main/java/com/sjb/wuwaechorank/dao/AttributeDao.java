package com.sjb.wuwaechorank.dao;

import java.util.List;

import com.sjb.wuwaechorank.dto.Attribute;

public interface AttributeDao {
    void add();
    Attribute get();
    List<Attribute> getAll();
    void delete();
    void update();
}
