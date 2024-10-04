package com.ingress2.userms.kafka;

import com.ingress2.userms.entity.UserEntity;
import lombok.RequiredArgsConstructor;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;


    @Value("${user.service.url}")
    private String url;

    public void sendMessage(String topic, UserEntity user) {

        String confirmationUrl = url + "/confirmation/activate/" + user.getUserId();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email" , user.getEmail());
        jsonObject.put("subject","Registration Confirmation");
        jsonObject.put("body", "Dear " + user.getFirstName() + ",\nPlease confirm your registration using this link: " + confirmationUrl);

        kafkaTemplate.send(topic, jsonObject.toString());
    }

}

