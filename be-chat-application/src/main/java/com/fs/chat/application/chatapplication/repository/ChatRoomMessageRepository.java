package com.fs.chat.application.chatapplication.repository;

import com.fs.chat.application.chatapplication.models.ChatRoom;
import com.fs.chat.application.chatapplication.models.ChatRoomMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;

public interface ChatRoomMessageRepository extends PagingAndSortingRepository<ChatRoomMessage, Long> {
    Page<ChatRoomMessage> findAllByChatRoomAndDateCreatedLessThan(ChatRoom chatRoom,
                                                                  LocalDateTime borderDate,
                                                                  Pageable pageable);
}
