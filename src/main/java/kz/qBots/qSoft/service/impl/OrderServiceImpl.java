package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.OrderComponent;
import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.data.entity.Order;
import kz.qBots.qSoft.data.enums.OrderStatus;
import kz.qBots.qSoft.mapper.OrderMapper;
import kz.qBots.qSoft.rest.request.OrderRequest;
import kz.qBots.qSoft.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderComponent orderComponent;
  private final OrderMapper orderMapper;

  @Override
  public Page<OrderDto> findByUserId(int id, Pageable pageable) {
    return orderComponent.findByUserId(id, pageable).map(orderMapper::mapOrderToOrderDto);
  }

  @Override
  public OrderDto findById(int id) {
    return orderMapper.mapOrderToOrderDto(orderComponent.findById(id));
  }

  @Override
  public OrderDto order(OrderRequest orderRequest) {
    // TODO
    return null;
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
    Order order=orderComponent.findById(id);
    order.setOrderStatus(OrderStatus.REJECTED);
    order.setRejectReason(reason);
    orderComponent.update(order);
  }
  @Override
  public List<OrderDto> getInWayOrders() {
    return orderComponent.findByStatus(OrderStatus.IN_THE_WAY).stream().map(orderMapper::mapOrderToOrderDto).toList();
  }
}
