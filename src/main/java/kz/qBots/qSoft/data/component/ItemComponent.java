package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.Item;

import java.util.List;

public interface ItemComponent {
    Item create(Item item);
    List<Item> findAllByIds(List<Integer> ids);
    Item findById(int id);
    void update(Item item);
}
