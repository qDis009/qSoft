package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.ItemFeedbackDto;
import kz.qBots.qSoft.rest.request.ItemFeedbackRequest;
import kz.qBots.qSoft.service.ItemFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  @DeleteMapping("/{id}/delete")
  public ResponseEntity<Void> delete(@PathVariable("id") int id){
    itemFeedbackService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
