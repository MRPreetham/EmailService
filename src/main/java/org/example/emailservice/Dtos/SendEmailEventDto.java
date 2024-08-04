package org.example.emailservice.Dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SendEmailEventDto {
    private String body;
    private String subject;
    private String to;
    private String from;
}
