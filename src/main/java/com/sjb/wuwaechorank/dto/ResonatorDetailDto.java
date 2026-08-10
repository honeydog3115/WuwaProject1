package com.sjb.wuwaechorank.dto;

import java.util.List;

import com.sjb.wuwaechorank.entity.Attribute;
import com.sjb.wuwaechorank.entity.Weapon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResonatorDetailDto {
    int id; 
    String name;
    Attribute attribute;
    Weapon weapon;
    int star;
    String[] validStats = new String[5];
    String imagePath;
}
