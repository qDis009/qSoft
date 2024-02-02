package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.CategoryComponent;
import kz.qBots.qSoft.data.dto.CategoryDto;
import kz.qBots.qSoft.data.entity.Category;
import kz.qBots.qSoft.mapper.CategoryMapper;
import kz.qBots.qSoft.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryComponent categoryComponent;
  private final CategoryMapper categoryMapper;

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
    categoryComponent.setEnable(enable, id);
  }
}
