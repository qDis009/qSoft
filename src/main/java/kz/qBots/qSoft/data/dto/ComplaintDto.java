package kz.qBots.qSoft.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintDto {
  private Integer id;
  private String description;
  private Integer userId;
  private Integer shopId;
}
