package com.hanuman.event.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hanuman.event.entity.domain.TicketType;

import jakarta.persistence.LockModeType;

@Repository
public interface TicketTypeRepo extends JpaRepository<TicketType , Long> {


    @Query("SELECT tt FROM TicketType tt WHERE tt.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<TicketType> findByIdWithLock(@Param(value = "id") Long id);

     

}
