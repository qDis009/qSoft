package kz.qBots.qSoft.data.dto;

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
  private Set<ItemDto> items;
}
