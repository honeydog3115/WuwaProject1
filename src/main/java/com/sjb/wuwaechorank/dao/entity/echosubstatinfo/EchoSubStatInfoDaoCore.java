package com.sjb.wuwaechorank.dao.entity.echosubstatinfo;

import java.util.List;

import com.sjb.wuwaechorank.customannotation.DaoCoreInterface;

@DaoCoreInterface
public interface EchoSubStatInfoDaoCore {
    // ResonatorEchoId로 subStatInfoId를 반환하는 함수
    List<Integer> getSubStatInfoIdsByResonatorEchoId(int resonatorEchoId);

}
