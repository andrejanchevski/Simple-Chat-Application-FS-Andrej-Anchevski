package com.fs.chat.application.chatapplication.repository;


import com.fs.chat.application.chatapplication.models.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CassandraRepository<User, String> {
    Optional<User> findByUserName(String userName);
}
