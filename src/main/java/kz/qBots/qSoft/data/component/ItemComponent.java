package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ItemComponent {
  Item create(Item item);

  List<Item> findAllByIds(List<Integer> ids);

  List<Item> findRetailItemsWithDiscountPercentageExist();

  Item findById(int id);

  void update(Item item);

  Page<Item> findAll(Pageable pageable);

  Set<Integer> findIdsByUserId(int userId);

  List<Item> findItemsByUserId(int userId);

  List<Item> findRetailItemsOrderBySoldCount();

  List<Item> findWholesaleItemsOrderBySoldCount();

  List<Item> findEnableRetailItems();

  List<Item> findEnableWholesaleItems();

  void setEnable(boolean enable, int id);

  void updateEnableForItemsInSubCategory(int subCategoryId, boolean newEnable);

  List<Item> findItemsBySubCategoryId(int subCategoryId);

  void deleteItemsBySubCategoryId(int subCategoryId);
}
