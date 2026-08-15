package com.sjb.wuwaechorank.controller;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.sjb.wuwaechorank.entity.Attribute;
import com.sjb.wuwaechorank.service.attribute.AttributeService;
import com.sjb.wuwaechorank.util.ControllerTestUtil;

@WebMvcTest(AttributeController.class)
@Import(ControllerTestUtil.class)
public class AttributeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AttributeService attributeService;

    @Autowired
    ControllerTestUtil controllerTestUtil;

    Attribute attribute1; 
    Attribute attribute2; 
    Attribute attribute3;

    @BeforeEach
    void setUp(){
        this.attribute1 = new Attribute(1, "용융", "asdf/qwer/a.jpg");
        this.attribute2 = new Attribute(2, "회절", "asdf/qwer/b.jpg");
        this.attribute3 = new Attribute(3, "전도", "asdf/qwer/c.jpg");
    }

    @Test
    void getAttributes() throws Exception{
        given(this.attributeService.getAllAttributes()).willReturn(List.of(this.attribute1, this.attribute2, this.attribute3));
        controllerTestUtil.validateListTypeResponse(mockMvc, "/attribute", Attribute.class, List.of(this.attribute1, this.attribute2, this.attribute3));
    }
}
