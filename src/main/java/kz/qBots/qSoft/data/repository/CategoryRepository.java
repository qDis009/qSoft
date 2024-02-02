package kz.qBots.qSoft.data.repository;

import kz.qBots.qSoft.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
  @Modifying
  @Transactional
  @Query(value = "update market.category set enabled=:enable where id=:id", nativeQuery = true)
  void setEnable(boolean enable, int id);
}
