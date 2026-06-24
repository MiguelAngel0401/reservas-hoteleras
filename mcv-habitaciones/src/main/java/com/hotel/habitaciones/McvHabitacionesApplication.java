package com.hotel.habitaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.hotel.habitaciones", "com.hotel.commons"})
@EnableFeignClients
public class McvHabitacionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(McvHabitacionesApplication.class, args);
    }

}