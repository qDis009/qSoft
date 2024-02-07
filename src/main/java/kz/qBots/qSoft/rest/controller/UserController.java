package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.CartDto;
import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.data.dto.UserDto;
import kz.qBots.qSoft.service.ResentSeenService;
import kz.qBots.qSoft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserService userService;
  private final ResentSeenService resentSeenService;

  @GetMapping("/{id}/favorite")
  public ResponseEntity<List<ItemDto>> getFavorite(@PathVariable("id") Integer id) {
    return ResponseEntity.ok(userService.getFavorite(id));
  }

  @PostMapping("/{id}/add-favorite/{itemId}")
  public ResponseEntity<Void> addFavorite(
      @PathVariable("id") int id, @PathVariable("itemId") int itemId) {
    userService.addFavorite(id, itemId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{id}/delete-favorite/{itemId}")
  public ResponseEntity<Void> deleteFavorite(
      @PathVariable("id") int id, @PathVariable("itemId") int itemId) {
    userService.deleteFavorite(id, itemId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/update")
  public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
    return ResponseEntity.ok(userService.update(userDto));
  }

  @PatchMapping("/{id}/set-language")
  public ResponseEntity<Void> setLanguage(
      @PathVariable("id") Integer id, @RequestParam("language") String language) {
    userService.setLanguage(id, language);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/{id}/order-history")
  public ResponseEntity<Page<OrderDto>> getOrdersHistory(
      @PathVariable("id") Integer id,
      @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {
    return ResponseEntity.ok(userService.getOrdersHistory(id, pageable));
  }

  @GetMapping("/{id}/cart")
  public ResponseEntity<List<CartDto>> getCart(@PathVariable("id") int id) {
    return ResponseEntity.ok(userService.getCart(id));
  }

  @GetMapping("/{chatId}")
  public ResponseEntity<UserDto> getByChatId(@PathVariable("chatId") long chatId) {
    return ResponseEntity.ok(userService.getByChatId(chatId));
  }

  @GetMapping("/{id}/resent-seen")
  public ResponseEntity<List<ItemDto>> getResentSeen(@PathVariable("id") int id) {
    return ResponseEntity.ok(resentSeenService.getResentSeenByUserId(id));
  }

  @PostMapping("/{id}/add-resent-seen/{itemId}")
  public ResponseEntity<Void> addResentSeen(
      @PathVariable("id") int userId, @PathVariable("itemId") int itemId) {
    resentSeenService.addResentSeen(userId, itemId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/couriers")
  public ResponseEntity<List<UserDto>> getCouriers() {
    return ResponseEntity.ok(userService.getCouriers());
  }

  @GetMapping("/employees")
  public ResponseEntity<List<UserDto>> getEmployees() {
    return ResponseEntity.ok(userService.getEmployees());
  }

  @DeleteMapping("/{id}/delete-employee")
  public ResponseEntity<Void> deleteEmployee(@PathVariable("id") int id) {
    userService.deleteEmployee(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
