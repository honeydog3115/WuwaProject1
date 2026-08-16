package com.sjb.wuwaechorank.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sjb.wuwaechorank.dto.request.ResonatorEchoScoreRequest;
import com.sjb.wuwaechorank.service.resonatorecho.EchoScoreService;

@RestController
public class ResonatorEchoController {
    private EchoScoreService echoScoreService;

    public ResonatorEchoController(EchoScoreService echoScoreService){
        this.echoScoreService = echoScoreService;
    }

    @PostMapping("/resonatorecho")
    public double getResonatorEchoScore(@RequestBody ResonatorEchoScoreRequest request) {
        return echoScoreService.getResonatorEchoScore(
            request.id(), 
            request.resonatorEchoInfoDtos(), 
            request.insertDB(), 
            request.presetId());
    }
}
