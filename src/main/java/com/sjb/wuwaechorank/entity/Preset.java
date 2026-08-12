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
public class Preset {
    @PrimaryKey
    @Builder.Default
    int id = 1;

    @Builder.Default
    String name = "방랑자 프리셋";
    
    @ForeignKey
    @Builder.Default
    Integer userId = 1;
    
    @Builder.Default
    Boolean bookmark = true;
    
    @ForeignKey
    @Builder.Default
    Integer resonatorId = 1;
        
    @Builder.Default
    Double echoTotalScore = 1.0;
}