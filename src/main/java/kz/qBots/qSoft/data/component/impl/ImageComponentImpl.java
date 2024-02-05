package kz.qBots.qSoft.data.component.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.qBots.qSoft.data.component.ImageComponent;
import kz.qBots.qSoft.data.entity.Image;
import kz.qBots.qSoft.data.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageComponentImpl implements ImageComponent {
  private final ImageRepository imageRepository;

  @Override
  public Image create(Image image) {
    return imageRepository.save(image);
  }

  @Override
  public Image update(Image image) {
    return imageRepository.save(image);
  }

  @Override
  public Image findById(int id) {
    return imageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public void delete(int id) {
    imageRepository.deleteById(id);
  }
}
