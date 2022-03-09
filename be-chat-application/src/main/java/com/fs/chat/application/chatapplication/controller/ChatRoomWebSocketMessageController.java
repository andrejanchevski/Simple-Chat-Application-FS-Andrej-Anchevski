package com.fs.chat.application.chatapplication.controller;

import com.fs.chat.application.chatapplication.mapper.ChatRoomMessageMapper;
import com.fs.chat.application.chatapplication.models.request.ChatMessageRequest;
import com.fs.chat.application.chatapplication.models.response.ChatRoomMessageResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatRoomWebSocketMessageController {

    private final ChatRoomMessageMapper chatRoomMessageMapper;

    public ChatRoomWebSocketMessageController(ChatRoomMessageMapper chatRoomMessageMapper){
        this.chatRoomMessageMapper = chatRoomMessageMapper;
    }

    @MessageMapping("/message")
    public ChatRoomMessageResponse receivePublicMessage(@Payload ChatMessageRequest chatMessage){
        return chatRoomMessageMapper.sendAndSaveChatRoomMessage(chatMessage);
    }

}
