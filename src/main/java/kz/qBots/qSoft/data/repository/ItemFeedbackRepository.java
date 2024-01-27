package kz.qBots.qSoft.data.repository;

import kz.qBots.qSoft.data.entity.ItemFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemFeedbackRepository extends JpaRepository<ItemFeedback, Integer> {
  @Query("select i from ItemFeedback i where i.item.id=:itemId and i.user.id=:userId")
  ItemFeedback findByItemIdAndUserId(int itemId, int userId);
}
