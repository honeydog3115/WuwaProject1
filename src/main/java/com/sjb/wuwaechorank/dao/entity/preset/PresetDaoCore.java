package com.sjb.wuwaechorank.dao.entity.preset;

import java.util.List;

import com.sjb.wuwaechorank.customannotation.DaoCoreInterface;
import com.sjb.wuwaechorank.entity.Preset;

@DaoCoreInterface
public interface PresetDaoCore {
    List<Preset> getAllByUserId(int userId);
}