package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ImageComponent;
import kz.qBots.qSoft.data.component.ItemComponent;
import kz.qBots.qSoft.data.component.ShopFeedbackComponent;
import kz.qBots.qSoft.data.entity.Image;
import kz.qBots.qSoft.data.entity.Item;
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
  private final ItemComponent itemComponent;
  private static final String UPLOAD_SHOP_FEEDBACK_PATH = "D:\\qshop\\shopFeedbacks\\files\\%s.%s";
  private static final String UPLOAD_ITEM_PHOTO_PATH = "D:\\qshop\\items\\photos\\%s.%s";

  @Override
  public void uploadShopFeedbackFile(int shopFeedbackId, List<MultipartFile> multipartFiles) {
    ShopFeedback shopFeedback = shopFeedbackComponent.findById(shopFeedbackId);
    Set<Image> images = new HashSet<>();
    for (MultipartFile multipartFile : multipartFiles) {
      Image image = new Image();
      imageComponent.create(image);
      String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
      String fileName = String.format(UPLOAD_SHOP_FEEDBACK_PATH, multipartFile.getOriginalFilename(), extension);
      File file = new File(fileName);
      try {
        multipartFile.transferTo(file);
      } catch (IOException e) {
        // TODO log
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

  @Override
  public void uploadItemPhotos(int itemId, List<MultipartFile> multipartFiles) {
    Item item = itemComponent.findById(itemId);
    Set<Image> images = new HashSet<>();
    for (MultipartFile multipartFile : multipartFiles) {
      Image image = new Image();
      imageComponent.create(image);
      String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
      String fileName = String.format(UPLOAD_ITEM_PHOTO_PATH, image.getId(), extension);
      File file = new File(fileName);
      try {
        multipartFile.transferTo(file);
      } catch (IOException e) {
        // TODO log
      }
      image.setPath(fileName);
      image.setItem(item);
      imageComponent.update(image);
      images.add(image);
    }
    item.setImages(images);
    itemComponent.update(item);
  }
}
