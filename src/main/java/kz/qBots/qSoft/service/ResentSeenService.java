package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.ItemDto;

import java.util.List;

public interface ResentSeenService {
    List<ItemDto> getResentSeenByUserId(int userId);
    void addResentSeen(int userId,int itemId);
}
