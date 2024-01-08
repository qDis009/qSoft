package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ItemComponent;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemFeedbackServiceImpl implements ItemFeedbackService {
    private final ItemFeedbackComponent itemFeedbackComponent;
    private final ItemFeedbackMapper itemFeedbackMapper;
    private final ItemComponent itemComponent;
    @Transactional
    @Override
    public ItemFeedbackDto create(ItemFeedbackRequest itemFeedbackRequest) {
        ItemFeedback itemFeedback=itemFeedbackMapper.mapItemFeedbackRequestToItemFeedback(itemFeedbackRequest);
        updateItemGrade(itemFeedback.getItem(),itemFeedback.getGrade());
        return itemFeedbackMapper.mapItemFeedbackToItemFeedbackDto(itemFeedbackComponent.create(itemFeedback));
    }
    private void updateItemGrade(Item item,double grade){
        int itemGradeCount=item.getItemFeedbacks().size();
        double totalGrade=item.getGrade()*itemGradeCount+grade;
        itemGradeCount++;
        item.setGrade(totalGrade/itemGradeCount);
        itemComponent.update(item);
    }

    @Override
    public List<ItemFeedbackDto> getFeedbacks(int id) {
        Item item=itemComponent.findById(id);
        return item.getItemFeedbacks().stream().map(itemFeedbackMapper::mapItemFeedbackToItemFeedbackDto).toList();
    }
    @Transactional
    @Override
    public void delete(int id) {
        //TODO
    }
}
