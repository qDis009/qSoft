package kz.qBots.qSoft.data.component.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.qBots.qSoft.data.component.OrderComponent;
import kz.qBots.qSoft.data.entity.Order;
import kz.qBots.qSoft.data.enums.DeliveryType;
import kz.qBots.qSoft.data.enums.OrderStatus;
import kz.qBots.qSoft.data.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByOrderStatus(status);
    }

    @Override
    public List<Order> findByExcludedOrderStatus(List<OrderStatus> excludedOrderStatus) {
        return orderRepository.findByOrderStatusNotIn(excludedOrderStatus);
    }

    @Override
    public List<Order> findByOrderStatuses(List<OrderStatus> orderStatuses) {
        return orderRepository.findByOrderStatusIn(orderStatuses);
    }

    @Override
    public List<Order> findByOrderStatusAndDeliveryType(OrderStatus status, DeliveryType deliveryType) {
        return orderRepository.findByOrderStatusAndDeliveryTypeEquals(status,deliveryType);
    }

    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findByOrderStatusAndCourierId(OrderStatus orderStatus, int courierId) {
        return orderRepository.findByOrderStatusAndCourierId(orderStatus,courierId);
    }
}
