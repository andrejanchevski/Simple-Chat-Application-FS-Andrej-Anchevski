package com.fs.chat.application.chatapplication.service.impl;

import com.fs.chat.application.chatapplication.models.User;
import com.fs.chat.application.chatapplication.repository.UserRepository;
import com.fs.chat.application.chatapplication.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(String userName) {
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        if(optionalUser.isEmpty()){
            return userRepository.save(
                    User.builder()
                            .userName(userName)
                            .dateCreated(LocalDateTime.now())
                            .build()
            );
        }else{
            return optionalUser.get();
        }
    }
}
