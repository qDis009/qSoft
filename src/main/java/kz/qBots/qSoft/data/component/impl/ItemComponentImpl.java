package kz.qBots.qSoft.data.component.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.qBots.qSoft.data.component.ItemComponent;
import kz.qBots.qSoft.data.entity.Item;
import kz.qBots.qSoft.data.enums.ItemType;
import kz.qBots.qSoft.data.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemComponentImpl implements ItemComponent {
  private final ItemRepository itemRepository;

  @Override
  public Item create(Item item) {
    return itemRepository.save(item);
  }

  @Override
  public List<Item> findAllByIds(List<Integer> ids) {
    return itemRepository.findAllByIdIn(ids);
  }

  @Override
  public Item findById(int id) {
    return itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public void update(Item item) {
    itemRepository.save(item);
  }

  @Override
  public Page<Item> findAll(Pageable pageable) {
    return itemRepository.findAll(pageable);
  }

  @Override
  public List<Item> findAll() {
    return itemRepository.findAll();
  }

  @Override
  public List<Item> findItemsByUserId(int userId) {
    return itemRepository.findItemsByUserId(userId);
  }

  @Override
  public Page<Item> findItemsByItemType(ItemType itemType, Pageable pageable) {
    return itemRepository.findByItemTypeOrderBySoldCountDesc(itemType,pageable);
  }

}
