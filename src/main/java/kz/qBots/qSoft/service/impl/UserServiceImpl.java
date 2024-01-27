package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.config.property.ServerProperty;
import kz.qBots.qSoft.data.component.ItemComponent;
import kz.qBots.qSoft.data.component.UserComponent;
import kz.qBots.qSoft.data.dto.CartDto;
import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.data.dto.UserDto;
import kz.qBots.qSoft.data.entity.*;
import kz.qBots.qSoft.exception.InvalidCommandException;
import kz.qBots.qSoft.mapper.CartMapper;
import kz.qBots.qSoft.mapper.UserMapper;
import kz.qBots.qSoft.service.ItemService;
import kz.qBots.qSoft.service.OrderService;
import kz.qBots.qSoft.service.UserService;
import kz.qBots.qSoft.telegram.constants.TelegramConstants;
import kz.qBots.qSoft.telegram.dto.StartCommandDto;
import kz.qBots.qSoft.telegram.enums.Interface;
import kz.qBots.qSoft.telegram.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private static final String CLICK_THE_BUTTON = "Нажмите кнопку";
  private final UserComponent userComponent;
  private final UserMapper userMapper;
  private final OrderService orderService;
  private final ItemService itemService;
  private final TelegramService telegramService;
  private final ServerProperty serverProperty;
  private final ItemComponent itemComponent;
  private final CartMapper cartMapper;

  @Override
  public void processStartCommand(User user, Interface roleInterface, String text) {
    try {
      deletePreviousWebAppInfo(user.getChatId(), user.getLastMessageId());
      StartCommandDto startCommandDto =
          new StartCommandDto(1, user.getChatId(), roleInterface); // TODO shopId
      SendMessage message =
          SendMessage.builder()
              .parseMode(TelegramConstants.PARSE_MODE_HTML)
              .chatId(user.getChatId())
              .text(CLICK_THE_BUTTON)
              .replyMarkup(prepareWebAppInfo(startCommandDto, text))
              .build();
      user.setLastMessageId(telegramService.sendMessage(message));
      userComponent.update(user);
    } catch (InvalidCommandException e) {
      // TODO
    } catch (TelegramApiException e) {
      // TODO
    }
  }

  @Override
  public boolean isManager(User user) {
    Set<Role> roles = user.getRoles();
    boolean isManager = false;
    for (Role role : roles) {
      if (role.getName().equals("MANAGER")) {
        isManager = true;
        break;
      }
    }
    return isManager;
  }

  @Override
  public boolean isStorekeeper(User user) {
    Set<Role> roles = user.getRoles();
    boolean isStorekeeper = false;
    for (Role role : roles) {
      if (role.getName().equals("STOREKEEPER")) {
        isStorekeeper = true;
        break;
      }
    }
    return isStorekeeper;
  }

  @Override
  public boolean isCourier(User user) {
    Set<Role> roles = user.getRoles();
    boolean isCourier = false;
    for (Role role : roles) {
      if (role.getName().equals("COURIER")) {
        isCourier = true;
        break;
      }
    }
    return isCourier;
  }

  @Override
  public UserDto getById(Integer id) {
    User user = userComponent.findById(id);
    return userMapper.mapUserToUserDto(user);
  }

  @Override
  public UserDto getByChatId(long chatId) {
    User user = userComponent.findByChatId(chatId);
    return userMapper.mapUserToUserDto(user);
  }

  @Override
  public UserDto update(UserDto userDto) {
    User user = userMapper.mapUserDtoToUser(userDto);
    userComponent.update(user);
    return userDto;
  }

  @Override
  public List<CartDto> getCart(int id) {
    User user = userComponent.findById(id);
    Set<Cart> carts = user.getCarts();
    List<Cart> enabledCarts = new ArrayList<>();
    carts.forEach(
        cart -> {
          if (cart.isEnabled()) {
            enabledCarts.add(cart);
          }
        });
    return enabledCarts.stream().map(cartMapper::mapCartToCartDto).toList();
  }

  @Override
  public void deleteFavorite(int userId, int itemId) {
    User user = userComponent.findById(userId);
    Item item = itemComponent.findById(itemId);
    user.getItems().remove(item);
    userComponent.update(user);
  }

  @Override
  public List<User> findByRoleName(String roleName) {
    return userComponent.findByRoleName(roleName);
  }

  @Override
  public void setLanguage(int id, String language) {
    userComponent.setLanguage(id, language);
  }

  @Override
  public Page<OrderDto> getOrdersHistory(int id, Pageable pageable) {
    return orderService.findByUserId(id, pageable);
  }

  @Override
  public List<ItemDto> getFavorite(int id) {
    return itemService.findItemsByUserId(id);
  }

  @Transactional
  @Override
  public void addFavorite(int userId, int itemId) {
    User user = userComponent.findById(userId);
    Item item = itemComponent.findById(itemId);
    user.getItems().add(item);
    userComponent.update(user);
  }

  private void deletePreviousWebAppInfo(Long chatId, Integer messageId) {
    try {
      if (Objects.nonNull(messageId)) {
        telegramService.deleteMessage(chatId, messageId);
      }
    } catch (TelegramApiException e) {
      // TODO log
    }
  }

  private String buildWebAppInfoUrl(StartCommandDto startCommandDto) {
    return startCommandDto.buildWebAppInfoUrl(serverProperty.getUrl());
  }

  private InlineKeyboardMarkup prepareWebAppInfo(StartCommandDto startCommandDto, String text) {
    WebAppInfo webAppInfo = new WebAppInfo();
    webAppInfo.setUrl(buildWebAppInfoUrl(startCommandDto));
    InlineKeyboardButton inlineKeyboardButton =
        InlineKeyboardButton.builder().webApp(webAppInfo).text(text).build();
    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    inlineKeyboardMarkup.setKeyboard(List.of(List.of(inlineKeyboardButton)));
    return inlineKeyboardMarkup;
  }
}
