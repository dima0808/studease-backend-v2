package tech.studease.studease.application.users;

import tech.studease.studease.api.users.dto.UserDto;
import tech.studease.studease.api.users.dto.UserJwtTokenDto;
import tech.studease.studease.api.users.dto.UserLoginRequestDto;
import tech.studease.studease.api.users.dto.UserRegisterRequestDto;

public interface AuthService {

  UserJwtTokenDto register(UserRegisterRequestDto userRequestDto);

  UserJwtTokenDto login(UserLoginRequestDto userRequestDto);

  UserDto getCurrentUser();

  void authenticate(String authorizationHeader);
}
