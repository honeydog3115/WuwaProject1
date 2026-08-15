package com.sjb.wuwaechorank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.sjb.wuwaechorank.util.ControllerTestUtil;

@WebMvcTest(ResonatorController.class)
@Import(ControllerTestUtil.class)
public class ResonatorControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ControllerTestUtil controllerTestUtil;

}