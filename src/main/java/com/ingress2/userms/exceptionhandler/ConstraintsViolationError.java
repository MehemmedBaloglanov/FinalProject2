package com.ingress2.userms.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConstraintsViolationError {

    private String filed;
    private Object rejectedValue;
    private String errorMessage;
}
