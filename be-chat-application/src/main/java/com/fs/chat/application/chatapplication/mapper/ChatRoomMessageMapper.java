package com.fs.chat.application.chatapplication.mapper;

import com.fs.chat.application.chatapplication.models.request.SendMessageRequest;
import com.fs.chat.application.chatapplication.models.response.ChatRoomMessagePageResponse;
import com.fs.chat.application.chatapplication.models.response.SendMessageResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChatRoomMessageMapper {
    SendMessageResponse sendAndSaveChatRoomMessage(SendMessageRequest sendMessageRequest);
    ChatRoomMessagePageResponse fetchMessagesPageable(Integer pageSize, Long chatRoomId, Optional<String> pagingState);
    List<SendMessageResponse> fetchChatRoomMessagesBefore(Long chatRoomId, LocalDateTime beforeDate);
}
