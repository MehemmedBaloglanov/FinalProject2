package com.ingress2.userms.kafka;

import com.ingress2.userms.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.ingress2.userms.kafka.KafkaTopicConfiguration.TOPIC_USER_REG_EVENTS;

@Component
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final EmailService emailService;

    @KafkaListener(topics = TOPIC_USER_REG_EVENTS, groupId = "user-ms")
    public void readMessages1(UserEntity userRegistrationEvent){
        System.out.println("result1: " +userRegistrationEvent);

        emailService.sendRegistrationEmail(userRegistrationEvent.getEmail()
                ,"Complete registration"
                ,userRegistrationEvent.getPin());
    }
}
