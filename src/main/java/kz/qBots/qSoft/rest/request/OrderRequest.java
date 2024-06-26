package kz.qBots.qSoft.rest.request;

import kz.qBots.qSoft.data.dto.CartDto;
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
  private Set<CartDto> carts;
  private String created;
  private Integer userId;
  private String comment;
  private String phoneNumber;
  private String address;
  private String IEName;
  private String shopName;
  private String fullName;
}
