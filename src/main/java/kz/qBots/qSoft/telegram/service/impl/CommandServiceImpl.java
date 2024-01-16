package kz.qBots.qSoft.telegram.service.impl;

import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.exception.InvalidCommandException;
import kz.qBots.qSoft.service.UserService;
import kz.qBots.qSoft.telegram.service.CommandService;
import kz.qBots.qSoft.telegram.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService {
  private final TelegramService telegramService;
  private final UserService userService;

  @Override
  public void process(User user, Message message) throws TelegramApiException {
    String messageText = message.getText();
    String command = getCommand(messageText);
    switch (command) {
      case "/magazine" -> {
        // telegramService.deleteMessage(user.getChatId(), user.getLastMessageId());
        userService.processMagazineCommand(user);
      }
      case "/manager" -> {
        //TODO check role
        userService.processManagerCommand(user);
      }
      default -> throw new InvalidCommandException("Cannot found command name: " + command);
    }
  }

  private String getCommand(String messageText) {
    return messageText.split(" ")[0];
  }
}
