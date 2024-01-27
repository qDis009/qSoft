package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.ItemFeedbackDto;
import kz.qBots.qSoft.data.enums.ItemType;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {
  void delete(int id);

  Page<ItemDto> findAll(Pageable pageable);

  List<ItemDto> findAll(int userId);

  List<ItemDto> findItemsByUserId(int userId);

  ItemDto findById(int id);

  List<ItemDto> findItemsByItemType(ItemType itemType, int userId);

  List<ItemDto> getStocks(int userId);

  List<ItemFeedbackDto> getFeedbacks(int id);
}
