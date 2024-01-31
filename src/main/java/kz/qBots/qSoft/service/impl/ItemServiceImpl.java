package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ItemComponent;
import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.ItemFeedbackDto;
import kz.qBots.qSoft.data.entity.Item;
import kz.qBots.qSoft.mapper.ItemMapper;
import kz.qBots.qSoft.service.ItemFeedbackService;
import kz.qBots.qSoft.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
  private final ItemComponent itemComponent;
  private final ItemMapper itemMapper;
  private final ItemFeedbackService itemFeedbackService;

  @Override
  public void delete(int id) {
    Item item = itemComponent.findById(id);
    item.setDeleted(true);
    itemComponent.update(item);
  }

  @Override
  public Page<ItemDto> findAll(Pageable pageable) {
    return itemComponent.findAll(pageable).map(itemMapper::mapItemToItemDto);
  }

  @Override
  public List<ItemDto> findAll(int userId) {
    List<ItemDto> items =
        itemComponent.findAll().stream().map(itemMapper::mapItemToItemDto).toList();
    Set<Integer> favoriteItems = itemComponent.findIdsByUserId(userId);
    items.forEach(
        it -> {
          if (favoriteItems.contains(it.getId())) {
            it.setFavorite(true);
          }
        });
    return items;
  }

  @Override
  public List<ItemDto> findItemsByUserId(int userId) {
    return itemComponent.findItemsByUserId(userId).stream()
        .map(itemMapper::mapItemToItemDto)
        .toList();
  }

  @Override
  public ItemDto findById(int id) {
    Item item = itemComponent.findById(id);
    return itemMapper.mapItemToItemDto(item);
  }

  @Override
  public List<ItemDto> findRetailHit(int userId) {
    List<ItemDto> retailItems =
        itemComponent.findRetailItemsOrderBySoldCount().stream()
            .map(itemMapper::mapItemToItemDto)
            .toList();
    Set<Integer> favoriteItems = itemComponent.findIdsByUserId(userId);
    retailItems.forEach(
        it -> {
          if (favoriteItems.contains(it.getId())) {
            it.setFavorite(true);
          }
        });
    return retailItems;
  }

  @Override
  public List<ItemDto> findWholesaleHit(int userId) {
    List<ItemDto> wholesaleItems =
        itemComponent.findWholesaleItemsOrderBySoldCount().stream()
            .map(itemMapper::mapItemToItemDto)
            .toList();
    Set<Integer> favoriteItems = itemComponent.findIdsByUserId(userId);
    wholesaleItems.forEach(
        it -> {
          if (favoriteItems.contains(it.getId())) {
            it.setFavorite(true);
          }
        });
    return wholesaleItems;
  }

  @Override
  public List<ItemDto> getStocks(int userId) {
    List<ItemDto> itemsWithStock =
        itemComponent.findRetailItemsWithDiscountPercentageExist().stream()
            .map(itemMapper::mapItemToItemDto)
            .toList();
    Set<Integer> favoriteItems = itemComponent.findIdsByUserId(userId);
    itemsWithStock.forEach(
        it -> {
          if (favoriteItems.contains(it.getId())) {
            it.setFavorite(true);
          }
        });
    return itemsWithStock;
  }

  @Override
  public List<ItemFeedbackDto> getFeedbacks(int id) {
    return itemFeedbackService.getFeedbacks(id);
  }

  @Override
  public void setEnable(boolean enable, int id) {
    itemComponent.setEnable(enable, id);
  }
}
