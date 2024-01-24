package kz.qBots.qSoft.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
  private Integer id;
  private int totalPrice;
  private int itemCount;
  private boolean enabled;
  private double totalDiscount;
  private Integer userId;
  private ItemDto item;
  private Integer orderId;
}
