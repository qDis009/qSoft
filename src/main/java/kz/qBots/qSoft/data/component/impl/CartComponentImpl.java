package kz.qBots.qSoft.data.component.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.qBots.qSoft.data.component.CartComponent;
import kz.qBots.qSoft.data.entity.Cart;
import kz.qBots.qSoft.data.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartComponentImpl implements CartComponent {
  private final CartRepository cartRepository;

  @Override
  public Cart create(Cart cart) {
    return cartRepository.save(cart);
  }

  @Override
  public void delete(int id) {
    cartRepository.deleteById(id);
  }

  @Override
  public void update(Cart cart) {
    cartRepository.save(cart);
  }

  @Override
  public Cart findById(int id) {
    return cartRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }
}
