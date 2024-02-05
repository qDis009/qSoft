package kz.qBots.qSoft.mapper;

import kz.qBots.qSoft.data.dto.ShopFeedbackDto;
import kz.qBots.qSoft.data.entity.ShopFeedback;
import kz.qBots.qSoft.rest.request.ShopFeedbackRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShopFeedbackMapper {
    @Mapping(target = "user.id",source = "userId")
    ShopFeedback mapShopFeedbackRequestToShopFeedback(ShopFeedbackRequest shopFeedbackRequest);
    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "shopId",source = "shop.id")
    @Mapping(target = "created", expression = "java(shopFeedback.getCreated().toString())")
    ShopFeedbackDto mapShopFeedbackToShopFeedbackDto(ShopFeedback shopFeedback);
}
