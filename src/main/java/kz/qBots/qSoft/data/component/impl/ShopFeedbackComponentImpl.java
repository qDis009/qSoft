package kz.qBots.qSoft.data.component.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.qBots.qSoft.data.component.ShopFeedbackComponent;
import kz.qBots.qSoft.data.entity.ShopFeedback;
import kz.qBots.qSoft.data.repository.ShopFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShopFeedbackComponentImpl implements ShopFeedbackComponent {
  private final ShopFeedbackRepository shopFeedbackRepository;

  @Override
  public ShopFeedback create(ShopFeedback shopFeedback) {
    return shopFeedbackRepository.save(shopFeedback);
  }

  @Override
  public ShopFeedback findById(int id) {
    return shopFeedbackRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public ShopFeedback update(ShopFeedback shopFeedback) {
    return shopFeedbackRepository.save(shopFeedback);
  }

  @Override
  public ShopFeedback findShopFeedbackByCreatedTime(LocalDateTime created) {
    return shopFeedbackRepository
        .findShopFeedbackByCreated(created)
        .orElseThrow(EntityNotFoundException::new);
  }
}
