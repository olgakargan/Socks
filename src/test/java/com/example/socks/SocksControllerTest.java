package com.example.socks;

import com.example.socks.controller.SocksController;
import com.example.socks.entity.SocksBatch;
import com.example.socks.enums.Operations;
import com.example.socks.service.SocksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SocksControllerTest {
    private MockMvc mockMvc;

    @Mock
    private SocksService socksServiceMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SocksController socksController = new SocksController(socksServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(socksController).build();
    }

    @Test
    public void testIncome() throws Exception {
        SocksBatch socksBatch = new SocksBatch();
        socksBatch.setColor("red");
        socksBatch.setCottonPart(80);
        socksBatch.setQuantity(20);
        when(socksServiceMock.income(socksBatch)).thenReturn(socksBatch);

        ResultActions resultActions = mockMvc.perform(post("/api/socks/income")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(socksBatch)));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.quantity", is(socksBatch.getQuantity())))
                .andExpect(jsonPath("$.color", is(socksBatch.getColor())))
                .andExpect(jsonPath("$.cottonPart", is(socksBatch.getCottonPart())));
    }

    @Test
    public void testOutcome() throws Exception {
        SocksBatch socksBatch = new SocksBatch();
        socksBatch.setColor("red");
        socksBatch.setCottonPart(80);
        socksBatch.setQuantity(10);
        when(socksServiceMock.outcome(socksBatch)).thenReturn(socksBatch);

        ResultActions resultActions = mockMvc.perform(post("/api/socks/outcome")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(socksBatch)));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.quantity", is(socksBatch.getQuantity())))
                .andExpect(jsonPath("$.color", is(socksBatch.getColor())))
                .andExpect(jsonPath("$.cottonPart", is(socksBatch.getCottonPart())));
    }

    @Test
    public void testGet() throws Exception {
        List<SocksBatch> socksBatchList = new ArrayList<>();
        SocksBatch socksBatch = new SocksBatch();
        socksBatch.setColor("red");
        socksBatch.setCottonPart(80);
        socksBatch.setQuantity(50);
        socksBatchList.add(socksBatch);
        when(socksServiceMock.get(anyString(), any(Operations.class), any())).thenReturn(50);

        ResultActions resultActions = mockMvc.perform(get("/api/socks?color=red&operation=equal&cottonPart=80")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(socksBatch)));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(50)));
    }

    //Метод для преобразования объекта Java в JSON строку
    private String asJsonString(Object object) {
        try {
            final var objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}