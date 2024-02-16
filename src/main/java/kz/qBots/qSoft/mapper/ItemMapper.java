package kz.qBots.qSoft.mapper;

import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.entity.Item;
import kz.qBots.qSoft.rest.request.ItemRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {ImageMapper.class})
public interface ItemMapper {
  @Mapping(target = "subCategoryId", source = "subCategory.id")
  @Mapping(target = "created", expression = "java(item.getCreated().toString())")
  @Mapping(target = "discount", expression = "java(item.getDiscount())")
  ItemDto mapItemToItemDto(Item item);

  @Mapping(target = "subCategory.id", source = "subCategoryId")
  Item mapItemRequestToItem(ItemRequest itemRequest);
  @Mapping(target = "subCategory.id",source = "subCategoryId")
  Item mapItemDtoToItem(ItemDto itemDto);
}
