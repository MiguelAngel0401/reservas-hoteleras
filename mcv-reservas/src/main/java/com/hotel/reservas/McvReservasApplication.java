package com.hotel.reservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.hotel.reservas","com.hotel.commons"})
public class McvReservasApplication {

    public static void main(String[] args) {
        SpringApplication.run(McvReservasApplication.class, args);
    }

}