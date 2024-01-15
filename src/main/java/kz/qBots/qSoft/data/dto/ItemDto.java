package kz.qBots.qSoft.data.dto;

import kz.qBots.qSoft.data.enums.ItemType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ItemDto {
  private Integer id;
  private String nameRu;
  private String nameKz;
  private int soldCount;
  private Integer discountPercentage;
  private int retailPrice;
  private int wholesalePrice;
  private int gradeCount;
  private int feedbackGradeCount;
  private boolean enabled;
  private boolean deleted;
  private double grade;
  private double feedbackGrade;
  private String imageUrl;
  private String imageUrl3D;
  private String article;
  private String descriptionKz;
  private String descriptionRu;
  private String created;
  private ItemType itemType;
  private boolean isFavorite;
  private Integer subCategoryId;
}
