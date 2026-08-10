package com.sjb.wuwaechorank.service.attribute;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.attribute.AttributeDao;
import com.sjb.wuwaechorank.entity.Attribute;

// 공명자 속성 관련 서비스 구현 클래스
@Service
public class AttributeServiceImpl implements AttributeService{
    private AttributeDao attributeDao;

    public AttributeServiceImpl(AttributeDao attributeDao){
        this.attributeDao = attributeDao;
    }
    
    @Override
    public List<Attribute> getAllAttributes() {
        List<Attribute> attributes = this.attributeDao.getAll();
        return attributes;
    }
}
