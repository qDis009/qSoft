package kz.qBots.qSoft.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopFeedbackRequest {
  private double grade;
  private String comment;
  private Integer shopId;
  private Integer userId;
}
