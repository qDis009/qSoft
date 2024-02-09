package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.CategoryComponent;
import kz.qBots.qSoft.data.dto.CategoryDto;
import kz.qBots.qSoft.data.entity.Category;
import kz.qBots.qSoft.data.entity.SubCategory;
import kz.qBots.qSoft.mapper.CategoryMapper;
import kz.qBots.qSoft.service.CategoryService;
import kz.qBots.qSoft.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryComponent categoryComponent;
  private final CategoryMapper categoryMapper;
  private final SubCategoryService subCategoryService;

  @Override
  public Page<CategoryDto> findAll(Pageable pageable) {
    return categoryComponent.findAll(pageable).map(categoryMapper::mapCategoryToCategoryDto);
  }

  @Override
  public CategoryDto findById(int id) {
    Category category = categoryComponent.findById(id);
    return categoryMapper.mapCategoryToCategoryDto(category);
  }

  @Override
  public void setEnable(boolean enable, int id) {
    updateEnableForItemsAndSubCategory(id, enable);
    categoryComponent.setEnable(enable, id);
  }

  @Override
  public List<CategoryDto> getEnableCategories() {
    return categoryComponent.getEnableCategories().stream()
        .map(categoryMapper::mapCategoryToCategoryDto)
        .toList();
  }

  private void updateEnableForItemsAndSubCategory(int id, boolean newEnable) {
    Category category = categoryComponent.findById(id);
    Set<SubCategory> subCategories = category.getSubCategories();
    for (SubCategory subCategory : subCategories) {
      if (!subCategory.isDeleted()) {
        subCategoryService.setEnable(newEnable, subCategory.getId());
      }
    }
  }
}
