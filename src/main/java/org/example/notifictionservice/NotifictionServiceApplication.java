package org.example.notifictionservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotifictionServiceApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("MAIL_USER", dotenv.get("MAIL_USER"));
        System.setProperty("MAIL_PASS", dotenv.get("MAIL_PASS"));
        System.setProperty("MAIL_HOST", dotenv.get("MAIL_HOST"));
        System.setProperty("MAIL_PORT", dotenv.get("MAIL_PORT"));

        System.setProperty("spring_rabbitmq_username", dotenv.get("spring_rabbitmq_username"));
        System.setProperty("spring_rabbitmq_password", dotenv.get("spring_rabbitmq_password"));

        SpringApplication.run(NotifictionServiceApplication.class, args);
    }
}
