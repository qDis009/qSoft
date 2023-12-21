package kz.qBots.qSoft.data.dto;

import kz.qBots.qSoft.data.entity.Shop;
import kz.qBots.qSoft.data.entity.SubCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CategoryDto {
    private Integer id;
    private String nameKz;
    private String nameRu;
    private Set<Shop> shops;
    private Set<SubCategory> subCategories;
}
