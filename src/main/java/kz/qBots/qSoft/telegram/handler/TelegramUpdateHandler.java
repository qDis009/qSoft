package kz.qBots.qSoft.telegram.handler;

import jakarta.persistence.EntityNotFoundException;
import kz.qBots.qSoft.config.property.TelegramProperty;
import kz.qBots.qSoft.data.component.UserComponent;
import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.exception.InvalidCommandException;
import kz.qBots.qSoft.telegram.service.CommandService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramUpdateHandler extends TelegramLongPollingBot {
  private final TelegramProperty telegramProperty;
  private final UserComponent userComponent;
  private final CommandService commandService;

  public TelegramUpdateHandler(
      TelegramProperty telegramProperty,
      UserComponent userComponent,
      CommandService commandService) {
    this.telegramProperty = telegramProperty;
    this.userComponent = userComponent;
    this.commandService = commandService;
  }

  @Override
  public void onUpdateReceived(Update update) {
    long chatId = update.getMessage().getChatId();
    User user;
    try {
      user = userComponent.findByChatId(chatId);
    } catch (EntityNotFoundException e) {
      Chat chat = update.getMessage().getChat();
      user = userComponent.create(new User(chat.getId(), chat.getUserName()));
    }
    try {
      if (isTextMessage(update)) {
        String messageText = update.getMessage().getText();
        if (messageText.startsWith("/")) {
          commandService.process(user, update.getMessage());
        } else {
          commandService.stringCommandProcess(user, update.getMessage());
        }
      } else if (update.hasCallbackQuery()) {
        // TODO callbackQuery
      }
    } catch (InvalidCommandException e) {

    } catch (TelegramApiException e) {

    }
  }

  private boolean isTextMessage(Update update) {
    return update.hasMessage() && update.getMessage().hasText();
  }

  @Override
  public String getBotUsername() {
    return telegramProperty.getUsername();
  }

  @Override
  public String getBotToken() {
    return telegramProperty.getToken();
  }
}
