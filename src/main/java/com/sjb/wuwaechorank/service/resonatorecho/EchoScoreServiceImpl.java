package com.sjb.wuwaechorank.service.resonatorecho;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sjb.wuwaechorank.dao.entity.echosubstatinfo.EchoSubStatInfoDao;
import com.sjb.wuwaechorank.dao.entity.presetecho.PresetEchoDao;
import com.sjb.wuwaechorank.dao.entity.resonatorecho.ResonatorEchoDao;
import com.sjb.wuwaechorank.dao.entity.substatinfo.SubStatInfoDao;
import com.sjb.wuwaechorank.dto.ResonatorEchoInfoDto;
import com.sjb.wuwaechorank.dto.ResonatorEchoSubStatDto;
import com.sjb.wuwaechorank.entity.SubStatInfo;
import com.sjb.wuwaechorank.entity.ValidStat;
import com.sjb.wuwaechorank.service.resonator.ResonatorService;

public class EchoScoreServiceImpl implements EchoScoreService{
    private static final double MAX_SUBSTAT_SCORE = 20;

    private ResonatorEchoDao resonatorEchoDao;
    private SubStatInfoDao subStatInfoDao;
    private EchoSubStatInfoDao echoSubStatInfoDao;
    private PresetEchoDao presetEchoDao;
    private ResonatorService resonatorService;

    public EchoScoreServiceImpl(ResonatorEchoDao resonatorEchoDao, SubStatInfoDao subStatInfoDao, EchoSubStatInfoDao echoSubStatInfoDao, PresetEchoDao presetEchoDao, ResonatorService resonatorService){
        this.resonatorEchoDao = resonatorEchoDao;
        this.subStatInfoDao = subStatInfoDao;
        this.echoSubStatInfoDao = echoSubStatInfoDao;
        this.presetEchoDao = presetEchoDao;
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
    
    // @Override
    // public double getResonatorEchoScore(int resonatorId, List<ResonatorEchoInfoDto> resonatorEchosInfo, boolean insertDB, int presetId) {
    //     ValidStat validStat = this.resonatorService.getResonatorValidStat(resonatorId);

    //     //
    //     List<Integer> subStatIds = resonatorEchosInfo.stream()
    //             .flatMap(resonatorEchoInfo->resonatorEchoInfo.echoSubStats().stream())
    //             .map(ResonatorEchoSubStatDto::subStatId)
    //             .distinct()
    //             .toList();

    //     Map<Integer, List<String>> idValueMap = this.subStatInfoDao.getAllBySubStatIdIn(subStatIds).stream()
    //             .collect(Collectors.groupingBy(
    //                 SubStatInfo::getSubStatId,
    //                 Collectors.mapping(SubStatInfo::getValue, Collectors.toList())
    //             ));
    //     //

    //     double totalScore = resonatorEchosInfo.stream()
    //             .map(resonatorEchoInfo->this.calcEchoScore(resonatorEchoInfo, validStat, idValueMap, insertDB, presetId))
    //             .mapToDouble(Double::doubleValue)
    //             .average()
    //             .orElse(0.0);
        
    //     return totalScore;
    // }

    // // 예코 점수 계산 함수
    // private double calcEchoScore(ResonatorEchoInfoDto resonatorEchoInfoDto, ValidStat validStat, Map<Integer, List<String>> idValueMap, boolean insertDB, int presetId){
    //     final int resonatorEchoId = insertDB ? this.resonatorEchoDao.add(
    //             ResonatorEcho.builder()
    //                 .echoId(resonatorEchoInfoDto.echoId())
    //                 .mainStatId(resonatorEchoInfoDto.mainStatId())
    //                 .build()
    //         ):-1;

    //     double score = resonatorEchoInfoDto.echoSubStats().stream()
    //             .map(resonatorEchoSubStat->this.calcSubStatScore(resonatorEchoSubStat, validStat, idValueMap, insertDB, resonatorEchoId))
    //             .mapToDouble(Double::doubleValue)
    //             .sum();

    //     if(insertDB){
    //         this.resonatorEchoDao.update(resonatorEchoId, ResonatorEcho.builder()
    //                 .echoId(resonatorEchoInfoDto.echoId())
    //                 .mainStatId(resonatorEchoInfoDto.mainStatId())
    //                 .score(score)
    //                 .build());
    //         this.presetEchoDao.add(PresetEcho.builder()
    //                 .presetId(presetId)
    //                 .resonatorEchoId(resonatorEchoId)
    //                 .build());
    //     }
        
    //     return score;
    // }

    // // 부음속성 점수 계산함수
    // private double calcSubStatScore(ResonatorEchoSubStatDto echoSubStat, ValidStat validStat, Map<Integer, List<String>> idValueMap, boolean insertDB, int resonatorEchoId){
    //     if(!validStat.containSubStat(echoSubStat.subStatId()))
    //         return 0;
        
    //     List<String> values = idValueMap.get(echoSubStat.subStatId());

    //     // 기본 점수 단위는 스탯당 최대점수(20)를 옵션 수로 나눈값
    //     double baseScoreUnit = MAX_SUBSTAT_SCORE/values.size();
    //     // 가중치는 해당 옵션이 상옵일 수록 올라감.
    //     int weight = values.indexOf(echoSubStat.value())+1;

    //     if(insertDB){
    //         this.echoSubStatInfoDao.add(EchoSubStatInfo.builder()
    //                 .resonatorEchoId(resonatorEchoId)
    //                 .subStatInfoId(echoSubStat.subStatInfoId())
    //                 .build());
    //     }

    //     return baseScoreUnit * weight;
    // }

}