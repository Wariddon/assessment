package com.kbtg.bootcamp.posttest.dao;

import com.kbtg.bootcamp.posttest.dto.BuyerInterfaceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByTicketId(int ticketId);

    @Query(value = "SELECT l.* as price FROM user_ticket u JOIN lottery l on u.ticket_id = l.id WHERE user_id = :userId", nativeQuery = true)
    List<BuyerInterfaceDto> getBuyerTicket(long userId);

    @Query(value = "SELECT l.* FROM user_ticket u JOIN lottery l on u.ticket_id = l.id WHERE user_id = :userId and l.id = :ticketId", nativeQuery = true)
    Optional<BuyerInterfaceDto> getBuyerTicketByUserIdAndTicketId(long userId, long ticketId);

}