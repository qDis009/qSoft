package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.rest.request.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
  Page<OrderDto> findByUserId(int id, Pageable pageable);
  OrderDto findById(int id);
  OrderDto order(OrderRequest orderRequest);
}
