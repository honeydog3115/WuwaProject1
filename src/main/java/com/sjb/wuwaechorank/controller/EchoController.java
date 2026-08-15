package com.sjb.wuwaechorank.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjb.wuwaechorank.dto.EchoInfoGroupBySonataEffectDto;
import com.sjb.wuwaechorank.entity.SonataEffect;
import com.sjb.wuwaechorank.service.echo.EchoService;



@RestController
public class EchoController {
    private EchoService echoService;

    public EchoController(EchoService echoService){
        this.echoService = echoService;
    }

    @GetMapping("/echo")
    public List<EchoInfoGroupBySonataEffectDto> getEchos() {
        return echoService.getAllEchos();
    }
    
    @GetMapping("path")
    public List<SonataEffect> getEchoFilter() {
        return echoService.getEchoFilter();
    }
}
