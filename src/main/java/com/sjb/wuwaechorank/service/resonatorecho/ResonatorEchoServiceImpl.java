package com.sjb.wuwaechorank.service.resonatorecho;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.resonator.ResonatorDao;
import com.sjb.wuwaechorank.dao.entity.resonatorecho.ResonatorEchoDao;
import com.sjb.wuwaechorank.dao.entity.substatinfo.SubStatInfoDao;
import com.sjb.wuwaechorank.dao.entity.validstat.ValidStatDao;
import com.sjb.wuwaechorank.dto.ResonatorEchoInfoDto;
import com.sjb.wuwaechorank.dto.ResonatorEchoSubStatDto;
import com.sjb.wuwaechorank.entity.ResonatorEcho;
import com.sjb.wuwaechorank.entity.SubStatInfo;
import com.sjb.wuwaechorank.entity.ValidStat;

//공명자 에코와 관련된 서비스 구현 클래스
@Service
public class ResonatorEchoServiceImpl implements ResonatorEchoService{
    private static final double MAX_SUBSTAT_SCORE = 20;
    private ResonatorEchoDao resonatorEchoDao;
    private SubStatInfoDao subStatInfoDao;
    private ResonatorDao resonatorDao;
    private ValidStatDao validStatDao;
    
    public ResonatorEchoServiceImpl(ResonatorEchoDao resonatorEchoDao, SubStatInfoDao subStatInfoDao, ResonatorDao resonatorDao, ValidStatDao validStatDao){
        this.resonatorEchoDao = resonatorEchoDao;
        this.subStatInfoDao = subStatInfoDao;
        this.resonatorDao = resonatorDao;
        this.validStatDao = validStatDao;
    }

    @Override
    public double getResonatorEchoScore(int resonatorId, List<ResonatorEchoInfoDto> resonatorEchosInfo) {
        ValidStat validStat = this.validStatDao.get(this.resonatorDao.get(resonatorId).getValidStatId());
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

        double totalScore = resonatorEchosInfo.stream()
                .map(resonatorEchoInfo->this.calcEchoScore(resonatorEchoInfo, validStat, idValueMap))
                .mapToDouble(Double::doubleValue)
                .average()
                .getAsDouble();
        
        return totalScore;
    }

    // 예코 점수 계산 함수
    private double calcEchoScore(ResonatorEchoInfoDto resonatorEchoInfoDto, ValidStat validStat, Map<Integer, List<String>> idValueMap){
        List<ResonatorEchoSubStatDto> echoSubStats = resonatorEchoInfoDto.echoSubStats();
        double score = resonatorEchoInfoDto.echoSubStats().stream()
                .map(resonatorEchoSubStat->this.calcSubStatScore(resonatorEchoSubStat, validStat, idValueMap))
                .mapToDouble(Double::doubleValue)
                .sum();

        this.resonatorEchoDao.add(
            ResonatorEcho.builder()
                .echoId(resonatorEchoInfoDto.echoId())
                .SubStatId1(this.getOrNull(echoSubStats, 0))
                .SubStatId2(this.getOrNull(echoSubStats, 1))
                .SubStatId3(this.getOrNull(echoSubStats, 2))
                .SubStatId4(this.getOrNull(echoSubStats, 3))
                .SubStatId5(this.getOrNull(echoSubStats, 4))
                .score(score)
                .build()
        );
        
        return score;
    }

    // 부음속성 점수 계산함수
    private double calcSubStatScore(ResonatorEchoSubStatDto echoSubStat, ValidStat validStat, Map<Integer, List<String>> idValueMap){
        if(!validStat.containSubStat(echoSubStat.subStatId()))
            return 0;
        
        List<String> values = idValueMap.get(echoSubStat.subStatId());

        // 기본 점수 단위는 스탯당 최대점수(20)를 옵션 수로 나눈값
        double baseScoreUnit = MAX_SUBSTAT_SCORE/values.size();
        // 가중치는 해당 옵션이 상옵일 수록 올라감.
        int weight = values.indexOf(echoSubStat.value())+1;
        return baseScoreUnit * weight;
    }

    private Integer getOrNull(List<ResonatorEchoSubStatDto> echoSubStats, int index){
        return echoSubStats.size() < index ? null : echoSubStats.get(index).subStatId();
    }
}
