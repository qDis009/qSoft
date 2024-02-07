package kz.qBots.qSoft.data.repository;

import kz.qBots.qSoft.data.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
  @Modifying
  @Transactional
  @Query(value = "update market.sub_category set enabled=:enable where id=:id", nativeQuery = true)
  void setEnable(boolean enable, int id);

  @Query("select s from SubCategory s where s.category.id=:categoryId and s.enabled=true")
  List<SubCategory> findSubCategoriesByCategory_Id(int categoryId);

  @Query("select s from SubCategory s where s.deleted=false")
  List<SubCategory> findAll();
}
