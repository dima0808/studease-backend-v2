package tech.studease.studease.api.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginRequestDto {

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Password is mandatory")
  private String password;
}
