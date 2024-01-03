package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ItemFeedbackComponent;
import kz.qBots.qSoft.data.dto.ItemFeedbackDto;
import kz.qBots.qSoft.data.entity.Item;
import kz.qBots.qSoft.data.entity.ItemFeedback;
import kz.qBots.qSoft.mapper.ItemFeedbackMapper;
import kz.qBots.qSoft.rest.request.ItemFeedbackRequest;
import kz.qBots.qSoft.service.ItemFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemFeedbackServiceImpl implements ItemFeedbackService {
    private final ItemFeedbackComponent itemFeedbackComponent;
    private final ItemFeedbackMapper itemFeedbackMapper;
    @Transactional
    @Override
    public ItemFeedbackDto create(ItemFeedbackRequest itemFeedbackRequest) {
        ItemFeedback itemFeedback=itemFeedbackMapper.mapItemFeedbackRequestToItemFeedback(itemFeedbackRequest);
        updateItemGrade(itemFeedback.getItem(),itemFeedback.getGrade());
        return itemFeedbackMapper.mapItemFeedbackToItemFeedbackDto(itemFeedbackComponent.create(itemFeedback));
    }
    public void updateItemGrade(Item item,double grade){
        int itemGradeCount=item.getItemFeedbacks().size();
        double totalGrade=item.getGrade()*itemGradeCount+grade;
        itemGradeCount++;
        item.setGrade(totalGrade/itemGradeCount);
    }
}
