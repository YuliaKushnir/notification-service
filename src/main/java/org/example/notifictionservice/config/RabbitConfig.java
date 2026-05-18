package org.example.notifictionservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for RabbitMQ mail queue.
 * Defines exchange, queue, binding, and JSON message converter.
 */
@EnableRabbit
@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "order-processing-exchange";
    public static final String QUEUE_NAME = "create-order-queue";
    public static final String ROUTING_KEY = "order.created.#";

    /**
     * Declares the mail queue
     * @return a durable queue
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    /**
     * Declares the topic exchange
     * @return a topic exchange
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    /**
     * Binds the queue to the exchange with routing key.
     *
     * @param mailQueue the queue
     * @param exchange the exchange
     * @return binding
     */
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    /**
     * Configures JSON message converter for RabbitMQ.
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
