package com.banking.assignment.payload.response;

import java.util.ArrayList;
import java.util.List;

@lombok.Getter
@lombok.Setter
public class ValidationErrorResponse {
    private List<Violation> violations = new ArrayList<>();
}
