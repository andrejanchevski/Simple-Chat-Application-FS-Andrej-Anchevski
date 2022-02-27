package com.fs.chat.application.chatapplication.controller;

import com.fs.chat.application.chatapplication.mapper.ChatRoomMessageMapper;
import com.fs.chat.application.chatapplication.models.request.SendMessageRequest;
import com.fs.chat.application.chatapplication.models.response.SendMessageResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatRoomMessageController {

    private final ChatRoomMessageMapper chatRoomMessageMapper;

    public ChatRoomMessageController(ChatRoomMessageMapper chatRoomMessageMapper){
        this.chatRoomMessageMapper = chatRoomMessageMapper;
    }

    @MessageMapping("/message")
    public SendMessageResponse receivePublicMessage(@Payload SendMessageRequest chatMessage){
        return chatRoomMessageMapper.sendAndSaveChatRoomMessage(chatMessage);
    }

}
