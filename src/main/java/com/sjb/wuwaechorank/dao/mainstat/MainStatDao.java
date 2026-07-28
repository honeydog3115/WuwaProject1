package com.sjb.wuwaechorank.dao.mainstat;

import java.util.List;

import com.sjb.wuwaechorank.dao.BaseDao;
import com.sjb.wuwaechorank.dto.MainStat;

public interface MainStatDao extends BaseDao {
    void add(MainStat mainStat);
    MainStat get(int id);
    List<MainStat> getAll();
    void delete(int id);
    void deleteAll();
    void update(int id, MainStat mainStat);
}
