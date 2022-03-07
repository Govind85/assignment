package com.banking.assignment.controller;

import com.banking.assignment.payload.request.ViewStatementRequest;
import com.banking.assignment.security.services.StatementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/statement")
@Validated
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class StatementController {
    @Autowired
    private StatementService statementService;
    @PostMapping("/statements")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(security = {
            @SecurityRequirement(name = "bearerAuth"),
    })
    public ResponseEntity<?> fetchAllStatements(@Valid @RequestBody ViewStatementRequest viewStatementRequest) {
        return new ResponseEntity<>(statementService.fetchStatements(viewStatementRequest), HttpStatus.OK);
    }
    @GetMapping("/")
    @Operation(security = {
            @SecurityRequirement(name = "bearerAuth"),
    })
    public ResponseEntity<?> fetchUserStatement(@RequestParam @NotEmpty @Pattern(regexp = "[\\d]{13}") String accountNumber) {

        return new ResponseEntity<>(statementService.fetchStatements(accountNumber),HttpStatus.OK);
    }
}
