package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.rest.request.OrderRequest;
import kz.qBots.qSoft.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
  public ResponseEntity<Void> setStatus(
      @PathVariable("id") int id, @RequestParam("status") String status) {
    orderService.setStatus(id, status);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/manager-new-orders")
  public ResponseEntity<List<OrderDto>> getManagerNewOrders() {
    return ResponseEntity.ok(orderService.getManagerNewOrders());
  }

  @GetMapping("/manager-accepted-orders")
  public ResponseEntity<List<OrderDto>> getManagerAcceptedOrders() {
    return ResponseEntity.ok(orderService.getManagerAcceptedOrders());
  }

  @PatchMapping("/{id}/reject")
  public ResponseEntity<Void> rejectOrder(
      @PathVariable("id") int id,
      @RequestParam("reason") String reason,
      @RequestParam("role") String role) {
    orderService.rejectOrder(id, reason, role);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/in-way-orders")
  public ResponseEntity<List<OrderDto>> getInWayOrders() {
    return ResponseEntity.ok(orderService.getInWayOrders());
  }

  @GetMapping("/manager-completed-orders")
  public ResponseEntity<List<OrderDto>> getCompletedOrders() {
    return ResponseEntity.ok(orderService.getManagerCompletedOrders());
  }

  @PatchMapping("/{id}/accept-by-manager")
  public ResponseEntity<Void> acceptOrderByManager(@PathVariable("id") int id) {
    orderService.acceptOrderByManager(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/storekeeper-new-orders")
  public ResponseEntity<List<OrderDto>> getStorekeeperNewOrders() {
    return ResponseEntity.ok(orderService.getStorekeeperNewOrders());
  }

  @GetMapping("/storekeeper-accepted-orders")
  public ResponseEntity<List<OrderDto>> getStorekeeperAcceptedOrders() {
    return ResponseEntity.ok(orderService.getStorekeeperAcceptedOrders());
  }

  @PatchMapping("/{id}/accept-by-storekeeper")
  public ResponseEntity<Void> acceptOrderByStorekeeper(@PathVariable("id") int id) {
    orderService.acceptOrderByStorekeeper(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping("/{id}/complete-by-storekeeper")
  public ResponseEntity<Void> completeOrderByStorekeeper(@PathVariable("id") int id) {
    orderService.completeOrderByStorekeeper(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/storekeeper-completed-orders")
  public ResponseEntity<List<OrderDto>> getStorekeeperCompletedOrders() {
    return ResponseEntity.ok(orderService.getStorekeeperCompletedOrders());
  }

  @GetMapping("/courier-new-orders")
  public ResponseEntity<List<OrderDto>> getCourierNewOrders() {
    return ResponseEntity.ok(orderService.getCourierNewOrders());
  }

  @PatchMapping("/{id}/accept-by-courier/{courierId}")
  public ResponseEntity<Void> acceptOrderByCourier(
      @PathVariable("id") int id, @PathVariable("courierId") int courierId) {
    orderService.acceptOrderByCourier(id, courierId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/courier-accepted-orders/{courierId}")
  public ResponseEntity<List<OrderDto>> getCourierAcceptedOrders(
      @PathVariable("courierId") int courierId) {
    return ResponseEntity.ok(orderService.getCourierAcceptedOrders(courierId));
  }

  @PatchMapping("/{id}/accept-in-way")
  public ResponseEntity<Void> acceptInWayOrder(@PathVariable("id") int id) {
    orderService.acceptInWayOrder(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/courier-in-way-orders/{courierId}")
  public ResponseEntity<List<OrderDto>> getCourierInWayOrders(
      @PathVariable("courierId") int courierId) {
    return ResponseEntity.ok(orderService.getCourierInWayOrders(courierId));
  }

  @GetMapping("/courier-completed-orders/{courierId}")
  public ResponseEntity<List<OrderDto>> getCourierCompletedOrders(
      @PathVariable("courierId") int courierId) {
    return ResponseEntity.ok(orderService.getCourierCompletedOrders(courierId));
  }
}
