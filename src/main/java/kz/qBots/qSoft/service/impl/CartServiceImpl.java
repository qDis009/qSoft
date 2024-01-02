package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.CartComponent;
import kz.qBots.qSoft.data.dto.CartDto;
import kz.qBots.qSoft.data.entity.Cart;
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
    @Override
    public CartDto create(CartRequest model) {
        Cart cart=cartMapper.mapCartRequestToCart(model);
        return cartMapper.mapCartToCartDto(cartComponent.create(cart));
    }

    @Override
    public void delete(int id) {
        cartComponent.delete(id);
    }

    @Override
    public CartDto reduceCount(int id) {
        Cart cart=cartComponent.findById(id);
        int itemCount=cart.getItemCount();
        itemCount++;
        cart.setItemCount(itemCount);
        cartComponent.update(cart);
        return cartMapper.mapCartToCartDto(cart);
    }

    @Override
    public CartDto increaseCount(int id) {
        Cart cart=cartComponent.findById(id);
        int itemCount=cart.getItemCount();
        itemCount--;
        cart.setItemCount(itemCount);
        cartComponent.update(cart);
        return cartMapper.mapCartToCartDto(cart);
    }
}
