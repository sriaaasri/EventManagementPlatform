package com.hanuman.event.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hanuman.event.entity.domain.QrCode;


@Repository
public interface QRCodeRepo extends JpaRepository<QrCode , UUID> {

    Optional<QrCode>  findByTicketId(Long ticketId);

}
