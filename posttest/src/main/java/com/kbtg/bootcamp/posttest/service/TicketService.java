package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dao.Lottery;
import com.kbtg.bootcamp.posttest.dao.LotteryRepository;
import com.kbtg.bootcamp.posttest.dao.User;
import com.kbtg.bootcamp.posttest.dao.UserRepository;
import com.kbtg.bootcamp.posttest.dto.BuyerInterfaceDto;
import com.kbtg.bootcamp.posttest.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketService {

    private final LotteryRepository lotteryRepository;
    private final UserRepository userRepository;


    public List<String> getLotteries() {
        List<String> ticketList = lotteryRepository.findAll().stream()
                .map(Lottery::getTicket)
                .collect(Collectors.toList());

        return ticketList;
    }

    public String createLottery(LotteryRequestDto requestDto) {


        Optional<Lottery> optionalLottery = lotteryRepository.findByTicket(requestDto.getTicket());

        if (optionalLottery.isPresent()) {
            Lottery lottery = optionalLottery.get();
            lottery.setAmount(optionalLottery.get().getAmount()+ requestDto.getAmount());
            lotteryRepository.save(lottery);
            return requestDto.getTicket();
        } else {
            Lottery lottery = new Lottery();
            lottery.setTicket(requestDto.getTicket());
            lottery.setPrice(requestDto.getPrice());
            lottery.setAmount(requestDto.getAmount());
            lotteryRepository.save(lottery);
        }

        return requestDto.getTicket();
    }

    public String buyTicket(int userId, int ticketId) {


        Optional<Lottery> optionalLottery = lotteryRepository.findById(Long.valueOf(ticketId));
        Optional<User> ticketAlreadyBuy = userRepository.findByTicketId(ticketId);

        if (!optionalLottery.isPresent()) {
            throw new NotFoundException("ticket id not found");
        }

        if (ticketAlreadyBuy.isPresent()) {
            throw new BadRequestException("this ticket has been sold out");
        }

        User user_ticket = new User();
        user_ticket.setTicketId(ticketId);
        user_ticket.setUserId(userId);
        userRepository.save(user_ticket);

        return String.valueOf(user_ticket.getId());
    }

    public String deleteBuyerTicket(int userId, int ticketId) {

        Optional<BuyerInterfaceDto> ticketAlreadyBuy = userRepository.getBuyerTicketByUserIdAndTicketId(userId, ticketId);

        if (!ticketAlreadyBuy.isPresent()) {
            throw new BadRequestException("Hey, you don't have this ticket, do you?");
        }
        userRepository.deleteById(Long.valueOf(ticketAlreadyBuy.get().getId()));
        return String.valueOf(ticketAlreadyBuy.get().getTicket());
    }

    public Map<String, Object> getBuyerTicket(int userId) {
        List<BuyerInterfaceDto> buyerTicket = userRepository.getBuyerTicket(userId);

        List<String> tickets = buyerTicket.stream()
                .map(ticket -> (String) ticket.getTicket())
                .collect(Collectors.toList());

        int totalCost = buyerTicket.stream()
                .mapToInt(ticket -> ticket.getPrice())
                .sum();

        int totalCount = buyerTicket.stream()
                .mapToInt(ticket -> ticket.getAmount())
                .sum();

        Map<String, Object> sumTicket = Map.of(
                "ticket", tickets,
                "cost", totalCost,
                "count", totalCount
        );

        return sumTicket;
    }

}
