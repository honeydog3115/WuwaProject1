package com.sjb.wuwaechorank.dao.sonataeffect;

import java.util.List;

import com.sjb.wuwaechorank.entity.SonataEffect;

public interface SonataEffectDao {
    void add(SonataEffect sonataEffect);
    SonataEffect get(int id);
    List<SonataEffect> getAll();
    void delete(int id);
    void deleteAll();
    void update(int id, SonataEffect sonataEffect);
    int getCount();
    void init();
}
