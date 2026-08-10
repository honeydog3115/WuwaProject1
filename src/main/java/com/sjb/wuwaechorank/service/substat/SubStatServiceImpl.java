package com.sjb.wuwaechorank.service.substat;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.substat.SubStatDao;
import com.sjb.wuwaechorank.dao.entity.substatinfo.SubStatInfoDao;
import com.sjb.wuwaechorank.dto.SubStatDto;
import com.sjb.wuwaechorank.entity.SubStat;
import com.sjb.wuwaechorank.entity.SubStatInfo;

// 부음 속성 관련 서비스 구현 클래스
@Service
public class SubStatServiceImpl implements SubStatService{
    private final SubStatDao subStatDao;
    private final SubStatInfoDao subStatInfoDao;

    public SubStatServiceImpl(SubStatDao subStatDao, SubStatInfoDao subStatInfoDao){
        this.subStatDao = subStatDao;
        this.subStatInfoDao = subStatInfoDao;
    }

    //
    @Override
    public List<SubStatDto> getSubStat() {
        List<SubStat> subStats =  this.subStatDao.getAll();
        List<SubStatInfo> subStatInfos = this.subStatInfoDao.getAll();
        Map<Integer, List<SubStatInfo>> subStatInfoGroupBySubStat = subStatInfos.stream()
                .collect(Collectors.groupingBy(SubStatInfo::getSubStatId));
        
        return subStats.stream()
                .map(subStat->SubStatDto.builder()
                        .id(subStat.getId())
                        .name(subStat.getName())
                        .subStatInfos(subStatInfoGroupBySubStat.getOrDefault(subStat.getId(), List.of()))
                        .build())
                .toList();
    }
}
