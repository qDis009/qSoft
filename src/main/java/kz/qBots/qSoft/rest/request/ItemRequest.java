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
    private int categoryId;
    private int subCategoryId;
    private String article;
    private Integer count;
    private int retailPrice;
    private int wholesalePrice;
    private Integer discountPercentage;
    private String created;
    private String imageUrl3D;
    private boolean hit;
}
