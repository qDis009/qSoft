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

  @Override
  public void process(User user, Message message) throws TelegramApiException {
    String messageText = message.getText();
    String command = getCommand(messageText);
    switch (command) {
      case "/magazine" -> {
        // telegramService.deleteMessage(user.getChatId(), user.getLastMessageId());
        userService.processStartCommand(user, Interface.USER, MAGAZINE);
      }
      case "/manager" -> {
        if (userService.isManager(user)) {
          userService.processStartCommand(user, Interface.MANAGER, MANAGER);
        } else {
          user.setLastMessageId(
              telegramService.sendMessage(getMessageToUnregisteredUser(user, MANAGER)));
        }
      }
      case "/storekeeper" -> {
        if (userService.isStorekeeper(user)) {
          userService.processStartCommand(user, Interface.STOREKEEPER, STOREKEEPER);
        } else {
          user.setLastMessageId(
              telegramService.sendMessage(getMessageToUnregisteredUser(user, STOREKEEPER)));
        }
      }
      case "/courier" -> {
        if (userService.isCourier(user)) {
          userService.processStartCommand(user, Interface.COURIER, COURIER);
        } else {
          user.setLastMessageId(
              telegramService.sendMessage(getMessageToUnregisteredUser(user, COURIER)));
        }
      }
      case "/admin" -> {
        if (userService.isAdmin(user)) {
          userService.processStartCommand(user, Interface.ADMIN, ADMIN);
        } else {
          user.setLastMessageId(
              telegramService.sendMessage(getMessageToUnregisteredUser(user, ADMIN)));
        }
      }
      case "/reg" -> {
        // TODO
      }
      default -> throw new InvalidCommandException("Cannot found command name: " + command);
    }
  }

  private String getCommand(String messageText) {
    return messageText.split(" ")[0];
  }

  private SendMessage getMessageToUnregisteredUser(User user, String role) {
    return SendMessage.builder()
        .text("Вы не являетесь " + role + "ом")
        .chatId(user.getChatId())
        .build();
  }
}
