package com.sjb.wuwaechorank.dao.entity.resonatorecho;

import java.util.List;

import com.sjb.wuwaechorank.customannotation.DaoCoreInterface;
import com.sjb.wuwaechorank.entity.ResonatorEcho;

@DaoCoreInterface
public interface ResonatorEchoDaoCore {
    List<ResonatorEcho> getAllByPresetId(int presetId);
}
