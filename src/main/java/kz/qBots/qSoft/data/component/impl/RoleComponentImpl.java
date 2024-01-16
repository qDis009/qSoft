package kz.qBots.qSoft.data.component.impl;

import kz.qBots.qSoft.data.component.RoleComponent;
import kz.qBots.qSoft.data.entity.Role;
import kz.qBots.qSoft.data.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleComponentImpl implements RoleComponent {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
