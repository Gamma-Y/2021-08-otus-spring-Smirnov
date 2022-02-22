package ru.otus.spring.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.Car;
import ru.otus.spring.integration.domain.WheelType;

@Service
public class TireService {

    public Car changeWheel(Car car) throws InterruptedException {
        System.out.println("Changing the " + car.getWheelType() + " tires on a " + car.getBrand() + " car ");
        Thread.sleep(5000);
        if (car.getWheelType().equals(WheelType.WINTER)) {
            car.setWheelType(WheelType.SUMMER);
        } else {
            car.setWheelType(WheelType.WINTER);
        }
        System.out.println("Wheels on a " + car.getBrand() + " car replaced by " + car.getWheelType() + " ones");
        return car;
    }
}
