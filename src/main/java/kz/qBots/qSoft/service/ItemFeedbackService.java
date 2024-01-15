package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.ItemFeedbackDto;
import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.rest.request.ItemFeedbackRequest;

import java.util.List;

public interface ItemFeedbackService {
  boolean hasComment(int userId,int itemId);
  ItemFeedbackDto create(ItemFeedbackRequest itemFeedbackRequest);

  List<ItemFeedbackDto> getFeedbacks(int id);

  void delete(int id);
}
