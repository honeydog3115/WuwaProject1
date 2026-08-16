package com.sjb.wuwaechorank.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjb.wuwaechorank.dto.PresetInputInfoDto;
import com.sjb.wuwaechorank.dto.PresetOutputInfoDto;
import com.sjb.wuwaechorank.dto.SimplePresetInfoDto;
import com.sjb.wuwaechorank.service.preset.PresetService;

@RestController
@RequestMapping("/preset")
public class PresetController {
    private PresetService presetService;
    public PresetController(PresetService presetService){
        this.presetService = presetService;
    }

    @PostMapping("/save")
    public int savePreset(@RequestBody PresetInputInfoDto request) {
        return presetService.savePreset(request);
    }

    @GetMapping("/simplepresetsinfo/{userId}")
    public List<SimplePresetInfoDto> getSimplePresetsInfo(@PathVariable int userId) {
        return presetService.getSimplePresetInfo(userId);
    }
    
    @GetMapping("/{presetId}")
    public PresetOutputInfoDto getPresetInfo(@PathVariable int presetId) {
        return presetService.getPresetInfo(presetId);
    }   
}
