package com.sjb.wuwaechorank.dao.entity.echosubstatinfo;

import java.util.List;

import com.sjb.wuwaechorank.customannotation.DaoCoreInterface;

@DaoCoreInterface
public interface EchoSubStatInfoDaoCore {
    // ResonatorEchoId로 echoSubStatinfoid를 반환하는 함수
    List<Integer> getIdsByResonatorEchoId(int resonatorEchoId);

}
