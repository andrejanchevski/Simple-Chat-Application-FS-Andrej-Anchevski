package com.fs.chat.application.chatapplication.models.request;

public record ChatMessageRequest(
        String senderName,
        Long userId,
        String messageBody,
        String messageType,
        Long chatRoomId
){

}
