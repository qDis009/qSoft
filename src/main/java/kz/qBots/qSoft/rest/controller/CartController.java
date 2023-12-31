package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.CartDto;
import kz.qBots.qSoft.rest.request.CartRequest;
import kz.qBots.qSoft.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
  private final CartService cartService;

  @PostMapping("/create")
  public ResponseEntity<CartDto> create(@RequestParam CartRequest model) {
    return ResponseEntity.ok(cartService.create(model));
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<Void> delete(@PathVariable("id") int id) {
    cartService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
