package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ImageComponent;
import kz.qBots.qSoft.data.component.ShopFeedbackComponent;
import kz.qBots.qSoft.data.entity.Image;
import kz.qBots.qSoft.data.entity.ShopFeedback;
import kz.qBots.qSoft.service.FileService;
import kz.qBots.qSoft.service.ShopFeedbackService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  private final ImageComponent imageComponent;
  private final ShopFeedbackComponent shopFeedbackComponent;
  private final ShopFeedbackService shopFeedbackService;
  private static final String UPLOAD_SHOP_FEEDBACK_PATH = "D:\\qshop\\shopFeedbacks\\files\\%s.%s";

  @Override
  public void uploadShopFeedbackFile(int shopFeedbackId, List<MultipartFile> multipartFiles) {
    ShopFeedback shopFeedback = shopFeedbackComponent.findById(shopFeedbackId);
    Set<Image> images = new HashSet<>();
    for (MultipartFile multipartFile : multipartFiles) {
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
      image.setShopFeedback(shopFeedback);
      imageComponent.update(image);
      images.add(image);
    }
    shopFeedback.setImages(images);
    shopFeedbackComponent.update(shopFeedback);
    if (!shopFeedback.getComment().isEmpty())
      shopFeedbackService.sendMessageToAdmin(
          shopFeedback.getComment(), shopFeedback.getUser(), images);
  }
}
