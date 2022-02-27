package com.fs.chat.application.chatapplication.mapper;

import com.fs.chat.application.chatapplication.models.request.SendMessageRequest;
import com.fs.chat.application.chatapplication.models.response.SendMessageResponse;

public interface ChatRoomMessageMapper {
    SendMessageResponse sendAndSaveChatRoomMessage(SendMessageRequest sendMessageRequest);
}
