package com.sjb.wuwaechorank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.AttributeDao;

@Service
public class AttributeServiceImpl implements AttributeService {
    private AttributeDao attributeDao;

    // @Autowired
    public AttributeServiceImpl(AttributeDao attributeDao){
        this.attributeDao = attributeDao;
    }

    @Override
    public void add() {
    }
}
