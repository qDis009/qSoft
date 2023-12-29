package kz.qBots.qSoft.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopFeedbackDto {
  private Integer id;
  private double grade;
  private String comment;
  private String created;
  private Integer shopId;
  private Integer userId;
}
