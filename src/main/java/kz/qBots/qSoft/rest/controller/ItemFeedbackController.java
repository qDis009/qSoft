package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.ItemFeedbackDto;
import kz.qBots.qSoft.rest.request.ItemFeedbackRequest;
import kz.qBots.qSoft.service.ItemFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item-feedback")
public class ItemFeedbackController {
  private final ItemFeedbackService itemFeedbackService;

  @PostMapping("/create")
  public ResponseEntity<ItemFeedbackDto> create(
      @RequestBody ItemFeedbackRequest itemFeedbackRequest) {
    return ResponseEntity.ok(itemFeedbackService.create(itemFeedbackRequest));
  }

}
