package org.example.emailservice.Consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.emailservice.Commons.EmailUtil;
import org.example.emailservice.Commons.TLSEmail;
import org.example.emailservice.Dtos.SendEmailEventDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;
@Service
public class SendEmailEventConsumer {
    private ObjectMapper objectMapper;
    private TLSEmail tlsEmail;

    private SendEmailEventConsumer(ObjectMapper objectMapper,
                                   TLSEmail tlsEmail){
        this.objectMapper = objectMapper;
        this.tlsEmail = tlsEmail;
    }

    @KafkaListener(topics = "sendEmail",groupId = "emailService")
    private void handleSendEmailEvent(String message){
        tlsEmail.sendEmail(message);
    }
}
