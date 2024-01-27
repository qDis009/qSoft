package kz.qBots.qSoft.mapper;

import kz.qBots.qSoft.data.dto.OrderDto;
import kz.qBots.qSoft.data.entity.Order;
import kz.qBots.qSoft.rest.request.OrderRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {CartMapper.class})
public interface OrderMapper {
    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "shopId",source = "shop.id")
    @Mapping(target = "courierId",source = "courier.id")
    @Mapping(target = "created", expression = "java(order.getCreated().toString())")
    OrderDto mapOrderToOrderDto(Order order);
    @Mapping(target = "user.id",source = "userId")
    Order mapOrderRequestToOrder(OrderRequest orderRequest);
}