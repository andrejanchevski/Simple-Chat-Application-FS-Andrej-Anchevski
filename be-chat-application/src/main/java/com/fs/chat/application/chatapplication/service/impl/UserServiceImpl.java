package com.fs.chat.application.chatapplication.service.impl;

import com.fs.chat.application.chatapplication.models.User;
import com.fs.chat.application.chatapplication.repository.UserRepository;
import com.fs.chat.application.chatapplication.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(String userName) {
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        if(optionalUser.isEmpty()){
            return userRepository.save(
                    User.builder()
                            .userId(UUID.randomUUID().toString())
                            .userName(userName)
                            .email("")
                            .firstName("")
                            .lastName("")
                            .dateCreated(LocalDateTime.now())
                            .build()
            );
        }else{
            return optionalUser.get();
        }
    }
}
