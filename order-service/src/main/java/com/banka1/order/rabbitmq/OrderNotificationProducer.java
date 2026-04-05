package com.banka1.order.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderNotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    public void sendOrderApproved(Object payload) {
        rabbitTemplate.convertAndSend(exchange, "order.approved", payload);
    }

    public void sendOrderDeclined(Object payload) {
        rabbitTemplate.convertAndSend(exchange, "order.declined", payload);
    }

    public void sendTaxCollected(Object payload) {
        rabbitTemplate.convertAndSend(exchange, "tax.collected", payload);
    }
}
