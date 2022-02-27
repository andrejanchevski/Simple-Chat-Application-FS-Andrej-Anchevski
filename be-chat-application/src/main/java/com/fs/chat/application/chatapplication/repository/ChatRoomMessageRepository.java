package com.fs.chat.application.chatapplication.repository;

import com.fs.chat.application.chatapplication.models.ChatRoomMessage;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ChatRoomMessageRepository extends CassandraRepository<ChatRoomMessage, Long> {
}
