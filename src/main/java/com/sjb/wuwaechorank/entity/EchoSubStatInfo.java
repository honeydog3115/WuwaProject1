package com.sjb.wuwaechorank.entity;

import com.sjb.wuwaechorank.customannotation.ForeignKey;
import com.sjb.wuwaechorank.customannotation.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EchoSubStatInfo {
    @PrimaryKey
    @Builder.Default
    int id = 1;
    @ForeignKey
    @Builder.Default
    int resonatorEchoId = 1;
    @ForeignKey
    @Builder.Default
    int subStatInfoId = 1;
}
