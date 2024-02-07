package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.ShopFeedbackDto;
import kz.qBots.qSoft.rest.request.ShopFeedbackRequest;
import kz.qBots.qSoft.service.FileService;
import kz.qBots.qSoft.service.ShopFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop-feedback")
public class ShopFeedbackController {
  private final ShopFeedbackService shopFeedbackService;
  private final FileService fileService;

  @PostMapping("/create")
  public ResponseEntity<ShopFeedbackDto> create(
      @RequestPart ShopFeedbackRequest model, @RequestPart List<MultipartFile> multipartFiles) {
    return ResponseEntity.ok(shopFeedbackService.create(model,multipartFiles));
  }

//  @PostMapping("/upload-file")
//  public ResponseEntity<Integer> uploadFile(@RequestParam("file") MultipartFile file) {
//    return ResponseEntity.ok(fileService.uploadShopFeedbackFile(file));
//  }

}
