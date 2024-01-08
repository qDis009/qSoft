package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.ShopFeedbackDto;
import kz.qBots.qSoft.rest.request.ShopFeedbackRequest;

public interface ShopFeedbackService {
  ShopFeedbackDto create(ShopFeedbackRequest model);
}
