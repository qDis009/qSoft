package kz.qBots.qSoft.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CategoryDto {
  private Integer id;
  private String nameKz;
  private String nameRu;
  private boolean enable;
  private Set<SubCategoryDto> subCategories;
}
