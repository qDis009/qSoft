package kz.qBots.qSoft.data.repository;

import kz.qBots.qSoft.data.entity.ShopFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ShopFeedbackRepository extends JpaRepository<ShopFeedback, Integer> {
  Optional<ShopFeedback> findShopFeedbackByCreated(LocalDateTime created);
}
