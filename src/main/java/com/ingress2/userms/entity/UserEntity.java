package com.ingress2.userms.entity;

import com.ingress2.userms.status.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

    @Id
    private String id;

    @NotBlank
    @ColumnTransformer(write = "trim(?)")
    private String firstName;

    @NotBlank
    @ColumnTransformer(write = "trim(?)")
    private String lastName;

    @NotBlank
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDateTime created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at",nullable = false)
    @UpdateTimestamp
    private Date updatedAt;

    private String pin;

}
