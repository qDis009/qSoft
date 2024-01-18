package kz.qBots.qSoft.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
  private Integer id;
  private int totalPrice;
  private int itemCount;
  private double totalDiscount;
  private boolean enabled;
  private String itemName;
  private Integer userId;
  private Integer itemId;
  private Integer orderId;
}
