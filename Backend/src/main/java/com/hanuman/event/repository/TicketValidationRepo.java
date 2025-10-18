package com.hanuman.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hanuman.event.entity.domain.TicketValidation;

@Repository
public interface TicketValidationRepo  extends JpaRepository<TicketValidation , Long>{
    

}
