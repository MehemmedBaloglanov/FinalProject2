package com.ingress2.userms.entity;

import com.ingress2.userms.status.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    private String firstName;

    private String lastName;

    private String email;

    private String pin;

    private LocalDateTime createAt;

    private LocalDateTime pinExpirationTime;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
