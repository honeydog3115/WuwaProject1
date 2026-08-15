package com.sjb.wuwaechorank.service.mainstat;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.mainstat.MainStatDao;
import com.sjb.wuwaechorank.entity.MainStat;

@Service
public class MainStatServiceImpl implements MainStatService {
    private MainStatDao mainStatDao;

    public MainStatServiceImpl(MainStatDao mainStatDao){
        this.mainStatDao = mainStatDao;
    }

    @Override
    public List<MainStat> getAllMainStats() {
        return mainStatDao.getAll();
    }
}
