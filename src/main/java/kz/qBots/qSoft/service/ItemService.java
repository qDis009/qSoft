package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.ItemFeedbackDto;
import kz.qBots.qSoft.data.entity.Item;
import kz.qBots.qSoft.rest.request.ItemRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
  void delete(int id);
  ItemDto create(ItemRequest itemRequest, List<MultipartFile> multipartFiles);
  Page<ItemDto> findAll(Pageable pageable);

  List<ItemDto> findAll(int userId,String clientType);

  List<ItemDto> findItemsByUserId(int userId);

  ItemDto findById(int id);
  List<ItemDto> findRetailHit(int userId);
  List<ItemDto> findWholesaleHit(int userId);

  List<ItemDto> getStocks(int userId);

  List<ItemFeedbackDto> getFeedbacks(int id);
  void setEnable(boolean enable,int id);
}
