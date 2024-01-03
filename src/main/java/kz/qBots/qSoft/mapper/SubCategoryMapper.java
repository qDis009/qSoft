package kz.qBots.qSoft.mapper;

import kz.qBots.qSoft.data.dto.SubCategoryDto;
import kz.qBots.qSoft.data.entity.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubCategoryMapper {
  @Mapping(target = "categoryId", source = "category.id")
  SubCategoryDto mapSubCategoryToSubCategoryDto(SubCategory subCategory);
}
