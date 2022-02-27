package com.fs.chat.application.chatapplication.mapper.impl;

import com.fs.chat.application.chatapplication.mapper.UserMapper;
import com.fs.chat.application.chatapplication.models.User;
import com.fs.chat.application.chatapplication.models.request.CreateUserRequest;
import com.fs.chat.application.chatapplication.models.response.UserResponse;
import com.fs.chat.application.chatapplication.service.UserService;
import org.springframework.stereotype.Component;


@Component
public class UserMapperImpl implements UserMapper {

    private final UserService userService;

    public UserMapperImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserResponse saveUser(CreateUserRequest createUserRequest) {
        User user = this.userService.saveUser(createUserRequest.userName());
        return UserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .build();
    }
}
