package kz.qBots.qSoft.data.repository;

import kz.qBots.qSoft.data.entity.ItemFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemFeedbackRepository extends JpaRepository<ItemFeedback,Integer> {

}
