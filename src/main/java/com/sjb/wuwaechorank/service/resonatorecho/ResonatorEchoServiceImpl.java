package com.sjb.wuwaechorank.service.resonatorecho;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.resonatorecho.ResonatorEchoDao;
import com.sjb.wuwaechorank.dao.entity.substatinfo.SubStatInfoDao;
import com.sjb.wuwaechorank.dto.ResonatorEchoInfoDto;
import com.sjb.wuwaechorank.dto.ResonatorEchoSubStatDto;
import com.sjb.wuwaechorank.entity.SubStatInfo;

//공명자 에코와 관련된 서비스 구현 클래스
@Service
public class ResonatorEchoServiceImpl implements ResonatorEchoService{
    private static final double MAX_SUBSTAT_SCORE = 20;
    private ResonatorEchoDao resonatorEchoDao;
    private SubStatInfoDao subStatInfoDao;
    
    public ResonatorEchoServiceImpl(ResonatorEchoDao resonatorEchoDao, SubStatInfoDao subStatInfoDao){
        this.resonatorEchoDao = resonatorEchoDao;
        this.subStatInfoDao = subStatInfoDao;
    }

    @Override
    public double getResonatorEchoScore(List<ResonatorEchoInfoDto> resonatorEchosInfo) {
        // TODO Auto-generated method stub
        return resonatorEchosInfo.stream().map(this::clacEchoScore).mapToDouble(Double::doubleValue).average().getAsDouble();
    }

    // 예코 점수 계산 함수
    private double clacEchoScore(ResonatorEchoInfoDto resonatorEchoInfoDto){
        double score = resonatorEchoInfoDto.echoSubStats().stream()
                .map(this::clacSubStatScore)
                .mapToDouble(Double::doubleValue)
                .sum();
        
        return score;
    }

    // 부음속성 점수 계산함수
    private double clacSubStatScore(ResonatorEchoSubStatDto echoSubStat){
        List<String> values = this.subStatInfoDao.getAllBySubStatId(echoSubStat.subStatId()).stream().map(subStatinfo->subStatinfo.getValue()).toList();
        // 기본 점수 단위는 스탯당 최대점수(20)를 옵션 수로 나눈값
        double baseScoreUnit = MAX_SUBSTAT_SCORE/values.size();
        // 가중치는 해당 옵션이 상옵일 수록 올라감.
        int weight = values.indexOf(echoSubStat.value())+1;
        return baseScoreUnit * weight;
    }
}
