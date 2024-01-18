package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.CartComponent;
import kz.qBots.qSoft.data.component.OrderComponent;
import kz.qBots.qSoft.data.dto.CartDto;
import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.data.entity.Cart;
import kz.qBots.qSoft.data.entity.Order;
import kz.qBots.qSoft.data.enums.OrderStatus;
import kz.qBots.qSoft.mapper.CartMapper;
import kz.qBots.qSoft.mapper.OrderMapper;
import kz.qBots.qSoft.rest.request.OrderRequest;
import kz.qBots.qSoft.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderComponent orderComponent;
  private final OrderMapper orderMapper;
  private final CartComponent cartComponent;
  private final CartMapper cartMapper;

  @Override
  public Page<OrderDto> findByUserId(int id, Pageable pageable) {
    return orderComponent.findByUserId(id, pageable).map(orderMapper::mapOrderToOrderDto);
  }

  @Override
  public OrderDto findById(int id) {
    return orderMapper.mapOrderToOrderDto(orderComponent.findById(id));
  }

  @Override
  @Transactional
  public OrderDto order(OrderRequest orderRequest) {
    Order order = orderComponent.create(orderMapper.mapOrderRequestToOrder(orderRequest));
    Set<Cart> orderCarts = order.getCarts();
    setOrderToCarts(orderCarts, order);
    order.setOrderStatus(OrderStatus.NEW);
    orderComponent.update(order);
    return orderMapper.mapOrderToOrderDto(order);
  }
  private void setOrderToCarts(Set<Cart> orderCarts, Order order) {
    for (Cart orderCart : orderCarts) {
      Cart cart = cartComponent.findById(orderCart.getId());
      cart.setOrder(order);
      cart.setEnabled(false);
      order.addDiscount(cart.getTotalDiscount());
      order.addTotal(cart.getTotalPrice());
      cartComponent.update(cart);
    }
  }

  @Override
  public void setStatus(int id, String status) {
    orderComponent.setStatus(id, status);
  }

  @Override
  public List<OrderDto> getManagerNewOrders() {
    return orderComponent.findByStatus(OrderStatus.NEW).stream()
        .map(orderMapper::mapOrderToOrderDto)
        .toList();
  }

  @Override
  public List<OrderDto> getManagerAcceptedOrders() {
    List<OrderStatus> excludedOrderStatus =
        List.of(OrderStatus.NEW, OrderStatus.GIVEN, OrderStatus.REJECTED);
    return orderComponent.findByExcludedOrderStatus(excludedOrderStatus).stream()
        .map(orderMapper::mapOrderToOrderDto)
        .toList();
  }

  @Override
  public void rejectOrder(int id, String reason) {
    Order order = orderComponent.findById(id);
    order.setOrderStatus(OrderStatus.REJECTED);
    order.setRejectReason(reason);
    orderComponent.update(order);
  }

  @Override
  public List<OrderDto> getInWayOrders() {
    return orderComponent.findByStatus(OrderStatus.IN_THE_WAY).stream()
        .map(orderMapper::mapOrderToOrderDto)
        .toList();
  }

  @Override
  public List<OrderDto> getCompletedOrders() {
    List<OrderStatus> completedOrderStatuses = List.of(OrderStatus.GIVEN, OrderStatus.REJECTED);
    return orderComponent.findByOrderStatuses(completedOrderStatuses).stream()
        .map(orderMapper::mapOrderToOrderDto)
        .toList();
  }
}
