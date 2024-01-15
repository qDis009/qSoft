package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.OrderComponent;
import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.mapper.OrderMapper;
import kz.qBots.qSoft.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderComponent orderComponent;
  private final OrderMapper orderMapper;

  @Override
  public Page<OrderDto> findByUserId(int id, Pageable pageable) {
    return orderComponent.findByUserId(id, pageable).map(orderMapper::mapOrderToOrderDto);
  }

  @Override
  public OrderDto findById(int id) {
    return orderMapper.mapOrderToOrderDto(orderComponent.findById(id));
  }
}
