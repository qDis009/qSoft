package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ItemComponent;
import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.entity.Item;
import kz.qBots.qSoft.mapper.ItemMapper;
import kz.qBots.qSoft.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
  private final ItemComponent itemComponent;
  private final ItemMapper itemMapper;

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
}
