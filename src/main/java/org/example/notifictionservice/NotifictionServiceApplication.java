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

        System.setProperty("RABBITMQ_USER", dotenv.get("RABBITMQ_USER"));
        System.setProperty("RABBITMQ_PASSWORD", dotenv.get("RABBITMQ_PASSWORD"));

        SpringApplication.run(NotifictionServiceApplication.class, args);
    }
}
