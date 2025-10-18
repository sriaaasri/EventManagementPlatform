package com.hanuman.event.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.hanuman.event.Exception.QRCodeNotFoundException;
import com.hanuman.event.Exception.QrCodeGenerationException;
import com.hanuman.event.entity.domain.QrCode;
import com.hanuman.event.entity.domain.Ticket;
import com.hanuman.event.entity.enums.QRCodeStatus;
import com.hanuman.event.repository.QRCodeRepo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class QRCodeService {

    private static final int QR_HEIGHT = 300;
    private static final int QR_WIDTH = 300;

    private QRCodeWriter qrCodeWriter;
    private QRCodeRepo qrCodeRepo;

    @Transactional(propagation = Propagation.REQUIRED)
    public QrCode generateQrCode(Ticket ticket){
                //  throw new RuntimeException("QRCode service throws exception for ticketID:- "+ ticket.getId());
                // error(ticket);
        try{
            //  throw new RuntimeException("QRCode service throws exception for ticketID:- "+ ticket.getId());
        UUID uniqueId = UUID.randomUUID();

        String qrCodeImage = generateQrCodeImage(uniqueId);
        QrCode qrCode = QrCode.builder()
                                .id(uniqueId)
                                .status(QRCodeStatus.ACTIVE)
                                .value(qrCodeImage)
                                .ticket(ticket)
                                .build();
                                
        return qrCodeRepo.saveAndFlush(qrCode);

        }catch(IOException | WriterException  ex){
            throw new QrCodeGenerationException("Generation of QRCODE failed for the ticket: "+ ticket.getId());
        }
    }
    // @Transactional(propagation = Propagation.REQUIRED)
    private void error(Ticket ticket){
         throw new RuntimeException("QRCode service throws exception for ticketID:- "+ ticket.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private String generateQrCodeImage(UUID uniqueId) throws WriterException, IOException {
        //bitmatrix for a qr code
        BitMatrix bitMatrix = qrCodeWriter.encode(uniqueId.toString(), BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);
        
         BufferedImage qrCodeImage=  MatrixToImageWriter.toBufferedImage(bitMatrix);

         try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(qrCodeImage,"PNG",baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
         }

    }

    public  byte[] getQRCodeImageforTicket(Long ticketId){

        QrCode qrCode = qrCodeRepo.findByTicketId(ticketId).orElseThrow(()-> new QRCodeNotFoundException("qr not found"));
        try {
            return Base64.getDecoder().decode(qrCode.getValue());
        }
            catch(IllegalArgumentException ex) {
            log.error("Invalid base64 QR Code for ticket ID: {}", ticketId, ex);
            throw new QRCodeNotFoundException("Not found");
        }
    }

    public QrCode validateQrCodeEntity(UUID id , String errorMessage){

        return qrCodeRepo.findById(id)
                        .orElseThrow(()-> new QRCodeNotFoundException(errorMessage.isEmpty() ? "QRCode not found " : errorMessage));

    }


}
