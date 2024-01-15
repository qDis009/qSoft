package kz.qBots.qSoft.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemFeedbackRequest {
  private String comment;
  private double grade;
  private String created;
  private Integer itemId;
  private Integer userId;
  private String fullName;
}
