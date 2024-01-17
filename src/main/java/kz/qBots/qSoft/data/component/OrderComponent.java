package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.Order;
import kz.qBots.qSoft.data.enums.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderComponent {
    Page<Order> findByUserId(int id, Pageable pageable);
    Order findById(int id);
    void update(Order order);
    void setStatus(int id,String status);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByExcludedOrderStatus(List<OrderStatus> excludedOrderStatus);
}
