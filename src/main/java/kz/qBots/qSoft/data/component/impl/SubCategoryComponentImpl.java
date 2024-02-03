package kz.qBots.qSoft.data.component.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.qBots.qSoft.data.component.SubCategoryComponent;
import kz.qBots.qSoft.data.entity.SubCategory;
import kz.qBots.qSoft.data.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubCategoryComponentImpl implements SubCategoryComponent {
  private final SubCategoryRepository subCategoryRepository;

  @Override
  public SubCategory findById(int id) {
    return subCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public void setEnable(boolean enable, int id) {
    subCategoryRepository.setEnable(enable, id);
  }
}
