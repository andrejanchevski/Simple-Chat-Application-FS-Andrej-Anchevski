package com.fs.chat.application.chatapplication.mapper;

import com.fs.chat.application.chatapplication.models.request.CreateUserRequest;
import com.fs.chat.application.chatapplication.models.response.UserResponse;

public interface UserMapper {
    UserResponse saveUser(CreateUserRequest createUserRequest);
}
