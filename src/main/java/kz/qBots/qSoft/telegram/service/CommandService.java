package kz.qBots.qSoft.telegram.service;

import kz.qBots.qSoft.data.entity.User;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface CommandService {
    void process(User user, Message message) throws TelegramApiException;
}
