package kz.qBots.qSoft.data.repository;

import kz.qBots.qSoft.data.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Integer> {

}
