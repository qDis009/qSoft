package kz.qBots.qSoft.data.dto;

import kz.qBots.qSoft.data.entity.Cart;
import kz.qBots.qSoft.data.entity.ItemFeedback;
import kz.qBots.qSoft.data.entity.Shop;
import kz.qBots.qSoft.data.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class ItemDto {
  private Integer id;
  private String nameRu;
  private String nameKz;
  private int soldCount;
  private int discountPercentage;
  private int retailPrice;
  private int wholesalePrice;
  private boolean enabled;
  private boolean deleted;
  private double grade;
  private String imageUrl;
  private String imageUrl3D;
  private String article;
  private String descriptionKz;
  private String descriptionRu;
  private LocalDateTime created;
  private Set<ItemFeedback> itemFeedbacks;
  private Set<Cart> carts;
  private Set<User> users;
  private Shop shop;
}
