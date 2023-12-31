package kz.qBots.qSoft.data.repository;

import kz.qBots.qSoft.data.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    Page<Order>findByUser_Id(int id, Pageable pageable);
}
