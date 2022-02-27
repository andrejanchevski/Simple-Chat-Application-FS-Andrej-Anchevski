package com.fs.chat.application.chatapplication.mapper.impl;

import com.fs.chat.application.chatapplication.mapper.ChatRoomMessageMapper;
import com.fs.chat.application.chatapplication.models.ChatRoomMessage;
import com.fs.chat.application.chatapplication.models.request.SendMessageRequest;
import com.fs.chat.application.chatapplication.models.response.SendMessageResponse;
import com.fs.chat.application.chatapplication.service.ChatRoomMessageService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ChatRoomMessageMapperImpl implements ChatRoomMessageMapper {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRoomMessageService chatRoomMessageService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    public ChatRoomMessageMapperImpl(SimpMessagingTemplate simpMessagingTemplate,
                                     ChatRoomMessageService chatRoomMessageService){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatRoomMessageService = chatRoomMessageService;
    }

    @Override
    public SendMessageResponse sendAndSaveChatRoomMessage(SendMessageRequest sendMessageRequest) {
        ChatRoomMessage chatRoomMessage = this.chatRoomMessageService
                .saveChatRoomMessage(sendMessageRequest.userId(),
                        sendMessageRequest.senderName(),
                        sendMessageRequest.messageBody(),
                        sendMessageRequest.messageType());
        SendMessageResponse sendMessageResponse =
                SendMessageResponse.builder()
                        .messageBody(chatRoomMessage.getMessageContent())
                        .messageType(chatRoomMessage.getMessageType().name())
                        .senderId(chatRoomMessage.getUserId())
                        .senderName(chatRoomMessage.getUserName())
                        .dateCreated(chatRoomMessage.getDateCreated().format(formatter))
                        .build();
        this.simpMessagingTemplate.convertAndSend("/chatroom/public", sendMessageResponse);
        return sendMessageResponse;
    }
}
