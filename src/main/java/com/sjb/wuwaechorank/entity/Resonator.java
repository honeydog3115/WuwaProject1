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
public class Resonator {
    @PrimaryKey
    @Builder.Default
    int id = 1;
    @Builder.Default
    String name = "방랑자";
    @ForeignKey
    @Builder.Default
    int attributeId = 1;
    @ForeignKey
    @Builder.Default
    int weaponId = 1;
    @Builder.Default
    int star = 5;
    @ForeignKey
    @Builder.Default
    int validStatId = 1;
    @Builder.Default
    String energyRegenRequirements = "120%";
    @Builder.Default
    String imagePath = "asdf/qwer/a.jpg";
}
