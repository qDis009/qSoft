package kz.qBots.qSoft.rest.request;

import kz.qBots.qSoft.data.enums.DeliveryType;
import kz.qBots.qSoft.data.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrderRequest {
  private DeliveryType deliveryType;
  private PaymentType paymentType;
  private Set<CartRequest> carts;
  private String created;
  private Integer userId;
  private Integer shopId;
}
