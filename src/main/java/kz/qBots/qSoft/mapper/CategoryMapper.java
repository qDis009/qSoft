package kz.qBots.qSoft.mapper;

import kz.qBots.qSoft.data.dto.CategoryDto;
import kz.qBots.qSoft.data.entity.Category;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {SubCategoryMapper.class})

public interface CategoryMapper {

  CategoryDto mapCategoryToCategoryDto(Category category);
}
