package com.sjb.wuwaechorank.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sjb.wuwaechorank.dto.request.ResonatorEchoScoreRequest;
import com.sjb.wuwaechorank.service.resonatorecho.ResonatorEchoService;

@RestController
public class ResonatorEchoController {
    private ResonatorEchoService resonatorEchoService;

    public ResonatorEchoController(ResonatorEchoService resonatorEchoService){
        this.resonatorEchoService = resonatorEchoService;
    }

    @PostMapping("/resonatorecho")
    public double getResonatorEchoScore(@RequestBody ResonatorEchoScoreRequest request) {
        return resonatorEchoService.getResonatorEchoScore(
            request.id(), 
            request.resonatorEchoInfoDtos(), 
            request.insertDB(), 
            request.presetId());
    }
}
