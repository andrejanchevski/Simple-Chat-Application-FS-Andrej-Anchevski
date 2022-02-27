package com.fs.chat.application.chatapplication.models.request;

public record SendMessageRequest(
        String senderName,
        String userId,
        String messageBody,
        String messageType
){

}
