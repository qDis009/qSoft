package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.Order;
import kz.qBots.qSoft.data.enums.DeliveryType;
import kz.qBots.qSoft.data.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderComponent {
  Page<Order> findByUserId(int id, Pageable pageable);

  Order findById(int id);

  void update(Order order);

  List<Order> findByStatus(OrderStatus status);

  List<Order> findByExcludedOrderStatus(List<OrderStatus> excludedOrderStatus);

  List<Order> findByOrderStatuses(List<OrderStatus> orderStatuses);

  List<Order> findByOrderStatusAndDeliveryType(OrderStatus status, DeliveryType deliveryType);

  Order create(Order order);

  List<Order> findByOrderStatusAndCourierId(OrderStatus orderStatus, int courierId);

  List<Order> findByOrderStatusesAndCourierId(List<OrderStatus> orderStatuses, int courierId);
}
