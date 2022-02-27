package com.fs.chat.application.chatapplication.repository;


import com.fs.chat.application.chatapplication.models.User;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface UserRepository extends CassandraRepository<User, String> {
    Optional<User> findByUserName(String userName);
}
