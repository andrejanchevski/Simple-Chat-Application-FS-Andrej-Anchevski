package com.fs.chat.application.chatapplication.mapper.impl;

import com.fs.chat.application.chatapplication.mapper.ChatRoomMessageMapper;
import com.fs.chat.application.chatapplication.models.ChatRoomMessage;
import com.fs.chat.application.chatapplication.models.request.ChatMessageRequest;
import com.fs.chat.application.chatapplication.models.response.ChatRoomMessagePageResponse;
import com.fs.chat.application.chatapplication.models.response.ChatRoomMessageResponse;
import com.fs.chat.application.chatapplication.service.ChatRoomMessageService;
import org.springframework.data.domain.Page;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class ChatRoomMessageMapperImpl implements ChatRoomMessageMapper {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRoomMessageService chatRoomMessageService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    public ChatRoomMessageMapperImpl(SimpMessagingTemplate simpMessagingTemplate,
                                     ChatRoomMessageService chatRoomMessageService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatRoomMessageService = chatRoomMessageService;
    }

    @Override
    public ChatRoomMessageResponse sendAndSaveChatRoomMessage(ChatMessageRequest chatMessageRequest) {
        ChatRoomMessage chatRoomMessage = this.chatRoomMessageService
                .saveChatRoomMessage(chatMessageRequest.chatRoomId(),
                        chatMessageRequest.userId(),
                        chatMessageRequest.senderName(),
                        chatMessageRequest.messageBody(),
                        chatMessageRequest.messageType());
        ChatRoomMessageResponse chatMessageResponse =
                ChatRoomMessageResponse.builder()
                        .messageBody(chatRoomMessage.getMessageBody())
                        .messageType(chatRoomMessage.getMessageType().name())
                        .senderId(chatRoomMessage.getUser().getUserId())
                        .senderName(chatRoomMessage.getUser().getUserName())
                        .dateCreated(chatRoomMessage.getDateCreated().format(formatter))
                        .build();
        this.simpMessagingTemplate.convertAndSend("/chatroom/public", chatMessageResponse);
        return chatMessageResponse;
    }

    @Override
    public ChatRoomMessagePageResponse getChatRoomMessagesByPage(Integer pageSize, Integer page,
                                                                 Long chatRoomId, LocalDateTime boundedDate) {
        Page<ChatRoomMessage> chatRoomMessagePage =
                chatRoomMessageService.findLatestChatMessages(chatRoomId, boundedDate,pageSize, page);
        return ChatRoomMessagePageResponse
                .builder()
                .pageSize(chatRoomMessagePage.getSize())
                .page(chatRoomMessagePage.getNumber())
                .chatMessages(chatRoomMessagePage.getContent().stream()
                        .sorted(Comparator.comparing(ChatRoomMessage::getDateCreated))
                        .map(chatRoomMessage ->
                                ChatRoomMessageResponse.builder()
                                        .messageBody(chatRoomMessage.getMessageBody())
                                        .messageType(chatRoomMessage.getMessageType().name())
                                        .senderId(chatRoomMessage.getUser().getUserId())
                                        .senderName(chatRoomMessage.getUser().getUserName())
                                        .dateCreated(chatRoomMessage.getDateCreated().format(formatter))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
