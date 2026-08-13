package com.sjb.wuwaechorank.dao.entity.presetecho;

import java.util.List;

import com.sjb.wuwaechorank.customannotation.DaoCoreInterface;
import com.sjb.wuwaechorank.entity.PresetEcho;

@DaoCoreInterface
public interface PresetEchoDaoCore {
    // presetId로 PresetEcho들을 가져오는 함수
    List<PresetEcho> getAllByPresetId(int presetId);
}
