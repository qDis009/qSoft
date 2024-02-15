package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.ItemFeedback;

import java.util.List;

public interface ItemFeedbackComponent {
    ItemFeedback create(ItemFeedback itemFeedback);
    ItemFeedback findById(int id);
    void delete(ItemFeedback itemFeedback);
    List<ItemFeedback> findByItemIdAndUserId(int itemId, int userId);
}
