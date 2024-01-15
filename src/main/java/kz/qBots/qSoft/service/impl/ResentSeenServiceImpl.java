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
  Map<Integer, Stack<Item>> resentSeen = new HashMap<>();

  @Override
  public List<ItemDto> getResentSeenByUserId(int userId) {
    Stack<Item> seen = resentSeen.get(userId);
    if (seen != null) {
      return seen.stream().map(itemMapper::mapItemToItemDto).toList();
    }
    return null;
  }

  @Override
  public void addResentSeen(int userId, int itemId) {
    Item item = itemComponent.findById(itemId);
    Stack<Item> seen = resentSeen.getOrDefault(userId,new Stack<>());
    if (seen.size() > 10) {
      seen.removeElementAt(0);
    }
    seen.push(item);
    resentSeen.put(userId,seen);
  }
}
