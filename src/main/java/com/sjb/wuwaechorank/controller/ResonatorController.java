package com.sjb.wuwaechorank.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjb.wuwaechorank.dto.ResonatorsInfoDto;
import com.sjb.wuwaechorank.service.resonator.ResonatorService;


@RestController
public class ResonatorController {
    private ResonatorService resonatorService;

    public ResonatorController(ResonatorService resonatorService){
        this.resonatorService = resonatorService;
    }

    @GetMapping("/resonator")
    public List<ResonatorsInfoDto> getMethodName() {
        return resonatorService.getAllResonatorInfo();
    }
    
    
}