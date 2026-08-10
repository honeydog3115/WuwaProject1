package com.sjb.wuwaechorank.service.attribute;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.attribute.AttributeDao;
import com.sjb.wuwaechorank.entity.Attribute;

@Service
public class AttributeServiceImpl implements AttributeService{
    private AttributeDao attributeDao;

    public AttributeServiceImpl(AttributeDao attributeDao){
        this.attributeDao = attributeDao;
    }
    
    @Override
    public List<Attribute> getAllAttribute() {
        

        return null;
    }
}
