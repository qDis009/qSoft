package kz.qBots.qSoft.mapper;

import kz.qBots.qSoft.data.dto.ImageDto;
import kz.qBots.qSoft.data.entity.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
  ImageDto mapImageToImageDto(Image image);
}
