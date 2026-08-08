package com.sjb.wuwaechorank.service.resonator;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.resonator.ResonatorDao;
import com.sjb.wuwaechorank.entity.Resonator;


// 공명자 서비스
@Service
public class ResonatorServiceImpl implements ResonatorService {
    private ResonatorDao resonatorDao;

    public ResonatorServiceImpl(ResonatorDao resonatorDao){
        this.resonatorDao = resonatorDao;
    }

    // 전체 공명자 정보 반환
    public List<Resonator> getAll(){
        return resonatorDao.getAll();
    }

    // 공명자의 유효 능력치 반환.
    public void getValidStat(){

    }
}
