package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.data.dto.UserDto;
import kz.qBots.qSoft.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
  UserDto getById(Integer id);

  UserDto update(UserDto userDto);

  void setLanguage(int id, String language);

  Page<OrderDto> getOrdersHistory(int id, Pageable pageable);

  List<ItemDto> getFavorite(int userId);

  void processMagazineCommand(User user);

  List<User> findByRoleName(String roleName);

  void addFavorite(int userId, int itemId);
}
