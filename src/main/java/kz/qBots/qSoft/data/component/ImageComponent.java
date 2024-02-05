package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.Image;

public interface ImageComponent {
  Image create(Image image);

  Image update(Image image);
  Image findById(int id);
  void delete(int id);
}
