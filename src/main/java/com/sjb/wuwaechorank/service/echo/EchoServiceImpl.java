package com.sjb.wuwaechorank.service.echo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.echo.EchoDao;
import com.sjb.wuwaechorank.dao.entity.sonataeffect.SonataEffectDao;
import com.sjb.wuwaechorank.entity.Echo;

@Service
public class EchoServiceImpl implements EchoService{
    private EchoDao echoDao;
    private SonataEffectDao sonataEffectDao;

    public EchoServiceImpl(EchoDao echoDao, SonataEffectDao sonataEffectDao){
        this.echoDao = echoDao;
        this.sonataEffectDao = sonataEffectDao;
    }

    @Override
    public List<Echo> getAllEchos() {
        // TODO Auto-generated method stub
        // List<Echo> echos = this.echoDao.getAll();
        // echos.stream().map(echo->echo.getSonataEffectId()).map(sonataEffectId->)

        return null;
    }
    
}