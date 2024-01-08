package kz.qBots.qSoft.telegram.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramService {
  int sendMessage(SendMessage sendMessage) throws TelegramApiException;

  void deleteMessage(Long chatId, Integer messageId) throws TelegramApiException;
}
