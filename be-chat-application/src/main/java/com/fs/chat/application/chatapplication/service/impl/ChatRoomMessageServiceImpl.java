package com.fs.chat.application.chatapplication.service.impl;

import com.datastax.oss.driver.api.core.cql.PagingState;
import com.fs.chat.application.chatapplication.models.ChatRoomMessage;
import com.fs.chat.application.chatapplication.models.enums.MessageType;
import com.fs.chat.application.chatapplication.repository.ChatRoomMessageRepository;
import com.fs.chat.application.chatapplication.service.ChatRoomMessageService;
import lombok.val;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ChatRoomMessageServiceImpl implements ChatRoomMessageService {

    private final ChatRoomMessageRepository chatRoomMessageRepository;

    public ChatRoomMessageServiceImpl(ChatRoomMessageRepository chatRoomMessageRepository){
        this.chatRoomMessageRepository = chatRoomMessageRepository;
    }

    @Override
    public ChatRoomMessage saveChatRoomMessage(String userId, String senderName, String messageBody, String messageType) {
        return this.chatRoomMessageRepository.save(
                ChatRoomMessage.builder()
                        .chatRoomId(1L)
                        .messageContent(messageBody)
                        .messageId(UUID.randomUUID().toString())
                        .messageType(MessageType.valueOf(messageType))
                        .dateCreated(LocalDateTime.now())
                        .userId(userId)
                        .userName(senderName)
                        .build());
    }
    @Override
    public Slice<ChatRoomMessage> getChatRoomMessagePage(Integer pageSize,
                                                         Long chatRoomId,
                                                         @Nullable String pagingState) {
        val pageState = pagingState != null ? PagingState.fromString(pagingState).getRawPagingState() : null;
        CassandraPageRequest cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, pageSize),
                pageState);
        return chatRoomMessageRepository.findChatRoomMessagesByChatRoomId(chatRoomId, cassandraPageRequest);
    }

    @Override
    public List<ChatRoomMessage> getChatRoomMessagesCreatedBefore(LocalDateTime boundedDateTime, Long chatRoomId) {
        return chatRoomMessageRepository.findChatRoomMessagesByChatRoomIdAndDateCreatedLessThan(chatRoomId,
                boundedDateTime, CassandraPageRequest.of(0, 10)).getContent();
    }
}
