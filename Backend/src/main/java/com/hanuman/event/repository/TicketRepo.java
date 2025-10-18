package com.hanuman.event.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hanuman.event.entity.domain.Ticket;
import com.hanuman.event.entity.domain.User;


public interface TicketRepo extends JpaRepository<Ticket , Long>{

    @Query(value = "SELECT COUNT(*) from tickets t where t.ticket_type_id = :ticketTypeId" , nativeQuery = true)
    long countByTicketTypeId(@Param(value = "ticketTypeId") Long ticketTypeId);

    @Query(value = "SELECT *  FROM tickets t where t.purchaser_id = :id " , nativeQuery = true)
    List<Ticket> getTicketsBypurchaserId(@Param(value = "id") Long id);

    Optional<Ticket> findByPurchaserAndId(User purchaser, Long id);

}
