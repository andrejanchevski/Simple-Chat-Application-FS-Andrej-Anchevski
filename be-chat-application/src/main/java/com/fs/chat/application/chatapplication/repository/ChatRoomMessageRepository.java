package com.fs.chat.application.chatapplication.repository;

import com.fs.chat.application.chatapplication.models.ChatRoomMessage;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatRoomMessageRepository extends CassandraRepository<ChatRoomMessage, Long> {
    Slice<ChatRoomMessage> findChatRoomMessagesByChatRoomId(Long chatRoomId,
                                                            CassandraPageRequest cassandraPageRequest);
    Slice<ChatRoomMessage> findChatRoomMessagesByChatRoomIdAndDateCreatedLessThan(Long chatRoomId,
                                                                                 LocalDateTime dateCreated,
                                                                                  CassandraPageRequest cassandraPageRequest);
}
