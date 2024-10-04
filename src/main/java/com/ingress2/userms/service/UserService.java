package com.ingress2.userms.service;

import com.ingress2.userms.dto.request.RegistrationRequestDto;
import com.ingress2.userms.dto.response.ConfirmationResponseDto;
import com.ingress2.userms.dto.response.RegistrationResponseDto;
import jakarta.validation.Valid;

import java.util.UUID;

public interface UserService {
    RegistrationResponseDto registration(@Valid RegistrationRequestDto registrationRequestDto);

    ConfirmationResponseDto confirmation(UUID userID);
}
