package kz.qBots.qSoft.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
  private Integer id;
  private int itemPrice;
  private int itemCount;
  private String itemName;
  private Integer userId;
  private Integer itemId;
  private Integer orderId;
}
