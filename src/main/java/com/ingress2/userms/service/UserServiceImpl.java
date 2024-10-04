package com.ingress2.userms.service;

import com.ingress2.userms.dto.request.RegistrationRequestDto;
import com.ingress2.userms.dto.response.ConfirmationResponseDto;
import com.ingress2.userms.dto.response.RegistrationResponseDto;
import com.ingress2.userms.entity.UserEntity;
import com.ingress2.userms.exception.InvalidException;
import com.ingress2.userms.exception.UserNotFoundException;
import com.ingress2.userms.kafka.KafkaProducerService;
import com.ingress2.userms.repository.UserRepository;
import com.ingress2.userms.status.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final KafkaProducerService kafkaProducerService;

    private final String kafkaTopic = "notification_topic";

    @Override
    public RegistrationResponseDto registration(RegistrationRequestDto registrationRequestDto) {

        // Mövcud istifadəçi olub-olmadığını yoxlayırıq
        Optional<UserEntity> userOptional = userRepository.findByEmail(registrationRequestDto.getEmail());
        UserEntity user = null;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (user.getStatus() == UserStatus.EXPIRED) {
                user.setPin(generateRandomPin());
                user.setPinExpirationTime(LocalDateTime.now().plus(Duration.ofDays(2)));
                userRepository.save(user);
            } else if (user.getStatus() == UserStatus.NEW) {
                if (!isPinValid(user.getPinExpirationTime())) {
                    user.setPin(generateRandomPin());
                    user.setPinExpirationTime(LocalDateTime.now().plus(Duration.ofDays(2)));
                    userRepository.save(user);
                } else {
                    throw new RuntimeException("User already exists, check your email");
                }
            } else {
                throw new InvalidException("Email already in use");
            }
        } else {
            LocalDateTime now = LocalDateTime.now();
            // Yeni istifadəçi yaradılır
            user = UserEntity.builder()
                    .userId(UUID.randomUUID())  // Unikal ID yaradılır
                    .firstName(registrationRequestDto.getFirstName())
                    .lastName(registrationRequestDto.getLastName())
                    .email(registrationRequestDto.getEmail())
                    .pin(generateRandomPin())
                    .createAt(now)
                    .pinExpirationTime(now.plus(Duration.ofMinutes(2)))
                    .status(UserStatus.NEW)
                    .build();
        }

        UserEntity savedUser = userRepository.save(user);

        kafkaProducerService.sendMessage(kafkaTopic, savedUser);

        return RegistrationResponseDto.builder()
                .userId(savedUser.getUserId())  // Bazaya yazılmış ID göndərilir
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .pin(savedUser.getPin())
                .createAt(savedUser.getCreateAt().toString())
                .pinExpirationTime(savedUser.getPinExpirationTime().toString())
                .status(savedUser.getStatus().toString())
                .build();
    }

    @Override
    public ConfirmationResponseDto confirmation(UUID userID) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(userID);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        UserEntity userEntity = userOptional.get();

        // PIN-in vaxtının keçib-keçmədiyini yoxlayırıq
        if (!isPinValid(userEntity.getPinExpirationTime())) {
            userEntity.setStatus(UserStatus.EXPIRED);
            userRepository.save(userEntity);
            throw new InvalidException("User's pin is expired");
        }

        // İstifadəçi aktivləşdirilir
        userEntity.setStatus(UserStatus.ACTIVATED);
        userEntity.setPin(userEntity.getPin());
        UserEntity savedUser = userRepository.save(userEntity);

        return ConfirmationResponseDto.builder()
                .confirmationPin(savedUser.getPin())
                .build();
    }

    private String generateRandomPin() {
        SecureRandom random = new SecureRandom();
        int pin = random.nextInt(900000) + 100000;
        return String.valueOf(pin);
    }

    private boolean isPinValid(LocalDateTime expireTime) {
        return LocalDateTime.now().isBefore(expireTime);
    }
}
