package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {
    void delete(int id);
    Page<ItemDto> findAll(Pageable pageable);
}
