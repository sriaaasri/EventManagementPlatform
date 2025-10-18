package com.hanuman.event.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.zxing.qrcode.QRCodeWriter;

@Configuration
public class ApplicationConfig {

    @Bean
    public Logger getLogger(){
        return LoggerFactory.getLogger(ApplicationConfig.class);
    }

    @Bean
    public QRCodeWriter qrCodeWriter(){
        return new QRCodeWriter();
    }

}
