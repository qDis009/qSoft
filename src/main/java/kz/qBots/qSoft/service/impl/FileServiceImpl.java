package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ImageComponent;
import kz.qBots.qSoft.data.component.ShopFeedbackComponent;
import kz.qBots.qSoft.data.dto.ShopFeedbackDto;
import kz.qBots.qSoft.data.entity.Image;
import kz.qBots.qSoft.data.entity.ShopFeedback;
import kz.qBots.qSoft.mapper.ShopFeedbackMapper;
import kz.qBots.qSoft.service.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  private final ImageComponent imageComponent;
  private static final String UPLOAD_SHOP_FEEDBACK_PATH = "D:\\qshop\\shopFeedbacks\\files\\%s.%s";

  @Override
  public Integer uploadShopFeedbackFile(MultipartFile multipartFile) {
    Image image = new Image();
    imageComponent.create(image);
    String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
    String fileName = String.format(UPLOAD_SHOP_FEEDBACK_PATH, image.getId(), extension);
    File file = new File(fileName);
    try {
      multipartFile.transferTo(file);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    image.setPath(fileName);
    return image.getId();
  }
}