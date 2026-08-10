package com.sjb.wuwaechorank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.sjb.wuwaechorank.dao.entity.attribute.AttributeDao;
import com.sjb.wuwaechorank.entity.Attribute;
import com.sjb.wuwaechorank.service.attribute.AttributeService;
import com.sjb.wuwaechorank.service.attribute.AttributeServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AttributeServiceTest {
    @Mock
    AttributeDao attributeDao;

    @InjectMocks
    AttributeService attributeService = new AttributeServiceImpl(this.attributeDao);

    Attribute attribute1;
    Attribute attribute2;
    Attribute attribute3;

    @BeforeEach
    void setUp(){
        this.attribute1 = new Attribute(1, "용융", "asdf/qwer/a.jpg");
        this.attribute2 = new Attribute(2, "회절", "asdf/qwer/b.jpg");
        this.attribute3 = new Attribute(3, "전도", "asdf/qwer/c.jpg");
    }

    // 서비스의 getAllAttributes 테스트
    @Test
    void getAllAttributes(){
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(attribute1);
        attributes.add(attribute2);
        attributes.add(attribute3);
        when(this.attributeDao.getAll()).thenReturn(attributes);

        assertThat(attributeService.getAllAttributes()).isEqualTo(attributes);
    }
}
