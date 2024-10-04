package com.ingress2.userms.rest;

import com.ingress2.userms.dto.request.RegistrationRequestDto;
import com.ingress2.userms.dto.response.ConfirmationResponseDto;
import com.ingress2.userms.dto.response.RegistrationResponseDto;
import com.ingress2.userms.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/1.0/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDto> registration(@Valid @RequestBody RegistrationRequestDto registrationRequestDto) {
        RegistrationResponseDto response = userService.registration(registrationRequestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/confirmation/activate/{userID}")
    public ResponseEntity<ConfirmationResponseDto> activate(@PathVariable("userID") UUID userID) {
        ConfirmationResponseDto confirmationResponseDto = userService.confirmation(userID);
        return ResponseEntity.ok(confirmationResponseDto);
    }
}
