package com.fs.chat.application.chatapplication.service;

import com.fs.chat.application.chatapplication.models.ChatRoomMessage;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface ChatRoomMessageService {
    Page<ChatRoomMessage> findLatestChatMessages(Long chatRoomId, LocalDateTime boundedDate, Integer pageSize, Integer page);

    ChatRoomMessage saveChatRoomMessage(Long chatRoomId, Long userId, String userName, String messageBody,
                                        String messageType);
}
