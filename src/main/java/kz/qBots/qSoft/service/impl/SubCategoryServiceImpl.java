package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ItemComponent;
import kz.qBots.qSoft.data.component.SubCategoryComponent;
import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.SubCategoryDto;
import kz.qBots.qSoft.data.entity.SubCategory;
import kz.qBots.qSoft.mapper.ItemMapper;
import kz.qBots.qSoft.mapper.SubCategoryMapper;
import kz.qBots.qSoft.rest.request.SubCategoryRequest;
import kz.qBots.qSoft.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
  private final SubCategoryComponent subCategoryComponent;
  private final SubCategoryMapper subCategoryMapper;
  private final ItemMapper itemMapper;
  private final ItemComponent itemComponent;

  @Override
  public SubCategoryDto findById(int id) {
    SubCategory subCategory = subCategoryComponent.findById(id);
    return subCategoryMapper.mapSubCategoryToSubCategoryDto(subCategory);
  }

  @Override
  public List<ItemDto> findItemsBySubCategoryId(int subCategoryId, int userId) {
    Set<Integer> favoriteItems = itemComponent.findIdsByUserId(userId);
    List<ItemDto> items =
        itemComponent.findItemsBySubCategoryId(subCategoryId).stream()
            .map(itemMapper::mapItemToItemDto)
            .toList();
    items.forEach(
        it -> {
          if (favoriteItems.contains(it.getId())) {
            it.setFavorite(true);
          }
        });
    return items;
  }

  @Override
  public void setEnable(boolean enable, int id) {
    itemComponent.updateEnableForItemsInSubCategory(id, enable);
    subCategoryComponent.setEnable(enable, id);
  }

  @Override
  public List<SubCategoryDto> getAll() {
    return subCategoryComponent.findAll().stream()
        .map(subCategoryMapper::mapSubCategoryToSubCategoryDto)
        .toList();
  }

  @Override
  public void delete(int id) {
    SubCategory subCategory = subCategoryComponent.findById(id);
    subCategory.setEnabled(false);
    subCategory.setDeleted(true);
    itemComponent.deleteItemsBySubCategoryId(id);
    subCategoryComponent.update(subCategory);
  }

  @Override
  public List<SubCategoryDto> getSubCategoriesByCategoryId(int categoryId) {
    return subCategoryComponent.findSubCategoriesByCategoryId(categoryId).stream()
        .map(subCategoryMapper::mapSubCategoryToSubCategoryDto)
        .toList();
  }

  @Override
  public SubCategoryDto create(SubCategoryRequest subCategoryRequest) {
    SubCategory subCategory =
        subCategoryComponent.create(
            subCategoryMapper.mapSubCategoryRequestToSubCategory(subCategoryRequest));
    return subCategoryMapper.mapSubCategoryToSubCategoryDto(subCategory);
  }
}
