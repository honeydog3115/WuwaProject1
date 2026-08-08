package com.sjb.wuwaechorank.service.resonator;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.resonator.ResonatorDao;

@Service
public class ResonatorServiceImpl implements ResonatorService {
    private ResonatorDao resonatorDao;

    public ResonatorServiceImpl(ResonatorDao resonatorDao){
        this.resonatorDao = resonatorDao;
    }


}
