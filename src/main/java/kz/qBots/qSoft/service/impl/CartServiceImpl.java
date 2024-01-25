package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.CartComponent;
import kz.qBots.qSoft.data.component.ItemComponent;
import kz.qBots.qSoft.data.component.UserComponent;
import kz.qBots.qSoft.data.dto.CartDto;
import kz.qBots.qSoft.data.entity.Cart;
import kz.qBots.qSoft.data.entity.Item;
import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.mapper.CartMapper;
import kz.qBots.qSoft.rest.request.CartRequest;
import kz.qBots.qSoft.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
  private final CartComponent cartComponent;
  private final CartMapper cartMapper;
  private final UserComponent userComponent;
  private final ItemComponent itemComponent;

  @Override
  public CartDto create(CartRequest model) {
    Cart cart = new Cart();
    createCart(cart, model);
    return cartMapper.mapCartToCartDto(cartComponent.create(cart));
  }

  private void createCart(Cart cart, CartRequest model) {
    User user = userComponent.findById(model.getUserId());
    Item item = itemComponent.findById(model.getItemId());
    cart.setUser(user);
    cart.setItem(item);
    cart.setItemCount(model.getItemCount());
  }

  @Override
  public void delete(int id) {
    cartComponent.delete(id);
  }

  @Override
  public CartDto reduceCount(int id) {
    Cart cart = cartComponent.findById(id);
    int itemCount = cart.getItemCount();
    itemCount--;
    cart.setItemCount(itemCount);
    cartComponent.update(cart);
    return cartMapper.mapCartToCartDto(cart);
  }

  @Override
  public CartDto increaseCount(int id) {
    Cart cart = cartComponent.findById(id);
    int itemCount = cart.getItemCount();
    itemCount++;
    cart.setItemCount(itemCount);
    cartComponent.update(cart);
    return cartMapper.mapCartToCartDto(cart);
  }
}
