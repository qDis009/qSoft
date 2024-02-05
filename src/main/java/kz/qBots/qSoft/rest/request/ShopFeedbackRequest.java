package kz.qBots.qSoft.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopFeedbackRequest {
  private String comment;
  private Integer userId;
  private String created;
}
