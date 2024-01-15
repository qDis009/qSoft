package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.SubCategoryDto;
import kz.qBots.qSoft.data.entity.Item;

import java.util.List;

public interface SubCategoryService {
  SubCategoryDto findById(int id);
  List<ItemDto> findItemsBySubCategoryId(int subCategoryId, int userId);
}
