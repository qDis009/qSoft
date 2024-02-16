package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.SubCategory;

import java.util.List;

public interface SubCategoryComponent {
  SubCategory findById(int id);

  void setEnable(boolean enable, int id);

  List<SubCategory> findSubCategoriesByCategoryId(int categoryId);

  List<SubCategory> findAll();

  void update(SubCategory subCategory);
  SubCategory create(SubCategory subCategory);
}
