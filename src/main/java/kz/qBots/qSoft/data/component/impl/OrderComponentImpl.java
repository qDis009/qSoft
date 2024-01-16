package kz.qBots.qSoft.data.component.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.qBots.qSoft.data.component.OrderComponent;
import kz.qBots.qSoft.data.entity.Order;
import kz.qBots.qSoft.data.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderComponentImpl implements OrderComponent {
    private final OrderRepository orderRepository;
    @Override
    public Page<Order> findByUserId(int id, Pageable pageable) {
        return orderRepository.findByUser_Id(id,pageable);
    }

    @Override
    public Order findById(int id) {
        return orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void update(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void setStatus(int id, String status) {
        orderRepository.setStatus(id,status);
    }
}
