package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ItemComponent;
import kz.qBots.qSoft.data.component.ItemFeedbackComponent;
import kz.qBots.qSoft.data.component.UserComponent;
import kz.qBots.qSoft.data.dto.ItemFeedbackDto;
import kz.qBots.qSoft.data.entity.Item;
import kz.qBots.qSoft.data.entity.ItemFeedback;
import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.mapper.ItemFeedbackMapper;
import kz.qBots.qSoft.rest.request.ItemFeedbackRequest;
import kz.qBots.qSoft.service.ItemFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ItemFeedbackServiceImpl implements ItemFeedbackService {
  private final ItemFeedbackComponent itemFeedbackComponent;
  private final ItemFeedbackMapper itemFeedbackMapper;
  private final ItemComponent itemComponent;
  private final UserComponent userComponent;

  @Transactional
  @Override
  public ItemFeedbackDto create(ItemFeedbackRequest itemFeedbackRequest) {
    ItemFeedback itemFeedback =
        itemFeedbackMapper.mapItemFeedbackRequestToItemFeedback(itemFeedbackRequest);
    if (isFeedbackWithComment(itemFeedback)) {
      updateFeedbackGradeAfterCreate(itemFeedback);
    } else {
      updateGradeAfterCreate(itemFeedback);
    }
    return itemFeedbackMapper.mapItemFeedbackToItemFeedbackDto(
        itemFeedbackComponent.create(itemFeedback));
  }

  private void updateGradeAfterCreate(ItemFeedback itemFeedback) {
    Item item = itemComponent.findById(itemFeedback.getItem().getId());
    int gradeCount = item.getGradeCount();
    double grade = itemFeedback.getGrade();
    double totalGrade=grade+gradeCount*item.getGrade();
    gradeCount++;
    item.setGradeCount(gradeCount);
    item.setGrade(totalGrade/gradeCount);
    itemComponent.update(item);
  }

  private void updateFeedbackGradeAfterCreate(ItemFeedback itemFeedback) {
    Item item = itemComponent.findById(itemFeedback.getItem().getId());
    int feedbackGradeCount = item.getFeedbackGradeCount();
    double feedbackGrade = itemFeedback.getGrade();
    double totalFeedbackGrade = feedbackGrade + feedbackGradeCount * item.getFeedbackGrade();
    feedbackGradeCount++;
    item.setFeedbackGradeCount(feedbackGradeCount);
    item.setFeedbackGrade(totalFeedbackGrade / feedbackGradeCount);
    itemComponent.update(item);
  }

  private void updateGradeAfterDelete(ItemFeedback itemFeedback) {
    Item item=itemComponent.findById(itemFeedback.getItem().getId());
    int gradeCount=item.getGradeCount();
    if(gradeCount==1){
      item.setGrade(0);
      item.setGradeCount(0);
    }else{
      double grade=itemFeedback.getGrade();
      double totalGrade=item.getGrade()*gradeCount-grade;
      gradeCount--;
      item.setGradeCount(gradeCount);
      item.setGrade(totalGrade/gradeCount);
    }
    itemComponent.update(item);
  }

  private void updateFeedbackGradeAfterDelete(ItemFeedback itemFeedback) {
    Item item=itemComponent.findById(itemFeedback.getItem().getId());
    int feedbackGradeCount=item.getFeedbackGradeCount();
    if(feedbackGradeCount==1){
      item.setFeedbackGradeCount(0);
      item.setFeedbackGrade(0);
    }else{
      double feedbackGrade=itemFeedback.getGrade();
      double totalFeedbackGrade=item.getFeedbackGrade()*feedbackGradeCount-feedbackGrade;
      feedbackGradeCount--;
      item.setFeedbackGradeCount(feedbackGradeCount);
      item.setFeedbackGrade(totalFeedbackGrade/feedbackGradeCount);
    }
    itemComponent.update(item);
  }

  @Override
  public List<ItemFeedbackDto> getFeedbacks(int id) {
    Item item = itemComponent.findById(id);
    return item.getItemFeedbacks().stream()
        .map(itemFeedbackMapper::mapItemFeedbackToItemFeedbackDto)
        .toList();
  }

  @Transactional
  @Override
  public void delete(int id) {
    ItemFeedback itemFeedback = itemFeedbackComponent.findById(id);
    if (isFeedbackWithComment(itemFeedback)) {
      updateFeedbackGradeAfterDelete(itemFeedback);
    } else {
      updateGradeAfterDelete(itemFeedback);
    }
    itemFeedbackComponent.delete(itemFeedback);
  }

  private boolean isFeedbackWithComment(ItemFeedback itemFeedback) {
    return itemFeedback.getComment() != null;
  }

  @Override
  public boolean hasComment(int userId, int itemId) {
    boolean hasComment=false;
    User user=userComponent.findById(userId);
    Set<ItemFeedback> itemFeedbacks=user.getItemFeedbacks();
    for(ItemFeedback itemFeedback:itemFeedbacks){
      if(itemFeedback.getItem().getId()==itemId){
        hasComment=true;
        break;
      }
    }
    return hasComment;
  }
}
