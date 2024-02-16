package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.ItemFeedbackDto;
import kz.qBots.qSoft.rest.request.ItemRequest;
import kz.qBots.qSoft.service.FileService;
import kz.qBots.qSoft.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
  private final ItemService itemService;
  private final FileService fileService;

  @PostMapping("/create")
  public ResponseEntity<ItemDto> create(@RequestBody ItemRequest itemRequest) {
    return ResponseEntity.ok(itemService.create(itemRequest));
  }

  @PostMapping("/{id}/upload-photos")
  public ResponseEntity<Void> uploadPhotos(
      @PathVariable("id") int id, @RequestParam("photos") List<MultipartFile> multipartFiles) {
    fileService.uploadItemPhotos(id, multipartFiles);
    return new ResponseEntity<>(HttpStatus.OK);
  }

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
    return ResponseEntity.ok(itemService.findRetailHit(userId));
  }

  @GetMapping("/wholesale-hit/{userId}")
  public ResponseEntity<List<ItemDto>> getWholesaleHit(@PathVariable("userId") int userId) {
    return ResponseEntity.ok(itemService.findWholesaleHit(userId));
  }

  @GetMapping("/stocks/{userId}")
  public ResponseEntity<List<ItemDto>> getStocks(@PathVariable("userId") int userId) {
    return ResponseEntity.ok(itemService.getStocks(userId));
  }

  @GetMapping("/{id}/feedbacks")
  public ResponseEntity<List<ItemFeedbackDto>> getFeedbacks(@PathVariable("id") int id) {
    return ResponseEntity.ok(itemService.getFeedbacks(id));
  }

  @PatchMapping("/{id}/set-enable")
  public ResponseEntity<Void> setEnable(
      @PathVariable("id") int id, @RequestParam("enable") boolean enable) {
    itemService.setEnable(enable, id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<Void> delete(@PathVariable("id") int id) {
    itemService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/update")
  public ResponseEntity<ItemDto> update(@RequestBody ItemDto itemDto) {
    return ResponseEntity.ok(itemService.update(itemDto));
  }
}
