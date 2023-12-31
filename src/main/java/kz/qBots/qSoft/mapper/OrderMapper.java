package kz.qBots.qSoft.mapper;

import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.data.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto mapOrderToOrderDto(Order order);
}
