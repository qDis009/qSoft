package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.ItemFeedbackDto;
import kz.qBots.qSoft.data.enums.ItemType;
import kz.qBots.qSoft.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

  @GetMapping("/get-all")
  public ResponseEntity<Page<ItemDto>> getAll(
      @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
    return ResponseEntity.ok(itemService.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ItemDto> getById(@PathVariable("id") int id) {
    return ResponseEntity.ok(itemService.findById(id));
  }

  @GetMapping("/retail-hit/{userId}")
  public ResponseEntity<List<ItemDto>> getRetailHit(@PathVariable("userId") int userId) {
    return ResponseEntity.ok(itemService.findItemsByItemType(ItemType.RETAIL, userId));
  }

  @GetMapping("/wholesale-hit/{userId}")
  public ResponseEntity<List<ItemDto>> getWholesaleHit(@PathVariable("userId") int userId) {
    return ResponseEntity.ok(itemService.findItemsByItemType(ItemType.WHOLESALE, userId));
  }

  @GetMapping("/stocks/{userId}")
  public ResponseEntity<List<ItemDto>> getStocks(@PathVariable("userId") int userId) {
    return ResponseEntity.ok(itemService.getStocks(userId));
  }

  @GetMapping("/{id}/feedbacks")
  public ResponseEntity<List<ItemFeedbackDto>> getFeedbacks(@PathVariable("id") int id) {
    return ResponseEntity.ok(itemService.getFeedbacks(id));
  }

  @GetMapping("/{id}/photo")
  public ResponseEntity<Resource> getPhoto(@PathVariable("id") int id)
      throws MalformedURLException {
    String fileName = id + ".jpg";
    Path photoPath = Paths.get("D:\\qshop\\items\\photos\\").resolve(fileName);
    Resource resource = new UrlResource(photoPath.toUri());
    return ResponseEntity.ok().body(resource);
  }
}
