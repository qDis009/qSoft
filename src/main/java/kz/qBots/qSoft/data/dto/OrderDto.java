package kz.qBots.qSoft.data.dto;

import kz.qBots.qSoft.data.entity.Cart;
import kz.qBots.qSoft.data.entity.Shop;
import kz.qBots.qSoft.data.enums.DeliveryType;
import kz.qBots.qSoft.data.enums.OrderStatus;
import kz.qBots.qSoft.data.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrderDto {
  private Integer id;
  private int price;
  private PaymentType paymentType;
  private DeliveryType deliveryType;
  private OrderStatus orderStatus;
  private String created;
  private String delivered;
  private Integer shopId;
  private Set<Cart> carts;
}
