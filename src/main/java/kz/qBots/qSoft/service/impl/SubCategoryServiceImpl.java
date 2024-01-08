package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.SubCategoryComponent;
import kz.qBots.qSoft.data.dto.SubCategoryDto;
import kz.qBots.qSoft.data.entity.SubCategory;
import kz.qBots.qSoft.mapper.SubCategoryMapper;
import kz.qBots.qSoft.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
  private final SubCategoryComponent subCategoryComponent;
  private final SubCategoryMapper subCategoryMapper;

  @Override
  public SubCategoryDto findById(int id) {
    SubCategory subCategory = subCategoryComponent.findById(id);
    return subCategoryMapper.mapSubCategoryToSubCategoryDto(subCategory);
  }
}
