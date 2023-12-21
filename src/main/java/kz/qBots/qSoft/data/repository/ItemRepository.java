package kz.qBots.qSoft.data.repository;

import kz.qBots.qSoft.data.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {
    List<Item> findAllByIdIn(List<Integer> ids);
}
