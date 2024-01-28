package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.OrderDto;
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
  void rejectOrder(int id,String reason,String role);
  List<OrderDto> getInWayOrders();
  List<OrderDto> getManagerCompletedOrders();
  void acceptOrderByManager(int id);
  List<OrderDto> getStorekeeperNewOrders();
  void acceptOrderByStorekeeper(int id);
  List<OrderDto> getStorekeeperAcceptedOrders();
  void completeOrderByStorekeeper(int id);
  List<OrderDto> getStorekeeperCompletedOrders();
  List<OrderDto> getCourierNewOrders();
  void acceptOrderByCourier(int id,int courierId);
  List<OrderDto> getCourierAcceptedOrders(int courierId);
  List<OrderDto> getCourierInWayOrders(int courierId);
  void acceptInWayOrder(int id);
  List<OrderDto> getCourierCompletedOrders(int courierId);
}
