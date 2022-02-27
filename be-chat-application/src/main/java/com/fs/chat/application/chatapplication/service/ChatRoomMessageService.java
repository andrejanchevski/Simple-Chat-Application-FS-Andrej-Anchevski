package com.fs.chat.application.chatapplication.service;

import com.fs.chat.application.chatapplication.models.ChatRoomMessage;

public interface ChatRoomMessageService {
    ChatRoomMessage saveChatRoomMessage(String userId,
                                        String senderName,
                                        String messageBody,
                                        String messageType);
}
