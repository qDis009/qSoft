package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.CartDto;
import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.data.dto.UserDto;
import kz.qBots.qSoft.data.entity.Cart;
import kz.qBots.qSoft.data.entity.Order;
import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.telegram.enums.Interface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface UserService {
    UserDto getById(Integer id);

    UserDto update(UserDto userDto);

    void setLanguage(int id, String language);

    Page<OrderDto> getOrdersHistory(int id, Pageable pageable);

    List<ItemDto> getFavorite(int userId);

    void processStartCommand(User user, Interface roleInterface,String text);

    List<User> findByRoleName(String roleName);

    void addFavorite(int userId, int itemId);

    List<CartDto> getCart(int id);

    UserDto getByChatId(long chatId);

    void deleteFavorite(int userId, int itemId);
    boolean isManager(User user);
    boolean isStorekeeper(User user);
}
