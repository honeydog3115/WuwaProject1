package com.sjb.wuwaechorank.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjb.wuwaechorank.dto.SubStatWithSubStatInfoDto;
import com.sjb.wuwaechorank.service.substat.SubStatService;


@RestController
public class SubStatController {
    private SubStatService subStatService;
    public SubStatController(SubStatService subStatService){
        this.subStatService = subStatService;
    }

    @GetMapping("/substat")
    public List<SubStatWithSubStatInfoDto> getSubStats() {
        return subStatService.getAllSubStatsWithSubStatInfo();
    }
    
}
