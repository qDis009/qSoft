package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.ItemFeedbackDto;
import kz.qBots.qSoft.rest.request.ItemFeedbackRequest;

import java.util.List;

public interface ItemFeedbackService {
    ItemFeedbackDto create(ItemFeedbackRequest itemFeedbackRequest);
    List<ItemFeedbackDto> getFeedbacks(int id);
}
