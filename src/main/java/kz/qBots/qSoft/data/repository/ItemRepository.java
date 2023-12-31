package kz.qBots.qSoft.data.repository;

import kz.qBots.qSoft.data.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {
    List<Item> findAllByIdIn(List<Integer> ids);
    @Query("select u.items from User u where u.id = :userId")
    List<Item> findItemsByUserId(@Param("userId") Integer userId);
}
