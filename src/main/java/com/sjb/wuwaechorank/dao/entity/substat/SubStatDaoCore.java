package com.sjb.wuwaechorank.dao.entity.substat;

import java.util.List;

import com.sjb.wuwaechorank.customannotation.DaoCoreInterface;
import com.sjb.wuwaechorank.dto.SubStatDetailDto;
import com.sjb.wuwaechorank.entity.SubStat;

@DaoCoreInterface
public interface SubStatDaoCore {
    // id들로 substat들을 가져오는 함수
    List<SubStat> getByIds(List<Integer> ids);
    // subStatInfoIds로 부음속성의 이름, 값, 확률을 반환하는 함수
    List<SubStatDetailDto> getSubStatDetailsBysubStatInfoIds(List<Integer> subStatInfoIds);
}
