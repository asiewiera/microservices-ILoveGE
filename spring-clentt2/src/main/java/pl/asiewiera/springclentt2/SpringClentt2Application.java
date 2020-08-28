package pl.asiewiera.springclentt2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringClentt2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringClentt2Application.class, args);
    }

}
