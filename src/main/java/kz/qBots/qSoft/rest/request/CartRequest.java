package kz.qBots.qSoft.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {
    private Integer userId;
    private Integer itemId;
    private Integer orderId;
    private Integer itemCount;
}
