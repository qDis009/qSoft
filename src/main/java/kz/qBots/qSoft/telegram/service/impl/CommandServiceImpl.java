package kz.qBots.qSoft.telegram.service.impl;

import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.telegram.service.CommandService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
@Service
public class CommandServiceImpl implements CommandService {
    @Override
    public void process(User user, Message message) throws TelegramApiException {

    }
}
