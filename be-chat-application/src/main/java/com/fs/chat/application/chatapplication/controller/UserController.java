package com.fs.chat.application.chatapplication.controller;

import com.fs.chat.application.chatapplication.mapper.UserMapper;
import com.fs.chat.application.chatapplication.models.request.CreateUserRequest;
import com.fs.chat.application.chatapplication.models.response.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/chat-room/users")
public class UserController {

    private final UserMapper userMapper;

    public UserController(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @PostMapping
    UserResponse saveUser(@RequestBody CreateUserRequest createUserRequest){
        return this.userMapper.saveUser(createUserRequest);
    }


}
