package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderDto> findByUserId(int id, Pageable pageable);
}
