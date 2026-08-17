package com.sjb.wuwaechorank.service.resonatorecho;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sjb.wuwaechorank.dao.entity.substatinfo.SubStatInfoDao;
import com.sjb.wuwaechorank.dto.ResonatorEchoInfoDto;
import com.sjb.wuwaechorank.dto.ResonatorEchoSubStatDto;
import com.sjb.wuwaechorank.entity.SubStatInfo;
import com.sjb.wuwaechorank.entity.ValidStat;
import com.sjb.wuwaechorank.service.resonator.ResonatorService;

public class Test{
    private static final double MAX_SUBSTAT_SCORE = 20;

    private SubStatInfoDao subStatInfoDao;
    private ResonatorService resonatorService;

    public Test(SubStatInfoDao subStatInfoDao, ResonatorService resonatorService){
        this.subStatInfoDao = subStatInfoDao;
        this.resonatorService = resonatorService;
    }
    
    public List<Double> getResonatorEchoScore(int resonatorId, List<ResonatorEchoInfoDto> resonatorEchosInfo) {
        ValidStat validStat = this.resonatorService.getResonatorValidStat(resonatorId);

        //
        List<Integer> subStatIds = resonatorEchosInfo.stream()
                .flatMap(resonatorEchoInfo->resonatorEchoInfo.echoSubStats().stream())
                .map(ResonatorEchoSubStatDto::subStatId)
                .distinct()
                .toList();

        Map<Integer, List<String>> idValueMap = this.subStatInfoDao.getAllBySubStatIdIn(subStatIds).stream()
                .collect(Collectors.groupingBy(
                    SubStatInfo::getSubStatId,
                    Collectors.mapping(SubStatInfo::getValue, Collectors.toList())
                ));
        //

        List<Double> scores = resonatorEchosInfo.stream()
                .map(resonatorEchoInfo->this.calcEchoScore(resonatorEchoInfo, validStat, idValueMap))
                .mapToDouble(Double::doubleValue).boxed().toList();
                
        return scores;
    }

    // 예코 점수 계산 함수
    private double calcEchoScore(ResonatorEchoInfoDto resonatorEchoInfoDto, ValidStat validStat, Map<Integer, List<String>> idValueMap){
        double score = resonatorEchoInfoDto.echoSubStats().stream()
                .mapToDouble(resonatorEchoSubstat->{
                    if(!validStat.containSubStat(resonatorEchoSubstat.subStatId()))
                        return 0;
                    
                    List<String> values = idValueMap.get(resonatorEchoSubstat.subStatId());

                    // 기본 점수 단위는 스탯당 최대점수(20)를 옵션 수로 나눈값
                    double baseScoreUnit = MAX_SUBSTAT_SCORE/values.size();
                    // 가중치는 해당 옵션이 상옵일 수록 올라감.
                    int weight = values.indexOf(resonatorEchoSubstat.value())+1;

                    return baseScoreUnit * weight;
                })
                .sum();

        return score;
    }
}