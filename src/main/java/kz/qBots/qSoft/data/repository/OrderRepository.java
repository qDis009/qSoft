package kz.qBots.qSoft.data.repository;

import kz.qBots.qSoft.data.entity.Order;
import kz.qBots.qSoft.data.enums.DeliveryType;
import kz.qBots.qSoft.data.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
  Page<Order> findByUser_Id(int id, Pageable pageable);

  List<Order> findByOrderStatus(OrderStatus status);

  List<Order> findByOrderStatusAndDeliveryTypeEquals(OrderStatus status, DeliveryType deliveryType);

  List<Order> findByOrderStatusNotIn(List<OrderStatus> excludedOrderStatus);

  List<Order> findByOrderStatusIn(List<OrderStatus> orderStatuses);

  @Query(value = "select o from Order o where o.orderStatus=:status and o.courier.id=:courierId")
  List<Order> findByOrderStatusAndCourierId(OrderStatus status, int courierId);

  @Query(
      value =
          "select o from Order o where o.orderStatus in :orderStatuses and o.courier.id=:courierId")
  List<Order> findByOrderStatusesAndCourierId(List<OrderStatus> orderStatuses, int courierId);
}
