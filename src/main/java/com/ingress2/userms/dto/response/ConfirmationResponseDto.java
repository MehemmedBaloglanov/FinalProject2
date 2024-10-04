package com.ingress2.userms.dto.response;

import com.ingress2.userms.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmationResponseDto {
    private String confirmationPin;
}
