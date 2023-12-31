package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {
    void delete(int id);
    Page<ItemDto> findAll(Pageable pageable);
    List<ItemDto> findItemsByUserId(int userId);
    ItemDto findById(int id);
}
