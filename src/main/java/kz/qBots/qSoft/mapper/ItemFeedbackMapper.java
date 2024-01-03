package kz.qBots.qSoft.mapper;

import kz.qBots.qSoft.data.dto.ItemFeedbackDto;
import kz.qBots.qSoft.data.entity.ItemFeedback;
import kz.qBots.qSoft.rest.request.ItemFeedbackRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemFeedbackMapper {
    @Mapping(target = "user.id",source = "userId")
    @Mapping(target = "item.id",source = "itemId")
    ItemFeedback mapItemFeedbackRequestToItemFeedback(ItemFeedbackRequest itemFeedbackRequest);
    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "itemId",source = "item.id")
    ItemFeedbackDto mapItemFeedbackToItemFeedbackDto(ItemFeedback itemFeedback);
}
