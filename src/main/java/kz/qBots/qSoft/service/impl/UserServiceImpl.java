package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.config.property.ServerProperty;
import kz.qBots.qSoft.data.component.UserComponent;
import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.data.dto.UserDto;
import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.exception.InvalidCommandException;
import kz.qBots.qSoft.mapper.UserMapper;
import kz.qBots.qSoft.service.ItemService;
import kz.qBots.qSoft.service.OrderService;
import kz.qBots.qSoft.service.UserService;
import kz.qBots.qSoft.telegram.constants.TelegramConstants;
import kz.qBots.qSoft.telegram.dto.StartCommandDto;
import kz.qBots.qSoft.telegram.enums.Role;
import kz.qBots.qSoft.telegram.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final static String CLICK_THE_BUTTON = "Нажмите кнопку";
  private final static String MAGAZINE = "Магазин";
  private final UserComponent userComponent;
  private final UserMapper userMapper;
  private final OrderService orderService;
  private final ItemService itemService;
  private final TelegramService telegramService;
  private final ServerProperty serverProperty;

  @Override
  public void processMagazineCommand(User user) {
    try{
      deletePreviousWebAppInfo(user.getChatId(),user.getLastMessageId());
      StartCommandDto startCommandDto=new StartCommandDto(1,user.getChatId(), Role.USER); //TODO shopId
      SendMessage message=SendMessage.builder()
              .parseMode(TelegramConstants.PARSE_MODE_HTML)
              .chatId(user.getChatId())
              .text(CLICK_THE_BUTTON)
              .replyMarkup(prepareWebAppInfo(startCommandDto))
              .build();
      user.setLastMessageId(telegramService.sendMessage(message));
      userComponent.update(user);
    }catch (InvalidCommandException e){
      //TODO
    }catch (TelegramApiException e){
      //TODO
    }
  }

  @Override
  public UserDto getById(Integer id) {
    User user = userComponent.findById(id);
    return userMapper.mapUserToUserDto(user);
  }

  @Override
  public UserDto update(UserDto userDto) {
    User user = userMapper.mapUserDtoToUser(userDto);
    userComponent.update(user);
    return userDto;
  }

  @Override
  public void setLanguage(int id, String language) {
    userComponent.setLanguage(id, language);
  }

  @Override
  public Page<OrderDto> getOrdersHistory(int id, Pageable pageable) {
    return orderService.findByUserId(id, pageable);
  }

  @Override
  public List<ItemDto> getFavorite(int id) {
    return itemService.findItemsByUserId(id);
  }

  private void deletePreviousWebAppInfo(Long chatId, Integer messageId) {
    try {
      if (Objects.nonNull(messageId)) {
        telegramService.deleteMessage(chatId, messageId);
      }
    } catch (TelegramApiException e) {
      // TODO log
    }
  }
  private String buildWebAppInfoUrl(StartCommandDto startCommandDto) {
    return startCommandDto.buildWebAppInfoUrl(serverProperty.getUrl());
  }
  private InlineKeyboardMarkup prepareWebAppInfo(StartCommandDto startCommandDto){
    WebAppInfo webAppInfo=new WebAppInfo();
    webAppInfo.setUrl(buildWebAppInfoUrl(startCommandDto));
    InlineKeyboardButton inlineKeyboardButton=InlineKeyboardButton.builder()
            .webApp(webAppInfo)
            .text(MAGAZINE)
            .build();
    InlineKeyboardMarkup inlineKeyboardMarkup=new InlineKeyboardMarkup();
    inlineKeyboardMarkup.setKeyboard(List.of(List.of(inlineKeyboardButton)));
    return inlineKeyboardMarkup;
  }
}
