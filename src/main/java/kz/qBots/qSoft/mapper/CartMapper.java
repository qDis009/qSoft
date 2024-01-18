package kz.qBots.qSoft.mapper;

import kz.qBots.qSoft.data.dto.CartDto;
import kz.qBots.qSoft.data.entity.Cart;
import kz.qBots.qSoft.rest.request.CartRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
  @Mapping(target = "user.id", source = "userId")
  @Mapping(target = "item.id", source = "itemId")
  Cart mapCartRequestToCart(CartRequest model);

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "itemId", source = "item.id")
  @Mapping(target = "orderId", source = "order.id")
  @Mapping(target = "totalPrice",expression = "java(cart.getTotalPrice())")
  @Mapping(target = "totalDiscount",expression = "java(cart.getTotalDiscount())")
  CartDto mapCartToCartDto(Cart cart);

}
