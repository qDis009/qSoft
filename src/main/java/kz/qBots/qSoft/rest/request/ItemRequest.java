package kz.qBots.qSoft.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequest {
    private String nameRu;
    private String nameKz;
    private String descriptionKz;
    private String descriptionRu;
    private String article;
    private Integer count;
    private Integer subCategoryId;
    private int retailPrice;
    private int wholesalePrice;
    private int discountPercentage;
    private String imageUrl3D;
    private boolean hit;
}
