package kz.qBots.qSoft.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopFeedbackDto {
  private Integer id;
  private double grade;
  private String comment;
  private Integer shopId;
  private Integer userId;
  private String created;
  private boolean enable;
}
