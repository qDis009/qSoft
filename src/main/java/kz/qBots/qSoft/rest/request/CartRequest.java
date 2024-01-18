package kz.qBots.qSoft.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {
  private Integer userId;
  private Integer itemId;
  private int  itemCount;
  private int itemPrice;
  private double itemDiscount;
  private String itemName;
}
