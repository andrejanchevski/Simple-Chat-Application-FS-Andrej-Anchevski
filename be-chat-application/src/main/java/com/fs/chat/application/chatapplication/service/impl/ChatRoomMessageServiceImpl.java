package com.fs.chat.application.chatapplication.service.impl;

import com.fs.chat.application.chatapplication.exceptions.ResourceNotFoundException;
import com.fs.chat.application.chatapplication.models.ChatRoom;
import com.fs.chat.application.chatapplication.models.ChatRoomMessage;
import com.fs.chat.application.chatapplication.models.User;
import com.fs.chat.application.chatapplication.models.enums.MessageType;
import com.fs.chat.application.chatapplication.repository.ChatRoomMessageRepository;
import com.fs.chat.application.chatapplication.repository.ChatRoomRepository;
import com.fs.chat.application.chatapplication.repository.UserRepository;
import com.fs.chat.application.chatapplication.service.ChatRoomMessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatRoomMessageServiceImpl implements ChatRoomMessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMessageRepository chatRoomMessageRepository;
    private final UserRepository userRepository;

    public ChatRoomMessageServiceImpl(ChatRoomRepository chatRoomRepository,
                                      ChatRoomMessageRepository chatRoomMessageRepository,
                                      UserRepository userRepository) {
        this.chatRoomMessageRepository = chatRoomMessageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Page<ChatRoomMessage> findLatestChatMessages(Long chatRoomId, LocalDateTime boundedDate,
                                                        Integer pageSize, Integer page) {
        Pageable sortedByDateCreated = PageRequest.of(page, pageSize, Sort.by("dateCreated").descending());
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ResourceNotFoundException(chatRoomId, ChatRoom.class));
        return chatRoomMessageRepository.findAllByChatRoomAndDateCreatedLessThan(chatRoom, boundedDate, sortedByDateCreated);
    }

    @Override
    public ChatRoomMessage saveChatRoomMessage(Long chatRoomId,
                                               Long userId, String userName, String messageBody, String messageType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(userId, User.class));

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ResourceNotFoundException(chatRoomId, ChatRoom.class));


        return this.chatRoomMessageRepository.save(
                ChatRoomMessage.builder()
                        .chatRoom(chatRoom)
                        .user(user)
                        .messageType(MessageType.valueOf(messageType))
                        .messageBody(messageBody)
                        .dateCreated(LocalDateTime.now())
                        .build()
        );
    }
}
