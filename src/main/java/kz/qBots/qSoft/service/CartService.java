package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.CartDto;
import kz.qBots.qSoft.rest.request.CartRequest;

public interface CartService {
    CartDto create(CartRequest model);
    void delete(int id);
    CartDto reduceCount(int id);
    CartDto increaseCount(int id);
}
