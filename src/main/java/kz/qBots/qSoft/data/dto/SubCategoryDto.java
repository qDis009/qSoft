package kz.qBots.qSoft.data.dto;

import kz.qBots.qSoft.data.entity.Item;
import kz.qBots.qSoft.data.entity.Shop;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SubCategoryDto {
  private Integer id;
  private String nameKz;
  private String nameRu;
  private Integer categoryId;
  private Set<Shop> shops;
  private Set<Item> items;
}
