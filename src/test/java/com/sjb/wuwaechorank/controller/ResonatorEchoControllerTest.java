package com.sjb.wuwaechorank.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ResonatorEchoController.class)
@ExtendWith(MockitoExtension.class)
public class ResonatorEchoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ResonatorEchoController resonatorEchoController;

    @Test
    void getResonatorEchoScore() throws Exception{

        String request = """
        {
            "id":1,
            "resonatorEchoInfoDtos":[
                {
                    "echoId":1,
                    "mainStatId":1,
                    "echoSubStats":[
                        {
                            "subStatId":1,
                            "subStatInfoId":1,
                            "value":"10%",
                            "chance":"10%"
                        },
                        {
                            "subStatId":2,
                            "subStatInfoId":2,
                            "value":"20%",
                            "chance":"20%"
                        }
                    ]
                },
                {
                    "echoId":2,
                    "mainStatId":2,
                    "echoSubStats":[
                        {
                            "subStatId":1,
                            "subStatInfoId":1,
                            "value":"20%",
                            "chance":"20%"
                        }
                    ]
                }
            ],
            "insertDB":false,
            "presetId":1
        }
        """;

        mockMvc.perform(post("/resonatorecho")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk());
    }
}
