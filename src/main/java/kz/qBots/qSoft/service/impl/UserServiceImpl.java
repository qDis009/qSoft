package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.CartComponent;
import kz.qBots.qSoft.data.component.ItemComponent;
import kz.qBots.qSoft.data.component.RoleComponent;
import kz.qBots.qSoft.data.component.UserComponent;
import kz.qBots.qSoft.data.dto.*;
import kz.qBots.qSoft.data.entity.*;
import kz.qBots.qSoft.data.enums.ClientType;
import kz.qBots.qSoft.exception.InvalidCommandException;
import kz.qBots.qSoft.mapper.CartMapper;
import kz.qBots.qSoft.mapper.ItemMapper;
import kz.qBots.qSoft.mapper.RoleMapper;
import kz.qBots.qSoft.mapper.UserMapper;
import kz.qBots.qSoft.service.ItemService;
import kz.qBots.qSoft.service.OrderService;
import kz.qBots.qSoft.service.UserService;
import kz.qBots.qSoft.telegram.constants.TelegramConstants;
import kz.qBots.qSoft.telegram.dto.StartCommandDto;
import kz.qBots.qSoft.telegram.enums.Interface;
import kz.qBots.qSoft.telegram.handler.TelegramUpdateHandler;
import kz.qBots.qSoft.telegram.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private static final String CLICK_THE_BUTTON = "Нажмите кнопку";
  private static final String NEWS_PATH = "D:\\qshop\\items\\news\\%s.%s";
  private final UserComponent userComponent;
  private final UserMapper userMapper;
  private final OrderService orderService;
  private final TelegramService telegramService;
  private final ItemComponent itemComponent;
  private final CartMapper cartMapper;
  private final CartComponent cartComponent;
  private final ItemMapper itemMapper;

  @Override
  public void processStartCommand(User user, Interface roleInterface, String text, String url) {
    try {
      deletePreviousWebAppInfo(user.getChatId(), user.getLastMessageId());
      StartCommandDto startCommandDto =
          new StartCommandDto(1, user.getChatId(), roleInterface); // TODO shopId
      SendMessage message =
          SendMessage.builder()
              .parseMode(TelegramConstants.PARSE_MODE_HTML)
              .chatId(user.getChatId())
              .text(CLICK_THE_BUTTON)
              .replyMarkup(prepareWebAppInfo(startCommandDto, text, url))
              .build();
      user.setLastMessageId(telegramService.sendMessage(message));
      telegramService.sendMessage(createAdminKeyboard(user));
      userComponent.update(user);
    } catch (InvalidCommandException e) {
      // TODO
    } catch (TelegramApiException e) {
      // TODO
    }
  }

  private SendMessage createAdminKeyboard(User user) {
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    List<KeyboardRow> keyboard = new ArrayList<>();
    KeyboardRow row1 = new KeyboardRow();
    row1.add("Список оптовиков");
    row1.add("Скачать отчёт");
    keyboard.add(row1);
    KeyboardRow row2 = new KeyboardRow();
    row2.add("Жалобы и предложения");
    row2.add("Связь с разработчиками");
    keyboard.add(row2);
    keyboardMarkup.setKeyboard(keyboard);
    keyboardMarkup.setResizeKeyboard(true);
    keyboardMarkup.setOneTimeKeyboard(true);
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(user.getChatId());
    sendMessage.setReplyMarkup(keyboardMarkup);
    return sendMessage;
  }

  @Override
  public boolean isManager(User user) {
    Set<Role> roles = user.getRoles();
    for (Role role : roles) {
      if (role.getName().equals("MANAGER")) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isStorekeeper(User user) {
    Set<Role> roles = user.getRoles();
    for (Role role : roles) {
      if (role.getName().equals("STOREKEEPER")) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isAdmin(User user) {
    Set<Role> roles = user.getRoles();
    for (Role role : roles) {
      if (role.getName().equals("ADMIN")) {
        return true;
      }
    }
    return false;
  }

  @Override
  public List<UserDto> getCouriers() {
    return userComponent.findByRoleName("COURIER").stream()
        .map(userMapper::mapUserToUserDto)
        .toList();
  }

  @Override
  public List<UserDto> getEmployees() {
    return userComponent.findUsersWithRoles().stream().map(userMapper::mapUserToUserDto).toList();
  }

  @Override
  public void deleteEmployee(int id) {
    User employee = userComponent.findById(id);
    employee.getRoles().clear();
    userComponent.update(employee);
  }

  @Override
  public void sendNotification(String comment, List<Integer> itemIds) {
    List<User> clients = userComponent.findUsersWithoutRole();
    for (int itemId : itemIds) {
      Item item = itemComponent.findById(itemId);
      for (User client : clients) {
        if (client.getClientType().equals(ClientType.RETAIL) && item.getRetailPrice() != 0
            || client.getClientType().equals(ClientType.WHOLESALE)
                && item.getWholesalePrice() != 0) {
          if (!item.getImages().isEmpty()) {
            SendPhoto sendPhoto =
                SendPhoto.builder()
                    .photo(new InputFile(new File(item.getImages().iterator().next().getPath())))
                    .chatId(885073188 + "")
                    .build();
            try {
              telegramService.sendPhoto(sendPhoto);
            } catch (TelegramApiException e) {

            }
          }
          SendMessage sendMessage =
              SendMessage.builder()
                  .text(
                      "Наименование: "
                          + item.getNameRu()
                          + "\nАртикул: "
                          + item.getArticle()
                          + "\nЦена: "
                          + (client.getClientType().equals(ClientType.RETAIL)
                              ? (item.getRetailPrice() - item.getDiscount())
                              : item.getWholesalePrice()))
                  .chatId(885073188 + "")
                  .build();
          try {
            telegramService.sendMessage(sendMessage);
          } catch (TelegramApiException e) {

          }
          if (comment != null) {
            SendMessage sendMessage1 =
                SendMessage.builder().text(comment).chatId(885073188 + "").build();
            try {
              telegramService.sendMessage(sendMessage1);
            } catch (TelegramApiException e) {

            }
          }
        }
      }
      item.setEnabled(true);
      itemComponent.update(item);
    }
  }

  @Override
  public void sendNews(String comment, List<MultipartFile> multipartFiles) {
    List<User> clients = userComponent.findUsersWithoutRole();
    for (User client : clients) {
      SendMessage sendMessage = SendMessage.builder().text(comment).chatId(885073188 + "").build();
      try {
        telegramService.sendMessage(sendMessage);
      } catch (TelegramApiException e) {
        // TODO
      }
      for (MultipartFile multipartFile : multipartFiles) {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fileName = String.format(NEWS_PATH, multipartFile.getOriginalFilename(), extension);
        File file = new File(fileName);
        try {
          multipartFile.transferTo(file);
        } catch (IOException e) {
          // TODO
        }
        SendDocument sendDocument =
            SendDocument.builder().document(new InputFile(file)).chatId(885073188 + "").build();
        try {
          telegramService.sendDocument(sendDocument);
        } catch (TelegramApiException e) {
          // TODO
        }
      }
    }
  }

  @Override
  public List<UserDto> getClients() {
    return userComponent.findUsersWithoutRole().stream().map(userMapper::mapUserToUserDto).toList();
  }

  @Override
  public void processWholesaleClientsCommand(User user) {
    List<User> wholesaleClients = userComponent.findUsersByClientType(ClientType.WHOLESALE);
    StringBuilder stringBuilder = new StringBuilder();
    for (User wholesaleClient : wholesaleClients) {
      stringBuilder
          .append("/del")
          .append(wholesaleClient.getId())
          .append("❌ - ")
          .append("\uD83D\uDD0E")
          .append(wholesaleClient.getFullName())
          .append("\n");
    }
    stringBuilder.append(
        "Чтобы добавить нового пользователя, отправьте контакт пользователя. Он должен быть зарегистрированным.");
    SendMessage sendMessage =
        SendMessage.builder().text(stringBuilder.toString()).chatId(user.getChatId()).build();
    try {
      telegramService.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      // TODO
    }
  }

  @Override
  public void deleteCommand(int id) {
    User client = userComponent.findById(id);
    client.setClientType(ClientType.RETAIL);
    userComponent.update(client);
  }

  @Override
  public void processShopFeedbackCommand(User user) {}

  private InlineKeyboardMarkup createInlineKeyboardWithShopFeedback() {
    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
    InlineKeyboardButton button = new InlineKeyboardButton();
    return null;
  }

  @Override
  public boolean isCourier(User user) {
    Set<Role> roles = user.getRoles();
    for (Role role : roles) {
      if (role.getName().equals("COURIER")) {
        return true;
      }
    }
    return false;
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
    return userMapper.mapUserToUserDto(user);
  }

  @Override
  public List<CartDto> getCart(int id) {
    List<Cart> carts = cartComponent.findCartsByUserId(id);
    return carts.stream().map(cartMapper::mapCartToCartDto).toList();
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
    List<Item> items = itemComponent.findItemsByUserId(id);
    List<Item> enableItems = new ArrayList<>();
    items.forEach(
        item -> {
          if (item.isEnabled()) {
            enableItems.add(item);
          }
        });
    return enableItems.stream().map(itemMapper::mapItemToItemDto).toList();
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

  private String buildWebAppInfoUrl(StartCommandDto startCommandDto, String url) {
    return startCommandDto.buildWebAppInfoUrl(url);
  }

  private InlineKeyboardMarkup prepareWebAppInfo(
      StartCommandDto startCommandDto, String text, String url) {
    WebAppInfo webAppInfo = new WebAppInfo();
    webAppInfo.setUrl(buildWebAppInfoUrl(startCommandDto, url));
    InlineKeyboardButton inlineKeyboardButton =
        InlineKeyboardButton.builder().webApp(webAppInfo).text(text).build();
    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    inlineKeyboardMarkup.setKeyboard(List.of(List.of(inlineKeyboardButton)));
    return inlineKeyboardMarkup;
  }
}
