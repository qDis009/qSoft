package kz.qBots.qSoft.mapper;

import kz.qBots.qSoft.data.dto.UserDto;
import kz.qBots.qSoft.data.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto mapUserToUserDto(User user);
  User mapUserDtoToUser(UserDto userDto);
}
