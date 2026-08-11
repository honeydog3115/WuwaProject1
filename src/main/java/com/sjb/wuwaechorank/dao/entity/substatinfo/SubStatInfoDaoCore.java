package com.sjb.wuwaechorank.dao.entity.substatinfo;

import java.util.List;

import com.sjb.wuwaechorank.customannotation.DaoCoreInterface;
import com.sjb.wuwaechorank.entity.SubStatInfo;

@DaoCoreInterface
public interface SubStatInfoDaoCore {
    // subStatId를 기준으로 SubStatInfo 엔티티를 전부 가져옴.
    List<SubStatInfo> getAllBySubStatId(int subStatId);
}
