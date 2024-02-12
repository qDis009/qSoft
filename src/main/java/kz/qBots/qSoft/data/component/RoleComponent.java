package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.Role;

import java.util.List;

public interface RoleComponent {
    List<Role> getAll();
    Role findById(int id);
}
