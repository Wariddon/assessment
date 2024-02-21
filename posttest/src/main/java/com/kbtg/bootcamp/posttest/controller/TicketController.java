package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class TicketController {

    TicketService ticketService;

    @GetMapping("/helloworld")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/lotteries")
    public ResponseEntity<Map<String, Object>> lotteries() {
        List<String> tickets = ticketService.getLotteries();
        Map<String, Object> response = new HashMap<>();
        response.put("tickets", tickets);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, Object>> buyLottery(
            @PathVariable(value = "userId") int userId,
            @PathVariable(value = "ticketId") int ticketId
    ) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", ticketService.buyTicket(userId, ticketId));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, Object>> deleteBuyerLottery(
            @PathVariable(value = "userId") int userId,
            @PathVariable(value = "ticketId") int ticketId
    ) {
        Map<String, Object> response = new HashMap<>();
        response.put("ticket", ticketService.deleteBuyerTicket(userId, ticketId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/lotteries")
    public ResponseEntity<Map<String, Object>> buyGetLottery(@PathVariable(value = "userId") int userId) {
        return ResponseEntity.ok(ticketService.getBuyerTicket(userId));
    }

}
