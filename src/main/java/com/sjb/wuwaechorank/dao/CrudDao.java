package com.sjb.wuwaechorank.dao;

import java.util.List;

public interface CrudDao<T>{
    void add(T entity);
    T get(Object primaryKey);
    List<T> getAll();
    void delete(Object primaryKey);
    int getCount();
    void update(Object primaryKey, T entity);
}
