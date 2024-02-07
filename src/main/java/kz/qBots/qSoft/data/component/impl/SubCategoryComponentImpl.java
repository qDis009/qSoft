package kz.qBots.qSoft.data.component.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.qBots.qSoft.data.component.SubCategoryComponent;
import kz.qBots.qSoft.data.entity.SubCategory;
import kz.qBots.qSoft.data.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryComponentImpl implements SubCategoryComponent {
  private final SubCategoryRepository subCategoryRepository;

  @Override
  public SubCategory findById(int id) {
    return subCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public void setEnable(boolean enable, int id) {
    subCategoryRepository.setEnable(enable, id);
  }

  @Override
  public List<SubCategory> findSubCategoriesByCategoryId(int categoryId) {
    return subCategoryRepository.findSubCategoriesByCategory_Id(categoryId);
  }

  @Override
  public List<SubCategory> findAll() {
    return subCategoryRepository.findAll();
  }


  @Override
  public void update(SubCategory subCategory) {
    subCategoryRepository.save(subCategory);
  }
}
