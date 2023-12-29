package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.UserComponent;
import kz.qBots.qSoft.data.dto.UserDto;
import kz.qBots.qSoft.data.entity.User;
import kz.qBots.qSoft.mapper.UserMapper;
import kz.qBots.qSoft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserComponent userComponent;
    private final UserMapper userMapper;
    @Override
    public UserDto getById(Integer id) {
        User user=userComponent.findById(id);
        return userMapper.mapUserToUserDto(user);
    }

}
