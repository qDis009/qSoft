package kz.qBots.qSoft.data.dto;

import kz.qBots.qSoft.data.entity.ItemFeedback;
import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.data.enums.ItemType;
import lombok.Getter;
import lombok.Setter;

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
  private String created;
  private Set<ItemFeedback> itemFeedbacks;
  private Integer shopId;
  private ItemType itemType;
  private Set<User> users;
}
