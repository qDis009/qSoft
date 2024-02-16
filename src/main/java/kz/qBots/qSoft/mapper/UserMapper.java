package kz.qBots.qSoft.mapper;

import kz.qBots.qSoft.data.dto.UserDto;
import kz.qBots.qSoft.data.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {OrderMapper.class, RoleMapper.class})
public interface UserMapper {
  UserDto mapUserToUserDto(User user);

  User mapUserDtoToUser(UserDto userDto);
}
