package kz.qBots.qSoft.data.component.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.qBots.qSoft.data.component.ItemFeedbackComponent;
import kz.qBots.qSoft.data.entity.ItemFeedback;
import kz.qBots.qSoft.data.repository.ItemFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemFeedbackComponentImpl implements ItemFeedbackComponent {
    private final ItemFeedbackRepository itemFeedbackRepository;

    @Override
    public ItemFeedback create(ItemFeedback itemFeedback) {
        return itemFeedbackRepository.save(itemFeedback);
    }

    @Override
    public ItemFeedback findById(int id) {
        return itemFeedbackRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void delete(ItemFeedback itemFeedback) {
        itemFeedbackRepository.delete(itemFeedback);
    }
}
