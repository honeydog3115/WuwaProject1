package com.sjb.wuwaechorank.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.type.CollectionType;

@TestComponent
public class ControllerTestUtil {
    private ObjectMapper objectMapper;

    public ControllerTestUtil(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public <T> void validateListTypeResponse(MockMvc mockMvc, String uri, Class<T> listType ,List<T> expected){
        try {
            String content = mockMvc.perform(get(uri).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, listType);
            List<T> response = objectMapper.readValue(content, type);

            assertThat(response).usingRecursiveComparison().isEqualTo(expected);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
