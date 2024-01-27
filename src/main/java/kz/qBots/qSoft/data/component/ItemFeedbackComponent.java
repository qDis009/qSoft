package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.ItemFeedback;

public interface ItemFeedbackComponent {
    ItemFeedback create(ItemFeedback itemFeedback);
    ItemFeedback findById(int id);
    void delete(ItemFeedback itemFeedback);
    ItemFeedback findByItemIdAndUserId(int itemId,int userId);
}
