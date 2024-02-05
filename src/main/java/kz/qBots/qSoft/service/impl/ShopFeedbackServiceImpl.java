package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ImageComponent;
import kz.qBots.qSoft.data.component.ShopFeedbackComponent;
import kz.qBots.qSoft.data.dto.ShopFeedbackDto;
import kz.qBots.qSoft.data.entity.Image;
import kz.qBots.qSoft.data.entity.ShopFeedback;
import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.mapper.ShopFeedbackMapper;
import kz.qBots.qSoft.rest.request.ShopFeedbackRequest;
import kz.qBots.qSoft.service.FileService;
import kz.qBots.qSoft.service.ShopFeedbackService;
import kz.qBots.qSoft.service.UserService;
import kz.qBots.qSoft.telegram.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShopFeedbackServiceImpl implements ShopFeedbackService {
  private final ShopFeedbackComponent shopFeedbackComponent;
  private final ShopFeedbackMapper shopFeedbackMapper;
  private final TelegramService telegramService;
  private final UserService userService;
  private ImageComponent imageComponent;

  @Override
  public ShopFeedbackDto create(ShopFeedbackRequest model, List<Integer> imageIds) {
    ShopFeedback shopFeedback =
        shopFeedbackComponent.create(
            shopFeedbackMapper.mapShopFeedbackRequestToShopFeedback(model));
    setImagesToShopFeedback(shopFeedback, imageIds);
    if (!shopFeedback.getComment().isEmpty())
      sendMessageToAdmin(shopFeedback.getComment(), shopFeedback.getUser());
    return shopFeedbackMapper.mapShopFeedbackToShopFeedbackDto(shopFeedback);
  }

  private void setImagesToShopFeedback(ShopFeedback shopFeedback, List<Integer> imageIds) {
    Set<Image> images = new HashSet<>();
    for (int id : imageIds) {
      Image image = imageComponent.findById(id);
      image.setShopFeedback(shopFeedback);
      images.add(image);
      imageComponent.update(image);
    }
    shopFeedback.setImages(images);
    shopFeedbackComponent.update(shopFeedback);
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
