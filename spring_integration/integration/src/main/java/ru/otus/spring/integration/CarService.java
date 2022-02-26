package ru.otus.spring.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.integration.domain.Car;

@MessagingGateway
public interface CarService {
    @Gateway(requestChannel = "entranceCarChannel", replyChannel = "awayCarChannel")
    Car process(Car car);
}
