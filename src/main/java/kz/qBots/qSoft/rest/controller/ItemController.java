package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.ItemFeedbackDto;
import kz.qBots.qSoft.data.enums.ItemType;
import kz.qBots.qSoft.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
  private final ItemService itemService;

  @GetMapping("/get-all/{userId}")
  public ResponseEntity<List<ItemDto>> getAll(@PathVariable("userId") int userId) {
    return ResponseEntity.ok(itemService.findAll(userId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ItemDto> getById(@PathVariable("id") int id) {
    return ResponseEntity.ok(itemService.findById(id));
  }

  @GetMapping("/get-retail-hit/{userId}")
  public ResponseEntity<List<ItemDto>> getRetailHit(@PathVariable("userId") int userId) {
    return ResponseEntity.ok(itemService.findItemsByItemType(ItemType.RETAIL, userId));
  }

  @GetMapping("/get-wholesale-hit/{userId}")
  public ResponseEntity<List<ItemDto>> getWholesaleHit(@PathVariable("userId") int userId) {
    return ResponseEntity.ok(itemService.findItemsByItemType(ItemType.WHOLESALE, userId));
  }

  @GetMapping("/get-stocks/{userId}")
  public ResponseEntity<List<ItemDto>> getStocks(@PathVariable("userId") int userId) {
    return ResponseEntity.ok(itemService.getStocks(userId));
  }

  @GetMapping("/{id}/get-feedbacks")
  public ResponseEntity<List<ItemFeedbackDto>> getFeedbacks(@PathVariable("id") int id) {
    return ResponseEntity.ok(itemService.getFeedbacks(id));
  }
}
