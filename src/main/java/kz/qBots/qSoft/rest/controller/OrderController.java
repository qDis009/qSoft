package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.rest.request.OrderRequest;
import kz.qBots.qSoft.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderDto> order(@RequestBody OrderRequest orderRequest) {
    return ResponseEntity.ok(orderService.order(orderRequest));
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderDto> getById(@PathVariable("id") int id) {
    return ResponseEntity.ok(orderService.findById(id));
  }
  @PatchMapping("/{id}/set-status")
  public ResponseEntity<Void> setStatus(@PathVariable("id") int id,
                                        @RequestParam("status") String status){
    orderService.setStatus(id,status);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
