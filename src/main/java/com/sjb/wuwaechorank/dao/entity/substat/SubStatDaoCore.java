package com.sjb.wuwaechorank.dao.entity.substat;

import java.util.List;

import com.sjb.wuwaechorank.customannotation.DaoCoreInterface;
import com.sjb.wuwaechorank.entity.SubStat;

@DaoCoreInterface
public interface SubStatDaoCore {
    // id들로 substat들을 가져오는 함수
    List<SubStat> getByIds(List<Integer> ids);
}
