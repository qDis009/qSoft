package kz.qBots.qSoft.mapper;

import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
  @Mapping(target = "created", expression = "java(item.getCreated().toString())")
  ItemDto mapItemToItemDto(Item item);
}
