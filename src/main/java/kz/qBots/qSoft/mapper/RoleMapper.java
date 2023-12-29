package kz.qBots.qSoft.mapper;

import kz.qBots.qSoft.data.dto.RoleDto;
import kz.qBots.qSoft.data.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto mapRoleToRoleDto(Role role);
}
