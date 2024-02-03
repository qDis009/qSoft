package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ItemComponent;
import kz.qBots.qSoft.data.component.SubCategoryComponent;
import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.SubCategoryDto;
import kz.qBots.qSoft.data.entity.Item;
import kz.qBots.qSoft.data.entity.SubCategory;
import kz.qBots.qSoft.mapper.ItemMapper;
import kz.qBots.qSoft.mapper.SubCategoryMapper;
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
    SubCategory subCategory = subCategoryComponent.findById(subCategoryId);
    Set<Item> subCategoryItems = subCategory.getItems();
    Set<Integer> favoriteItems = itemComponent.findIdsByUserId(userId);
    List<ItemDto> subCategoryItemsDtos =
        subCategoryItems.stream().map(itemMapper::mapItemToItemDto).toList();
    subCategoryItemsDtos.forEach(
        it -> {
          if (favoriteItems.contains(it.getId())) {
            it.setFavorite(true);
          }
        });
    return subCategoryItemsDtos;
  }

  @Override
  public void setEnable(boolean enable, int id) {
    subCategoryComponent.setEnable(enable, id);
  }
}
