package com.wishes.postmanbe.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wishes.postmanbe.model.Mail;
import com.wishes.postmanbe.service.MailingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;

@Component
@Slf4j
public class RabbitMqReceiver {
    private final MailingService mailingService;
    private final ObjectMapper mapper;

    public RabbitMqReceiver(@Lazy MailingService mailingService) {
        this.mailingService = mailingService;
        this.mapper = new ObjectMapper();
    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void receiveMessage(String jsonStr) {
        try {
            Mail mail = mapper.readValue(jsonStr, Mail.class);
            mailingService.deliver(mail);
        }
        catch (IOException e) {
            log.error(MessageFormat.format("Failed to parse mail from message: {0}", e));
        }
    }
}
