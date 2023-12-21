package kz.qBots.qSoft.data.component.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.qBots.qSoft.data.component.CategoryComponent;
import kz.qBots.qSoft.data.entity.Category;
import kz.qBots.qSoft.data.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryComponentImpl implements CategoryComponent {
    private final CategoryRepository categoryRepository;
    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
