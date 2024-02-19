package kz.qBots.qSoft.telegram.service.impl;

import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.exception.InvalidCommandException;
import kz.qBots.qSoft.service.UserService;
import kz.qBots.qSoft.telegram.enums.Interface;
import kz.qBots.qSoft.telegram.service.CommandService;
import kz.qBots.qSoft.telegram.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService {
  private final TelegramService telegramService;
  private final UserService userService;
  private static final String MANAGER = "Менеджер";
  private static final String MAGAZINE = "Магазин";
  private static final String STOREKEEPER = "Кладовщик";
  private static final String COURIER = "Курьер";
  private static final String ADMIN = "Админ";
  private static final String CLIENT_URL = "https://109.233.108.126:471";
  private static final String MANAGER_URL = "https://109.233.108.126:472";
  private static final String STOREKEEPER_URL = "";
  private static final String COURIER_URL = "";
  private static final String ADMIN_URL = "";

  @Override
  public void process(User user, Message message) throws TelegramApiException {
    String messageText = message.getText();
    String command = getCommand(messageText);
    if(isDeleteCommand(messageText)){
      int id=Integer.parseInt(messageText.substring(4));
      userService.deleteCommand(id);
    }
    switch (command) {
      case "/magazine" -> {
        // telegramService.deleteMessage(user.getChatId(), user.getLastMessageId());
        userService.processStartCommand(user, Interface.USER, MAGAZINE, CLIENT_URL);
      }
      case "/manager" -> {
        if (userService.isManager(user)) {
          userService.processStartCommand(user, Interface.MANAGER, MANAGER, MANAGER_URL);
        } else {
          user.setLastMessageId(
              telegramService.sendMessage(getMessageToUnregisteredUser(user, MANAGER)));
        }
      }
      case "/storekeeper" -> {
        if (userService.isStorekeeper(user)) {
          userService.processStartCommand(
              user, Interface.STOREKEEPER, STOREKEEPER, STOREKEEPER_URL);
        } else {
          user.setLastMessageId(
              telegramService.sendMessage(getMessageToUnregisteredUser(user, STOREKEEPER)));
        }
      }
      case "/courier" -> {
        if (userService.isCourier(user)) {
          userService.processStartCommand(user, Interface.COURIER, COURIER, COURIER_URL);
        } else {
          user.setLastMessageId(
              telegramService.sendMessage(getMessageToUnregisteredUser(user, COURIER)));
        }
      }
      case "/admin" -> {
        if (userService.isAdmin(user)) {
          userService.processStartCommand(user, Interface.ADMIN, ADMIN, ADMIN_URL);
        } else {
          user.setLastMessageId(
              telegramService.sendMessage(getMessageToUnregisteredUser(user, ADMIN)));
        }
      }
      default -> throw new InvalidCommandException("Cannot found command name: " + command);
    }
  }

  @Override
  public void stringCommandProcess(User user, Message message) {
    String messageText = message.getText();
    switch (messageText) {
      case "Список оптовиков" -> {
        userService.processWholesaleClientsCommand(user);
      }
      case "Скачать отчет" -> {}
      case "Жалобы и предложения" -> {
        userService.processShopFeedbackCommand(user);
      }
      case "Связь с разработчиками" -> {}
    }
  }

  private String getCommand(String messageText) {
    return messageText.split(" ")[0];
  }
  private boolean isDeleteCommand(String messageText){
    return messageText.startsWith("/del");
  }
  private SendMessage getMessageToUnregisteredUser(User user, String role) {
    return SendMessage.builder()
        .text("Вы не являетесь " + role + "ом")
        .chatId(user.getChatId())
        .build();
  }
}
