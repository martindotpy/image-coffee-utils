package xyz.cupscoffee.imagecoffeeutils.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Main class for the Spring Boot application.
 */
@EnableConfigServer
@SpringBootApplication
public class ImageCoffeeUtilsConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImageCoffeeUtilsConfigApplication.class, args);
    }
}
