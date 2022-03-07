package com.banking.assignment.payload.request;

import lombok.*;

import java.util.Set;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignupRequest {
  @NotBlank
  @Size(min = 4, max = 20)
  private String username;
  @NotBlank
  private Set<String> role;

  @NotBlank
  @Size(min = 4, max = 40)
  private String password;

}
