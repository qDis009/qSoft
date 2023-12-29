package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.dto.UserDto;

public interface UserService {
    UserDto getById(Integer id);
}
