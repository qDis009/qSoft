package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.Item;
import kz.qBots.qSoft.data.enums.ItemType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ItemComponent {
    Item create(Item item);

    List<Item> findAllByIds(List<Integer> ids);
    List<Item> findByItemTypeAndDiscountPercentageExist(ItemType itemType);

    Item findById(int id);

    void update(Item item);

    Page<Item> findAll(Pageable pageable);

    Set<Integer> findIdsByUserId(int userId);
    List<Item> findItemsByUserId(int userId);

    List<Item> findItemsByItemType(ItemType itemType);

    List<Item> findAll();
}
