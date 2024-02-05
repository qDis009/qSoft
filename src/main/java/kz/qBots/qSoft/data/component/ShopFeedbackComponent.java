package kz.qBots.qSoft.data.component;


import kz.qBots.qSoft.data.entity.ShopFeedback;

public interface ShopFeedbackComponent {
    ShopFeedback create(ShopFeedback shopFeedback);
    ShopFeedback findById(int id);
    ShopFeedback update(ShopFeedback shopFeedback);
}
