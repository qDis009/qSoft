package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.ShopFeedback;

import java.time.LocalDateTime;

public interface ShopFeedbackComponent {
  ShopFeedback create(ShopFeedback shopFeedback);

  ShopFeedback findById(int id);

  ShopFeedback update(ShopFeedback shopFeedback);
  ShopFeedback findShopFeedbackByCreatedTime(LocalDateTime created);
}
