package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.ShopFeedbackDto;
import kz.qBots.qSoft.rest.request.ShopFeedbackRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShopFeedbackService {
  ShopFeedbackDto create(ShopFeedbackRequest model, List<Integer> imageIds);
}
