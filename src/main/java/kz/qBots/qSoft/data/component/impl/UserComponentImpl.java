package kz.qBots.qSoft.data.component.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.qBots.qSoft.data.component.UserComponent;
import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserComponentImpl implements UserComponent {
  private final UserRepository userRepository;

  @Override
  public User create(User user) {
    return userRepository.save(user);
  }

  @Override
  public User findById(int id) {
    return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public User findByChatId(long chatId) {
    return userRepository.findByChatId(chatId).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public void setLanguage(int id, String language) {
    userRepository.setLanguage(id, language);
  }

  @Override
  public void update(User user) {
    userRepository.save(user);
  }

  @Override
  public List<User> findByRoleName(String roleName) {
    return userRepository.findByRoles_Name(roleName);

  }

  @Override
  public List<User> findUsersWithRoles() {
    return userRepository.findUsersWithRoles();
  }
}
