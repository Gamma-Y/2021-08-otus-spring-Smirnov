package ru.otus.spring.integration;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.integration.domain.Car;
import ru.otus.spring.integration.domain.WheelType;

import java.util.concurrent.ForkJoinPool;

@SpringBootApplication
public class App {
    private static final String[] CARS = {"Toyota", "Audi", "BMW", "FORD", "Volkswagen", "Tesla", "Lada", "Opel"};
    private static final WheelType[] WHEEL_TYPES = {WheelType.SUMMER, WheelType.WINTER};

    @Bean
    public QueueChannel entranceCarChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel awayCarChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(5).get();
    }

    @Bean
    public IntegrationFlow carServiceFlow() {
        return IntegrationFlows.from("entranceCarChannel")
                .handle("tireService", "changeWheel")
                .channel("awayCarChannel")
                .get();
    }

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);

        CarService carService = ctx.getBean(CarService.class);

        ForkJoinPool pool = ForkJoinPool.commonPool();

        while (true) {
            Thread.sleep(1000);

            pool.execute(() -> {
                Car car = generateCar();
                System.out.println("New car: " + car);
                car = carService.process(car);
                System.out.println("Ready car: " + car);
            });
        }
    }

    private static Car generateCar() {
        return new Car(CARS[RandomUtils.nextInt(0, CARS.length)], WHEEL_TYPES[RandomUtils.nextInt(0, WHEEL_TYPES.length)]);
    }


}
