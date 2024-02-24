package com.kbtg.bootcamp.posttest;


import com.kbtg.bootcamp.posttest.controller.AdminController;
import com.kbtg.bootcamp.posttest.controller.TicketController;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PostTestApplicationTests {

    MockMvc mockMvc;
    @Mock
    TicketService ticketService;

    @BeforeEach
    void setUp() {

        AdminController adminController = new AdminController(ticketService);
        TicketController ticketController = new TicketController(ticketService);

        mockMvc = MockMvcBuilders.standaloneSetup(adminController, ticketController)
                .alwaysDo(print())
                .build();

    }

    @Test
    @DisplayName("can run /helloworld")
    void helloWorld() throws Exception {
        mockMvc.perform(get("/helloworld"))
                .andExpect(content().string("Hello World"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("admin can get /lotteries")
    void canGetLotteries() throws Exception {
        mockMvc.perform(get("/lotteries")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("admin can create lotteries /lotteries")
    void canCreateLottery() throws Exception {
        when(ticketService.createLottery(any())).thenReturn("111111");
        mockMvc.perform(
                        post("/admin/lotteries")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .content("{\"ticket\":\"111111\",\"price\":250,\"amount\":1}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket", is("111111")));
    }

    @Test
    @DisplayName("user can buy lottery //users/{userId}/lotteries/{ticketId}}")
    void buyLottery() throws Exception {
        when(ticketService.buyTicket(1111111111,1)).thenReturn("1");
        mockMvc.perform(post("/users/{userId}/lotteries/{ticketId}", "1111111111", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")));
    }

    @Test
    @DisplayName("user can get lottery //users/{userId}/lotteries/{ticketId}}")
    void getBuyerLottery() throws Exception {

        Map<String, Object> testTicket = Map.of(
                "ticket", "111111",
                "cost", 250,
                "count", 1
        );

        when(ticketService.getBuyerTicket(1111111111)).thenReturn(testTicket);

        mockMvc.perform(get("/users/{userId}/lotteries", "1111111111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket", is("111111")))
                .andExpect(jsonPath("$.cost", is(250)))
                .andExpect(jsonPath("$.count", is(1)));
    }


    @Test
    @DisplayName("user can sell lottery //users/{userId}/lotteries/{ticketId}}")
    void deleteBuyerLottery() throws Exception {
        when(ticketService.deleteBuyerTicket(1111111111,1)).thenReturn("111111");
        mockMvc.perform(delete("/users/{userId}/lotteries/{ticketId}", "1111111111", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket", is("111111")));
    }

}
