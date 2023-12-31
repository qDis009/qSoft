package kz.qBots.qSoft.data.component.impl;

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
}
