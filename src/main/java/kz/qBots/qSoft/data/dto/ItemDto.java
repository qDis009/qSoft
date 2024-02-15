package kz.qBots.qSoft.data.dto;

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
  private double discount;
  private int retailPrice;
  private int wholesalePrice;
  private int gradeCount;
  private int feedbackGradeCount;
  private boolean enabled;
  private boolean deleted;
  private double grade;
  private double feedbackGrade;
  private String imageUrl3D;
  private String article;
  private String descriptionKz;
  private String descriptionRu;
  private String created;
  private boolean isFavorite;
  private Integer subCategoryId;
  private Integer count;
  private Set<ImageDto> images;
}
