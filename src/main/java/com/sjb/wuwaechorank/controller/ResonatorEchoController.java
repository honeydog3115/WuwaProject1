package com.sjb.wuwaechorank.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sjb.wuwaechorank.service.resonator.ResonatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
public class ResonatorEchoController {
    private ResonatorService resonatorService;

    public ResonatorEchoController(ResonatorService resonatorService){
        this.resonatorService = resonatorService;
    }

    @PostMapping("path")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
}
