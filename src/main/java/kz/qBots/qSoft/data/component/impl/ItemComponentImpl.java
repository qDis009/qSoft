package kz.qBots.qSoft.data.component.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.qBots.qSoft.data.component.ItemComponent;
import kz.qBots.qSoft.data.entity.Item;
import kz.qBots.qSoft.data.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
  public Set<Integer> findIdsByUserId(int userId) {
    List<Item> items = itemRepository.findItemsByUserId(userId);
    Set<Integer> ids = new HashSet<>();
    items.forEach(
        it -> {
          ids.add(it.getId());
        });
    return ids;
  }

  @Override
  public List<Item> findRetailItemsOrderBySoldCount() {
    return itemRepository.findRetailOrderBySoldCount();
  }

  @Override
  public List<Item> findWholesaleItemsOrderBySoldCount() {
    return itemRepository.findWholesaleOrderBySoldCount();
  }

  @Override
  public List<Item> findRetailItemsWithDiscountPercentageExist() {
    return itemRepository.findRetailItemsWithDiscountPercentageIsExist();
  }

  @Override
  public void setEnable(boolean enable, int id) {
    itemRepository.setEnable(enable, id);
  }
}
