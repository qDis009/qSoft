package kz.qBots.qSoft.telegram.service;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public interface TelegramService {
  int sendMessage(SendMessage sendMessage) throws TelegramApiException;
  void deleteMessage(Long chatId, Integer messageId) throws TelegramApiException;
  int sendDocument(SendDocument sendDocument) throws TelegramApiException;
  int sendPhoto(SendPhoto sendPhoto) throws TelegramApiException;
}
