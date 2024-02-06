package kz.qBots.qSoft.data.component;

import kz.qBots.qSoft.data.entity.Cart;

import java.util.List;

public interface CartComponent {
  Cart create(Cart cart);
  void delete(int id);
  Cart findById(int id);
  void update(Cart cart);
  List<Cart> findCartsByUserId(int userId);
}
