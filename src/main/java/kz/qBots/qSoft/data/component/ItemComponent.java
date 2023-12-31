package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemComponent {
  Item create(Item item);

  List<Item> findAllByIds(List<Integer> ids);

  Item findById(int id);

  void update(Item item);

  Page<Item> findAll(Pageable pageable);
  List<Item> findItemsByUserId(int userId);
}
