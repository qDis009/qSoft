package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderComponent {
    Page<Order> findByUserId(int id, Pageable pageable);
    Order findById(int id);
    void update(Order order);
    void setStatus(int id,String status);
}
