package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ItemComponent;
import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.entity.Item;
import kz.qBots.qSoft.mapper.ItemMapper;
import kz.qBots.qSoft.service.ResentSeenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ResentSeenServiceImpl implements ResentSeenService {
  private final ItemMapper itemMapper;
  private final ItemComponent itemComponent;
  Map<Integer, LinkedList<Item>> resentSeen = new HashMap<>();

  @Override
  public List<ItemDto> getResentSeenByUserId(int userId) {
    LinkedList<Item> seen = resentSeen.getOrDefault(userId, new LinkedList<>());
    Set<Integer> favorite = itemComponent.findIdsByUserId(userId);
    List<ItemDto> items = seen.stream().map(itemMapper::mapItemToItemDto).toList();
    items.forEach(
        it -> {
          if (favorite.contains(it.getId())) {
            it.setFavorite(true);
          }
        });
    return items;
  }

  @Override
  public void addResentSeen(int userId, int itemId) {
    Item item = itemComponent.findById(itemId);
    LinkedList<Item> seen = resentSeen.getOrDefault(userId, new LinkedList<>());
    seen.remove(item);
    if(seen.size()>=20){
      seen.removeFirst();
    }
    seen.add(item);
    resentSeen.put(userId, seen);
  }
}
