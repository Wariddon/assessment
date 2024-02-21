package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.service.TicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    TicketService ticketService;

    @GetMapping("/lotteries")
    public ResponseEntity<Map<String, Object>> lotteries() {
        List<String> tickets = ticketService.getLotteries();
        Map<String, Object> response = new HashMap<>();
        response.put("tickets", tickets);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/lotteries")
    public ResponseEntity<Map<String, String>> lotteries(@Valid @RequestBody LotteryRequestDto requestDto) throws Exception {

        Map<String, String> response = new HashMap<>();
        response.put("ticket", ticketService.createLottery(requestDto));
        return ResponseEntity.ok(response);
    }

}
