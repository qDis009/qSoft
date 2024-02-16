package kz.qBots.qSoft.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCategoryRequest {
    private String nameKz;
    private String nameRu;
    private Integer categoryId;
}
