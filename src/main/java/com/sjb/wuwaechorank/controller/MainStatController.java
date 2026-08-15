package com.sjb.wuwaechorank.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjb.wuwaechorank.entity.MainStat;
import com.sjb.wuwaechorank.service.mainstat.MainStatService;


@RestController
public class MainStatController {
    private MainStatService mainStatService;

    public MainStatController(MainStatService mainStatService){
        this.mainStatService = mainStatService;
    }

    @GetMapping("/mainstat")
    public List<MainStat> getMethodName() {
        return mainStatService.getAllMainStats();
    }
    
}
