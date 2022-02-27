package com.fs.chat.application.chatapplication.mapper.impl;

import com.fs.chat.application.chatapplication.mapper.ChatRoomMessageMapper;
import com.fs.chat.application.chatapplication.models.ChatRoomMessage;
import com.fs.chat.application.chatapplication.models.request.SendMessageRequest;
import com.fs.chat.application.chatapplication.models.response.ChatRoomMessagePageResponse;
import com.fs.chat.application.chatapplication.models.response.SendMessageResponse;
import com.fs.chat.application.chatapplication.service.ChatRoomMessageService;
import lombok.val;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ChatRoomMessageMapperImpl implements ChatRoomMessageMapper {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRoomMessageService chatRoomMessageService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    public ChatRoomMessageMapperImpl(SimpMessagingTemplate simpMessagingTemplate,
                                     ChatRoomMessageService chatRoomMessageService){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatRoomMessageService = chatRoomMessageService;
    }

    @Override
    public SendMessageResponse sendAndSaveChatRoomMessage(SendMessageRequest sendMessageRequest) {
        ChatRoomMessage chatRoomMessage = this.chatRoomMessageService
                .saveChatRoomMessage(sendMessageRequest.userId(),
                        sendMessageRequest.senderName(),
                        sendMessageRequest.messageBody(),
                        sendMessageRequest.messageType());
        SendMessageResponse sendMessageResponse =
                SendMessageResponse.builder()
                        .messageBody(chatRoomMessage.getMessageContent())
                        .messageType(chatRoomMessage.getMessageType().name())
                        .senderId(chatRoomMessage.getUserId())
                        .senderName(chatRoomMessage.getUserName())
                        .dateCreated(chatRoomMessage.getDateCreated().format(formatter))
                        .build();
        this.simpMessagingTemplate.convertAndSend("/chatroom/public", sendMessageResponse);
        return sendMessageResponse;
    }

    @Override
    public ChatRoomMessagePageResponse fetchMessagesPageable(Integer pageSize, Long chatRoomId, Optional<String> pagingState) {
        Slice<ChatRoomMessage> chatRoomMessageSlice =
                chatRoomMessageService.getChatRoomMessagePage(pageSize, chatRoomId, pagingState.orElse(null));

        ChatRoomMessagePageResponse chatRoomMessagePageResponse = ChatRoomMessagePageResponse.builder()
                .page(chatRoomMessageSlice.getNumber())
                .pageSize(chatRoomMessageSlice.getNumberOfElements())
                .chatMessages(chatRoomMessageSlice.get()
                        .map(chatRoomMessage -> SendMessageResponse.builder()
                                .messageBody(chatRoomMessage.getMessageContent())
                                .messageType(chatRoomMessage.getMessageType().name())
                                .senderName(chatRoomMessage.getUserName())
                                .senderId(chatRoomMessage.getUserId())
                                .dateCreated(chatRoomMessage.getDateCreated().format(formatter))
                                .build())
                        .collect(Collectors.toList()))
                .hasNext(chatRoomMessageSlice.hasNext())
                .pagingState(getPagingState(chatRoomMessageSlice))
                .build();


        return chatRoomMessagePageResponse;

    }

    @Override
    public List<SendMessageResponse> fetchChatRoomMessagesBefore(Long chatRoomId, LocalDateTime beforeDate) {
        return chatRoomMessageService.getChatRoomMessagesCreatedBefore(beforeDate, chatRoomId).stream()
                .map(chatRoomMessage -> SendMessageResponse.builder()
                        .messageBody(chatRoomMessage.getMessageContent())
                        .messageType(chatRoomMessage.getMessageType().name())
                        .senderName(chatRoomMessage.getUserName())
                        .senderId(chatRoomMessage.getUserId())
                        .dateCreated(chatRoomMessage.getDateCreated().format(formatter))
                        .build()).
                collect(Collectors.toList());
    }

    @Override
    public List<SendMessageResponse> fetchAllChatRoomMessages(Long chatRoomId) {
        return chatRoomMessageService.fetchAllChatRoomMessages(chatRoomId).stream()
                .map(chatRoomMessage -> SendMessageResponse.builder()
                        .messageBody(chatRoomMessage.getMessageContent())
                        .messageType(chatRoomMessage.getMessageType().name())
                        .senderName(chatRoomMessage.getUserName())
                        .senderId(chatRoomMessage.getUserId())
                        .dateCreated(chatRoomMessage.getDateCreated().format(formatter))
                        .build()).
                collect(Collectors.toList());
    }


    @Nullable
    private static <T> String getPagingState(final Slice<T> slice) {
        if (slice.hasNext()) {
            val pageRequest = (CassandraPageRequest) slice.nextPageable();
            return Objects.requireNonNull(pageRequest.getPagingState()).toString();
        } else {
            return null;
        }
    }
}
