package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryComponent {
  Page<Category> findAll(Pageable pageable);

  Category findById(int id);
  void setEnable(boolean enable,int id);
  List<Category> getEnableCategories();
}
