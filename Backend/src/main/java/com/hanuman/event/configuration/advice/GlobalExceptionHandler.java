package com.hanuman.event.configuration.advice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hanuman.event.Exception.EventNotFoundException;
import com.hanuman.event.Exception.EventUpdateException;
import com.hanuman.event.Exception.QrCodeGenerationException;
import com.hanuman.event.Exception.TicketNotFoundException;
import com.hanuman.event.Exception.TicketsSoldOutException;
import com.hanuman.event.Exception.UserNotFoundException;
import com.hanuman.event.dtos.ArgumentNotValidDto;
import com.hanuman.event.dtos.ErrorDto;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

     @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex)
    {
        ErrorDto errorDto = ErrorDto.builder()
                                    .error(ex.getClass().getTypeName())
                                    .errorCode(HttpStatus.NOT_ACCEPTABLE.value())
                                    .errorMessage(ex.getMessage())
                                    .build();
        return ResponseEntity.badRequest().body(errorDto);
    }

     @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEventNotFoundException(EventNotFoundException ex)
    {
        ErrorDto errorDto = ErrorDto.builder()
                                    .error(ex.getClass().getTypeName())
                                    .errorCode(HttpStatus.NOT_FOUND.value())
                                    .errorMessage(ex.getMessage())
                                    .build();
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.NOT_FOUND);
    }

     @ExceptionHandler(EventUpdateException.class)
    public ResponseEntity<ErrorDto> handleEventUpdateException(EventUpdateException ex)
    {
        ErrorDto errorDto = ErrorDto.builder()
                                    .error(ex.getClass().getTypeName())
                                    .errorCode(HttpStatus.NOT_IMPLEMENTED.value())
                                    .errorMessage(ex.getMessage())
                                    .build();
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.NOT_IMPLEMENTED);
    }

     @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTicketNotFoundException(TicketNotFoundException ex)
    {
        ErrorDto errorDto = ErrorDto.builder()
                                    .error(ex.getClass().getTypeName())
                                    .errorCode(HttpStatus.NOT_FOUND.value())
                                    .errorMessage(ex.getMessage())
                                    .build();
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.NOT_FOUND);
    }

    
     @ExceptionHandler(TicketsSoldOutException.class)
    public ResponseEntity<ErrorDto> handleTicketsSoldOutException(TicketsSoldOutException ex)
    {
        ErrorDto errorDto = ErrorDto.builder()
                                    .error(ex.getClass().getTypeName())
                                    .errorCode(HttpStatus.BAD_REQUEST.value())
                                    .errorMessage(ex.getMessage())
                                    .build();
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.BAD_REQUEST);
    }


         @ExceptionHandler(QrCodeGenerationException.class)
        public ResponseEntity<ErrorDto> handleQrCodeGenerationException(QrCodeGenerationException ex) {
                log.error("Caught QrCodeGenerationException", ex);
                ErrorDto errorDto = ErrorDto.builder()
                                            .errorCode(500)
                                            .error("QrCodeGenerationException")
                                            .errorMessage(ex.getLocalizedMessage())
                                            .build();

                // errorDto.setError("Unable to generate QR Code");
                return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

     @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ArgumentNotValidDto> handleException(MethodArgumentNotValidException ex)
    {
        List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getField()+" : "+error.getDefaultMessage())
                                .toList();

        ArgumentNotValidDto argumentNotValidDto = ArgumentNotValidDto.builder()
                                                                     .errorCode(HttpStatus.BAD_REQUEST.value())
                                                                     .error("MethodArgumentNotValidException")
                                                                     .errorMsg(errors)
                                                                     .build();
        return ResponseEntity.badRequest().body(argumentNotValidDto);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex)
    {
        ErrorDto errorDto = ErrorDto.builder()
                                    .error(ex.getClass().getTypeName())
                                    .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                    .errorMessage(ex.getMessage())
                                    .build();
        return ResponseEntity.internalServerError().body(errorDto);
    }



}
