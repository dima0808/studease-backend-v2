package tech.studease.studease.api.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.studease.studease.api.users.dto.UserDto;
import tech.studease.studease.api.users.dto.UserJwtTokenDto;
import tech.studease.studease.api.users.dto.UserLoginRequestDto;
import tech.studease.studease.api.users.dto.UserRegisterRequestDto;
import tech.studease.studease.application.users.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<UserJwtTokenDto> register(
      @RequestBody @Valid UserRegisterRequestDto registerDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerDto));
  }

  @PostMapping("/login")
  public ResponseEntity<UserJwtTokenDto> login(@RequestBody @Valid UserLoginRequestDto loginDto) {
    return ResponseEntity.ok(authService.login(loginDto));
  }

  @GetMapping("/current")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<UserDto> getCurrentUser() {
    return ResponseEntity.ok(authService.getCurrentUser());
  }
}
