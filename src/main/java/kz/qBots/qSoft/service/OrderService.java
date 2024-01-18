package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.data.enums.OrderStatus;
import kz.qBots.qSoft.rest.request.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
  Page<OrderDto> findByUserId(int id, Pageable pageable);
  OrderDto findById(int id);
  OrderDto order(OrderRequest orderRequest);
  void setStatus(int id,String status);
  List<OrderDto> getManagerNewOrders();
  List<OrderDto> getManagerAcceptedOrders();
  void rejectOrder(int id,String reason);
  List<OrderDto> getInWayOrders();
  List<OrderDto> getCompletedOrders();
}
