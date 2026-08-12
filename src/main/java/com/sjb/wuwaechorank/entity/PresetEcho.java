package com.sjb.wuwaechorank.entity;

import com.sjb.wuwaechorank.customannotation.ForeignKey;
import com.sjb.wuwaechorank.customannotation.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 프리셋에 저장할 공명자 에코를 매핑하는 엔티티
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PresetEcho {
    @PrimaryKey
    @Builder.Default
    int id = 1;
    @ForeignKey
    @Builder.Default
    int presetId = 1;
    @ForeignKey
    @Builder.Default
    int resonatorEchoId= 1;
}
