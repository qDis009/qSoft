package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.ItemFeedbackDto;
import kz.qBots.qSoft.rest.request.ItemFeedbackRequest;

public interface ItemFeedbackService {
    ItemFeedbackDto create(ItemFeedbackRequest itemFeedbackRequest);
}
