package com.sjb.wuwaechorank.service.resonatorecho;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.resonatorecho.ResonatorEchoDao;

//공명자 에코와 관련된 서비스 구현 클래스
@Service
public class ResonatorEchoServiceImpl implements ResonatorEchoService{
    private ResonatorEchoDao resonatorEchoDao;
    
    public ResonatorEchoServiceImpl(ResonatorEchoDao resonatorEchoDao){
        this.resonatorEchoDao = resonatorEchoDao;
    }

    
}
