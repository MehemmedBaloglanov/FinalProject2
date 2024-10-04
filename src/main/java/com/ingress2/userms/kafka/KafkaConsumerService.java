//package com.ingress2.userms.kafka;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class KafkaConsumerService {
//    private final EmailService emailService;
//
//    @KafkaListener(topics = TOPIC_USER_REG_EVENTS, groupId = "user-ms")
//    public void readMessages1(RegistrationEntity userRegistrationEvent){
//        System.out.println("result1: " +userRegistrationEvent);
//
//        emailService.sendRegistrationEmail(userRegistrationEvent.getEmail()
//                ,"Complete registration"
//                ,userRegistrationEvent.getPin());
//    }
//}
