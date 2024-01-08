package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ShopFeedbackComponent;
import kz.qBots.qSoft.data.dto.ShopFeedbackDto;
import kz.qBots.qSoft.data.entity.ShopFeedback;
import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.mapper.ShopFeedbackMapper;
import kz.qBots.qSoft.rest.request.ShopFeedbackRequest;
import kz.qBots.qSoft.service.ShopFeedbackService;
import kz.qBots.qSoft.service.UserService;
import kz.qBots.qSoft.telegram.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopFeedbackServiceImpl implements ShopFeedbackService {
  private final ShopFeedbackComponent shopFeedbackComponent;
  private final ShopFeedbackMapper shopFeedbackMapper;
  private final TelegramService telegramService;
  private final UserService userService;

  @Override
  public ShopFeedbackDto create(ShopFeedbackRequest model) {
    ShopFeedback shopFeedback = shopFeedbackMapper.mapShopFeedbackRequestToShopFeedback(model);
    if (!shopFeedback.getComment().isEmpty())
      sendMessageToAdmin(shopFeedback.getComment(), shopFeedback.getUser());
    // TODO add file
    return shopFeedbackMapper.mapShopFeedbackToShopFeedbackDto(
        shopFeedbackComponent.create(shopFeedback));
  }

  public void sendMessageToAdmin(String message, User user) {
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
        // TODO
      }
    }
    // TODO add file
  }
}
