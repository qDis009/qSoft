package kz.qBots.qSoft.data.dto;

import kz.qBots.qSoft.data.enums.ClientType;
import kz.qBots.qSoft.data.enums.Language;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    private Integer id;
    private long chatId;
    private String fullName;
    private String phoneNumber;
    private String IEName;
    private Language language;
    private String address;
    private ClientType clientType;
    private String shopName;
    private Set<OrderDto> accepterOrders;
}
