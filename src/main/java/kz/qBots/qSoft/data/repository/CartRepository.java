package kz.qBots.qSoft.data.repository;

import kz.qBots.qSoft.data.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
  @Query("select cart from Cart cart where cart.enabled=true and cart.user.id=:userId")
  List<Cart> findCartsByUserId(@Param("userId") Integer userId);
}
