package com.sjb.wuwaechorank.entity;

import java.util.Objects;
import java.util.stream.Stream;

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
public class ValidStat {
    @PrimaryKey
    @Builder.Default
    int id = 1;
    @ForeignKey
    @Builder.Default
    Integer subStatId1 = 1;
    @ForeignKey
    @Builder.Default
    Integer subStatId2 = 1;
    @ForeignKey
    @Builder.Default
    Integer subStatId3 = 1;
    @ForeignKey
    @Builder.Default
    Integer subStatId4 = 1;
    @ForeignKey
    @Builder.Default
    Integer subStatId5 = 1;
    @ForeignKey
    @Builder.Default
    Integer subStatId6 = 1;

    // subStatId가 ValidStat에 포함되어 있는지 확인하는 함수
    public boolean containSubStat(int subStatId) {
        return Stream.of(subStatId1, subStatId2, subStatId3, subStatId4, subStatId5, subStatId6)
                .filter(Objects::nonNull)
                .anyMatch(id -> id.equals(subStatId));
    }
}
