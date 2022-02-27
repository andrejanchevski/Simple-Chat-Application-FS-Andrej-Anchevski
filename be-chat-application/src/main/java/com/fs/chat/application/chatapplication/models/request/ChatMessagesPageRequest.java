package com.fs.chat.application.chatapplication.models.request;

public record ChatMessagesPageRequest(
        Long chatRoomId,
        Integer page,
        Integer pageSize
){

}
