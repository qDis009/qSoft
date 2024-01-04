package kz.qBots.qSoft.data.dto;

import kz.qBots.qSoft.data.entity.Cart;
import kz.qBots.qSoft.data.entity.Item;
import kz.qBots.qSoft.data.entity.ItemFeedback;
import kz.qBots.qSoft.data.entity.Order;
import kz.qBots.qSoft.data.enums.ClientType;
import kz.qBots.qSoft.data.enums.Language;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {
  private Integer id;
  private long chatId;
  private boolean deleted;
  private String fullName;
  private String phoneNumber;
  private String IEName;
  private String tgUserName;
  private Language language;
  private String address;
  private ClientType clientType;
  private Set<Cart> carts;
  private Set<Order> orders;
  private Set<ItemFeedback> itemFeedbacks;
  private Set<Item> items;
}
