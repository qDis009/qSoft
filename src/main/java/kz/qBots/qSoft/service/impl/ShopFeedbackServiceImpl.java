package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ShopFeedbackComponent;
import kz.qBots.qSoft.data.dto.ShopFeedbackDto;
import kz.qBots.qSoft.data.entity.Image;
import kz.qBots.qSoft.data.entity.ShopFeedback;
import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.mapper.ShopFeedbackMapper;
import kz.qBots.qSoft.rest.request.ShopFeedbackRequest;
import kz.qBots.qSoft.service.ShopFeedbackService;
import kz.qBots.qSoft.service.UserService;
import kz.qBots.qSoft.telegram.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShopFeedbackServiceImpl implements ShopFeedbackService {
  private final ShopFeedbackComponent shopFeedbackComponent;
  private final ShopFeedbackMapper shopFeedbackMapper;
  private final TelegramService telegramService;
  private final UserService userService;

  @Override
  public ShopFeedbackDto create(ShopFeedbackRequest model) {
    ShopFeedback shopFeedback =
        shopFeedbackComponent.create(
            shopFeedbackMapper.mapShopFeedbackRequestToShopFeedback(model));
    return shopFeedbackMapper.mapShopFeedbackToShopFeedbackDto(shopFeedback);
  }

  @Override
  public void sendMessageToAdmin(String message, User user, Set<Image> images) {
    List<User> admins = userService.findByRoleName("ADMIN");
    String messageText =
        "Предложение/жалоба от клиента!\n"
            + "ФИО: "
            + user.getFullName()
            + "\n"
            + "Номер телефона: "
            + user.getPhoneNumber()
            + "\n"
            + "Текст обращения: "
            + message;
    for (User admin : admins) {
      SendMessage sendMessage =
          SendMessage.builder().text(messageText).chatId(admin.getChatId()).build();
      try {
        admin.setLastMessageId(telegramService.sendMessage(sendMessage));
      } catch (TelegramApiException e) {
        // TODO log
      }
      for (Image image : images) {
        SendDocument sendDocument =
            SendDocument.builder()
                .document(new InputFile(new File(image.getPath())))
                .chatId(admin.getChatId())
                .build();
        try {
          telegramService.sendDocument(sendDocument);
        } catch (TelegramApiException e) {
          //TODO log
        }
      }
    }
  }
}
