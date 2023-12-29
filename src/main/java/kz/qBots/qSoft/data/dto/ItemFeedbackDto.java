package kz.qBots.qSoft.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemFeedbackDto {
  private Integer id;
  private String comment;
  private double grade;
  private String created;
  private Integer shopId;
  private Integer userId;
}
