package xyz.cupscoffee.imagecoffeeutils.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Main class for the Spring Boot application.
 */
@EnableEurekaServer
@SpringBootApplication
public class ImageCoffeeUtilsEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImageCoffeeUtilsEurekaApplication.class, args);
    }
}
