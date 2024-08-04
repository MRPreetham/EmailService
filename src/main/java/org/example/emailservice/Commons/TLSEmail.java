package org.example.emailservice.Commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.emailservice.Dtos.SendEmailEventDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
@Component
public class TLSEmail {
    private SendEmailEventDto emailEventDto;
    private ObjectMapper objectMapper;

    private TLSEmail(SendEmailEventDto emailEventDto,
                     ObjectMapper objectMapper){
        this.emailEventDto = emailEventDto;
        this.objectMapper = objectMapper;
    }
    public void sendEmail(String message) {

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        try {
            emailEventDto = objectMapper.readValue(message,SendEmailEventDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailEventDto.getFrom(), "");
            }
        };
        Session session = Session.getInstance(props,auth);
        EmailUtil.sendEmail(session, emailEventDto.getTo(), emailEventDto.getSubject(), emailEventDto.getBody());

    }


}
