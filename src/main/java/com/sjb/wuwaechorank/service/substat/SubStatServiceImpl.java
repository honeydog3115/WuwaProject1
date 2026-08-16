package com.sjb.wuwaechorank.service.substat;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.echosubstatinfo.EchoSubStatInfoDao;
import com.sjb.wuwaechorank.dao.entity.substat.SubStatDao;
import com.sjb.wuwaechorank.dao.entity.substatinfo.SubStatInfoDao;
import com.sjb.wuwaechorank.dto.SubStatDetailDto;
import com.sjb.wuwaechorank.dto.SubStatWithSubStatInfoDto;
import com.sjb.wuwaechorank.entity.SubStat;
import com.sjb.wuwaechorank.entity.SubStatInfo;

// 부음 속성 관련 서비스 구현 클래스
@Service
public class SubStatServiceImpl implements SubStatService{
    private SubStatDao subStatDao;
    private SubStatInfoDao subStatInfoDao;
    private EchoSubStatInfoDao echoSubStatInfoDao;

    public SubStatServiceImpl(SubStatDao subStatDao, SubStatInfoDao subStatInfoDao, EchoSubStatInfoDao echoSubStatInfoDao){
        this.subStatDao = subStatDao;
        this.subStatInfoDao = subStatInfoDao;
        this.echoSubStatInfoDao = echoSubStatInfoDao;
    }

    // 에코의 부음속성 선택시 필요한 모든 부음속성 정보들을 반환.
    @Override
    public List<SubStatWithSubStatInfoDto> getAllSubStatsWithSubStatInfo() {
        List<SubStat> subStats =  this.subStatDao.getAll();
        List<SubStatInfo> subStatInfos = this.subStatInfoDao.getAll();
        Map<Integer, List<SubStatInfo>> subStatInfoGroupBySubStat = subStatInfos.stream()
                .collect(Collectors.groupingBy(SubStatInfo::getSubStatId));
        
        return subStats.stream()
                .map(subStat->SubStatWithSubStatInfoDto.builder()
                        .id(subStat.getId())
                        .name(subStat.getName())
                        .subStatInfos(subStatInfoGroupBySubStat.getOrDefault(subStat.getId(), List.of()))
                        .build())
                .toList();
    }

    @Override
    public SubStatDetailDto getSubStatDetail(int subStatInfoId) {
        SubStatInfo subStatInfo = this.subStatInfoDao.get(subStatInfoId);
        SubStat subStat = this.subStatDao.get(subStatInfo.getSubStatId());
        return SubStatDetailDto.builder()
                .subStatName(subStat.getName())
                .subStatValue(subStatInfo.getValue())
                .subStatChance(subStatInfo.getChance())
                .build();
    }

    @Override
    public List<SubStatDetailDto> getSubStatDetails(List<Integer> substatInfoIds) {
        return this.subStatDao.getSubStatDetailsBysubStatInfoIds(substatInfoIds);
    }

    @Override
    public List<SubStatDetailDto> getSubStatDetailsByResonatorEchoId(int resonatorEchoId) {
        return this.getSubStatDetails(this.echoSubStatInfoDao.getSubStatInfoIdsByResonatorEchoId(resonatorEchoId));
    }
}
