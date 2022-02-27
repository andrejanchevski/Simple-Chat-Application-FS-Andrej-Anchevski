package com.fs.chat.application.chatapplication.service;

import com.fs.chat.application.chatapplication.models.ChatRoomMessage;
import org.springframework.data.domain.Slice;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

public interface ChatRoomMessageService {
    ChatRoomMessage saveChatRoomMessage(String userId,
                                        String senderName,
                                        String messageBody,
                                        String messageType);

    Slice<ChatRoomMessage> getChatRoomMessagePage(Integer pageSize,
                                                  Long chatRoomId,
                                                  @Nullable String pagingState);

    List<ChatRoomMessage> getChatRoomMessagesCreatedBefore(LocalDateTime boundedDateTime, Long chatRoomId);
    List<ChatRoomMessage> fetchAllChatRoomMessages(Long chatRoomId);
}
