package kz.qBots.qSoft.data.component.impl;

import kz.qBots.qSoft.data.component.ShopFeedbackComponent;
import kz.qBots.qSoft.data.entity.ShopFeedback;
import kz.qBots.qSoft.data.repository.ShopFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopFeedbackComponentImpl implements ShopFeedbackComponent {
    private final ShopFeedbackRepository shopFeedbackRepository;

    @Override
    public ShopFeedback create(ShopFeedback shopFeedback) {
        return shopFeedbackRepository.save(shopFeedback);
    }
}
