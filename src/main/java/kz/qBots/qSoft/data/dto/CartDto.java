package kz.qBots.qSoft.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
    private Integer id;
    private Integer userId;
    private Integer itemId;
    private Integer orderId;
    private int itemCount;
}
