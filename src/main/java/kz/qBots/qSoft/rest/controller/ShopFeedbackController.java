package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.ShopFeedbackDto;
import kz.qBots.qSoft.rest.request.ShopFeedbackRequest;
import kz.qBots.qSoft.service.ShopFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop-feedback")
public class ShopFeedbackController {
  private final ShopFeedbackService shopFeedbackService;

  @PostMapping("/create")
  public ResponseEntity<ShopFeedbackDto> create(
      @RequestPart ShopFeedbackRequest model, @RequestParam MultipartFile file) {
    return ResponseEntity.ok(shopFeedbackService.create(model,file));
  }
}
