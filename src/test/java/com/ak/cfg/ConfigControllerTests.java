package com.ak.cfg;

import com.ak.cfg.controller.ConfigController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ConfigController.class)
public class ConfigControllerTests {

    @Autowired private MockMvc mvc;
    @MockBean private ConfigController configController;

    @Before
    @Test
    public void shouldAddDocument() throws Exception {
        String URL = "/api/test%d/config/%d";
        String json = "{\"test\":\"test%d-%d\"}";
        for (int appCode=1; appCode<3; appCode++) {
            for (int version=1; version<5; version++) {
                mvc.perform(post(String.format(URL, appCode, version))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(json, appCode, version)))
                        .andExpect(status().isOk());
            }
        }
    }

    @Test
    public void shouldReturnDocument() throws Exception {
        mvc.perform(get("/api/test2/config/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnVersionsByAppCode() throws Exception {
        mvc.perform(get("/api/test2/config")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateDocument() throws Exception {
        String json = "{\"test\":\"test2-3\"}";
        mvc.perform(post("/api/test2/config/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}