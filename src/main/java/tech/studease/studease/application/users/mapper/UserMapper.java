package tech.studease.studease.application.users.mapper;

import org.mapstruct.Mapper;
import tech.studease.studease.api.users.dto.UserDto;
import tech.studease.studease.domain.users.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserDto toUserDto(User user);
}
