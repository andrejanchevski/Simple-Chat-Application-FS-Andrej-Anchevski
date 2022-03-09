package com.fs.chat.application.chatapplication.mapper;

import com.fs.chat.application.chatapplication.models.request.ChatMessageRequest;
import com.fs.chat.application.chatapplication.models.response.ChatRoomMessagePageResponse;
import com.fs.chat.application.chatapplication.models.response.ChatRoomMessageResponse;

import java.time.LocalDateTime;

public interface ChatRoomMessageMapper {
    ChatRoomMessageResponse sendAndSaveChatRoomMessage(ChatMessageRequest chatMessageRequest);
    ChatRoomMessagePageResponse getChatRoomMessagesByPage(Integer pageSize, Integer page, Long chatRoomId, LocalDateTime bounedDate);
}
