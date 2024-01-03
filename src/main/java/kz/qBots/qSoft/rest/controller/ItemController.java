package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.enums.ItemType;
import kz.qBots.qSoft.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
  private final ItemService itemService;

  @GetMapping("/get-all")
  public ResponseEntity<Page<ItemDto>> getAll(
      @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
    return ResponseEntity.ok(itemService.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ItemDto> getById(@PathVariable("id") int id) {
    return ResponseEntity.ok(itemService.findById(id));
  }

  @GetMapping("/get-retail-hit")
  public ResponseEntity<Page<ItemDto>> getRetailHit(
      @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
    return ResponseEntity.ok(itemService.findItemsByItemType(ItemType.RETAIL, pageable));
  }

  @GetMapping("get-wholesale-hit")
  public ResponseEntity<Page<ItemDto>> getWholesaleHit(
      @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
    return ResponseEntity.ok(itemService.findItemsByItemType(ItemType.WHOLESALE, pageable));
  }

  @GetMapping("/get-stocks")
  public ResponseEntity<Page<ItemDto>> getStocks(
      @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
    return ResponseEntity.ok(itemService.getStocks(pageable));
  }
}
