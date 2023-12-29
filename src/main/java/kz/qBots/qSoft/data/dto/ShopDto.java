package kz.qBots.qSoft.data.dto;

import kz.qBots.qSoft.data.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ShopDto {
    private Integer id;
    private String name;
    private Set<ShopFeedback> shopFeedbacks;
    private Set<Item> items;
    private Set<Complaint> complaints;
}
