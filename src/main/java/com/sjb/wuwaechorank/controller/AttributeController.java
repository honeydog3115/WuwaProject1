package com.sjb.wuwaechorank.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjb.wuwaechorank.entity.Attribute;
import com.sjb.wuwaechorank.service.attribute.AttributeService;


@RestController
public class AttributeController {
    private AttributeService attributeService;

    public AttributeController(AttributeService attributeService){
        this.attributeService = attributeService;
    }

    @GetMapping("/attribute")
    public List<Attribute> getAttributes() {
        return attributeService.getAllAttributes();
    }
}
