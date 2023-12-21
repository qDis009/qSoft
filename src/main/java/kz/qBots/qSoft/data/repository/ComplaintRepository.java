package kz.qBots.qSoft.data.repository;

import kz.qBots.qSoft.data.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

}
