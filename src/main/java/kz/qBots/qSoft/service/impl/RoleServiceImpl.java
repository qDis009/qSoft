package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.RoleComponent;
import kz.qBots.qSoft.data.dto.RoleDto;
import kz.qBots.qSoft.mapper.RoleMapper;
import kz.qBots.qSoft.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleComponent roleComponent;
    private final RoleMapper roleMapper;
    @Override
    public List<RoleDto> getAll() {
        return roleComponent.getAll().stream().map(roleMapper::mapRoleToRoleDto).toList();
    }
}
