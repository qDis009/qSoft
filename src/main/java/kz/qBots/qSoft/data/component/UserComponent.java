package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.User;

import java.util.List;

public interface UserComponent {
  User create(User user);

  User findById(int id);

  User findByChatId(long chatId);

  void setLanguage(int id, String language);

  void update(User user);
  List<User> findByRoleName(String roleName);
  List<User> findUsersWithRoles();
  List<User> findUsersWithoutRole();
}
