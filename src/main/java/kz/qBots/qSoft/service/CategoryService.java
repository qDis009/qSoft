package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
  Page<CategoryDto> findAll(Pageable pageable);

  CategoryDto findById(int id);
  void setEnable(boolean enable,int id);
}
