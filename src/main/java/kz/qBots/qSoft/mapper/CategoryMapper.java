package kz.qBots.qSoft.mapper;

import kz.qBots.qSoft.data.dto.CategoryDto;
import kz.qBots.qSoft.data.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  CategoryDto mapCategoryToCategoryDto(Category category);
}
