package tech.studease.studease.api.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequestDto {

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "First name is mandatory")
  private String firstName;

  @NotBlank(message = "Last name is mandatory")
  private String lastName;

  @NotBlank(message = "Password is mandatory")
  @Size(min = 6, max = 35, message = "Password must be between 6 and 35 characters")
  private String password;
}
