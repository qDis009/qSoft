package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ItemComponent;
import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.ItemFeedbackDto;
import kz.qBots.qSoft.data.entity.Item;
import kz.qBots.qSoft.data.enums.ItemType;
import kz.qBots.qSoft.mapper.ItemMapper;
import kz.qBots.qSoft.service.ItemFeedbackService;
import kz.qBots.qSoft.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    List<Item> favoriteItems = itemComponent.findItemsByUserId(userId);
    for (Item it : favoriteItems) {
      items.get(it.getId()).setFavorite(true);
    }
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
  public List<ItemDto> findItemsByItemType(ItemType itemType, int userId) {
    List<ItemDto> items =
        itemComponent.findItemsByItemType(itemType).stream()
            .map(itemMapper::mapItemToItemDto)
            .toList();
    List<Item> favoriteItems = itemComponent.findItemsByUserId(userId);
    for (Item it : favoriteItems) {
      items.get(it.getId()).setFavorite(true);
    }
    return items;
  }

  @Override
  public List<ItemDto> getStocks(int userId) {
    List<Item> items = itemComponent.findAll();
    List<Item> itemsWithStock = new ArrayList<>();
    List<Item> favoriteItems = itemComponent.findItemsByUserId(userId);
    for (Item it : items) {
      if (it.getDiscountPercentage() != 0) {
        itemsWithStock.add(it);
      }
    }
    List<ItemDto> itemDtoWithStock =
        itemsWithStock.stream().map(itemMapper::mapItemToItemDto).toList();
    for (Item it : favoriteItems) {
      itemDtoWithStock.get(it.getId()).setFavorite(true);
    }
    return itemDtoWithStock;
  }

  @Override
  public List<ItemFeedbackDto> getFeedbacks(int id) {
    return itemFeedbackService.getFeedbacks(id);
  }
}
