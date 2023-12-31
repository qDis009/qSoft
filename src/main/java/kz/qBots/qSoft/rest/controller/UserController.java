package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.data.dto.UserDto;
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

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getById(@PathVariable("id") Integer id) {
    return ResponseEntity.ok(userService.getById(id));
  }

  @GetMapping("/{id}/favorite")
  public ResponseEntity<List<ItemDto>> getFavorite(@PathVariable("id") Integer id){
    return ResponseEntity.ok(userService.getFavorite(id));
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
    return ResponseEntity.ok(userService.getOrdersHistory(id,pageable));
  }

}
