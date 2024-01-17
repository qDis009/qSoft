package kz.qBots.qSoft.data.repository;

import kz.qBots.qSoft.data.entity.Order;
import kz.qBots.qSoft.data.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
  Page<Order> findByUser_Id(int id, Pageable pageable);

  @Modifying
  @Transactional
  @Query(value = "update market.order set order_status = :status where id = :id", nativeQuery = true)
  void setStatus(int id, String status);
  List<Order> findByOrderStatus(OrderStatus status);
  List<Order> findByOrderStatusNotIn(List<OrderStatus> excludedOrderStatus);
}
