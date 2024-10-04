//package com.ingress2.userms.kafka;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONObject;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
//import static com.ingress2.userms.kafka.KafkaTopicConfiguration.TOPIC_USER_REG_EVENTS;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class KafkaListenerService {
//
//    private final EmailService notificationService;
//
//    @KafkaListener(topics = TOPIC_USER_REG_EVENTS, groupId = "user_ms")
//    public void consumeMessage(@Payload String message,
//                               @Header(KafkaHeaders.PARTITION) int partition) {
//        log.info("Received message: {}", message);
//
//        JSONObject jsonMessage = new JSONObject(message);
//        String email = jsonMessage.getString("email");
//        String subject = jsonMessage.getString("subject");
//        String body = jsonMessage.getString("body");
//
//        notificationService.sendRegistrationEmail(email, subject, body);
//    }
//}
