package ru.otus.spring.integration.domain;

public class Car {
    private final String brand;
    private WheelType wheelType;

    public Car(String brand, WheelType wheelType) {
        this.brand = brand;
        this.wheelType = wheelType;
    }

    public String getBrand() {
        return brand;
    }

    public WheelType getWheelType() {
        return wheelType;
    }

    public void setWheelType(WheelType wheelType) {
        this.wheelType = wheelType;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", wheelType=" + wheelType +
                '}';
    }
}
