package kz.qBots.qSoft.data.repository;

import kz.qBots.qSoft.data.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
  List<Item> findAllByIdIn(List<Integer> ids);

  @Query("select u.items from User u where u.id = :userId")
  List<Item> findItemsByUserId(@Param("userId") Integer userId);

  @Query(
      "select item from Item item where item.retailPrice <> 0 and item.soldCount <> 0 or item.retailPrice <> 0 and item.hit=true order by item.soldCount desc")
  List<Item> findRetailOrderBySoldCount();

  @Query(
      "select item from Item item where item.wholesalePrice <> 0 and item.soldCount <> 0 or item.wholesalePrice <> 0 and item.hit=true order by item.soldCount desc")
  List<Item> findWholesaleOrderBySoldCount();

  @Query("select item from Item item where item.retailPrice <> 0 and item.discountPercentage <> 0")
  List<Item> findRetailItemsWithDiscountPercentageIsExist();

  @Modifying
  @Transactional
  @Query(value = "update market.item set enabled=:enable where id =:id", nativeQuery = true)
  void setEnable(boolean enable, int id);
}
