package com.sjb.wuwaechorank.service.substat;

import java.util.List;

import com.sjb.wuwaechorank.dto.SubStatDetailDto;
import com.sjb.wuwaechorank.dto.SubStatWithSubStatInfoDto;

// 부음속성 관련    서비스 인터페이스
public interface SubStatService {
    // 모든 서브 스탯을 대상이 갖을 수 있는 값, 확률 전부를 반환하는 함수
    List<SubStatWithSubStatInfoDto> getAllSubStatsWithSubStatInfo();
    // 서브 스탯의 이름, 값, 확률을 반환하는 함수
    SubStatDetailDto getSubStatDetail(int subStatInfoId);
    // 서브 스탯의 이름, 값, 확률이 담긴 객체를 리스트로 반환하는 함수
    List<SubStatDetailDto> getSubStatDetails(List<Integer> substatInfoIds);
    // resonatorEchoId로 SubStatDetailDto를 반환하는 함수
    List<SubStatDetailDto> getSubStatDetailsByResonatorEchoId(int resonatorEchoId);

}
