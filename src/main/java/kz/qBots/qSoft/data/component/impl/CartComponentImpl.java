package kz.qBots.qSoft.data.component.impl;

import kz.qBots.qSoft.data.component.CartComponent;
import kz.qBots.qSoft.data.entity.Cart;
import kz.qBots.qSoft.data.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartComponentImpl implements CartComponent {
  private final CartRepository cartRepository;

  @Override
  public Cart create(Cart cart) {
    return cartRepository.save(cart);
  }
}
