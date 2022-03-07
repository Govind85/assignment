package com.banking.assignment.payload.response;


@lombok.Getter
@lombok.AllArgsConstructor
public class Violation {

    private final String fieldName;

    private final String message;
}
