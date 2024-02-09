package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.ShopFeedbackDto;
import kz.qBots.qSoft.data.entity.Image;
import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.rest.request.ShopFeedbackRequest;

import java.util.Set;


public interface ShopFeedbackService {
  ShopFeedbackDto create(ShopFeedbackRequest model);
  void sendMessageToAdmin(String comment, User user, Set<Image> images);
}
