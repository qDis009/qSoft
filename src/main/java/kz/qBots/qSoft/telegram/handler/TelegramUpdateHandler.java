package kz.qBots.qSoft.telegram.handler;

import jakarta.persistence.EntityNotFoundException;
import kz.qBots.qSoft.config.property.TelegramProperty;
import kz.qBots.qSoft.data.component.UserComponent;
import kz.qBots.qSoft.data.entity.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramUpdateHandler extends TelegramLongPollingBot {
    private final TelegramProperty telegramProperty;
    private final UserComponent userComponent;

    public TelegramUpdateHandler(TelegramProperty telegramProperty,UserComponent userComponent) {
        this.telegramProperty=telegramProperty;
        this.userComponent=userComponent;
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId=update.getMessage().getChatId();
        User user;
        try{
            user=userComponent.findByChatId(chatId);
        }catch (EntityNotFoundException e){
            Chat chat=update.getMessage().getChat();
            user=userComponent.create(new User(chat.getId(),chat.getUserName()));
        }

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
