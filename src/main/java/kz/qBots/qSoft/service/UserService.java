package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.*;
import kz.qBots.qSoft.data.entity.Cart;
import kz.qBots.qSoft.data.entity.Order;
import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.data.enums.ClientType;
import kz.qBots.qSoft.telegram.enums.Interface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Set;

public interface UserService {
  UserDto getById(Integer id);

  UserDto update(UserDto userDto);

  void setLanguage(int id, String language);

  Page<OrderDto> getOrdersHistory(int id, Pageable pageable);

  List<ItemDto> getFavorite(int userId);

  void processStartCommand(User user, Interface roleInterface, String text, String url);

  List<User> findByRoleName(String roleName);

  void addFavorite(int userId, int itemId);

  List<CartDto> getCart(int id);

  UserDto getByChatId(long chatId);

  void deleteFavorite(int userId, int itemId);

  boolean isManager(User user);

  boolean isStorekeeper(User user);

  boolean isCourier(User user);

  boolean isAdmin(User user);

  List<UserDto> getCouriers();

  List<UserDto> getEmployees();

  void deleteEmployee(int id);

  void sendNotification(String comment, List<Integer> itemIds);

  void sendNews(String comment, List<MultipartFile> multipartFiles);

  List<UserDto> getClients();

  void processWholesaleClientsCommand(User user);
  void deleteCommand(int id);
  void processShopFeedbackCommand(User user);
}
