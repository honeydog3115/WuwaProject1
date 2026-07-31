package com.sjb.wuwaechorank.dao;

import java.util.List;

public interface BaseDao<T, S>{
    void add(T entity);
    T get(S primaryKey);
    List<T> getAll();
    void delete(S primaryKey);
    int getCount();
    void update(S primaryKey, T entity);
}
