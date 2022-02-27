package com.fs.chat.application.chatapplication.service.impl;

import com.fs.chat.application.chatapplication.models.ChatRoomMessage;
import com.fs.chat.application.chatapplication.models.enums.MessageType;
import com.fs.chat.application.chatapplication.repository.ChatRoomMessageRepository;
import com.fs.chat.application.chatapplication.service.ChatRoomMessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ChatRoomMessageServiceImpl implements ChatRoomMessageService {

    private final ChatRoomMessageRepository chatRoomMessageRepository;

    public ChatRoomMessageServiceImpl(ChatRoomMessageRepository chatRoomMessageRepository){
        this.chatRoomMessageRepository = chatRoomMessageRepository;
    }

    @Override
    public ChatRoomMessage saveChatRoomMessage(String userId, String senderName, String messageBody, String messageType) {
        return this.chatRoomMessageRepository.save(
                ChatRoomMessage.builder()
                        .chatRoomId(1L)
                        .messageContent(messageBody)
                        .messageId(UUID.randomUUID().toString())
                        .messageType(MessageType.valueOf(messageType))
                        .dateCreated(LocalDateTime.now())
                        .userId(userId)
                        .userName(senderName)
                        .build());
    }
}
