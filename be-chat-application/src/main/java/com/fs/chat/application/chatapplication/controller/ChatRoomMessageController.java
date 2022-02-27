package com.fs.chat.application.chatapplication.controller;

import com.fs.chat.application.chatapplication.mapper.ChatRoomMessageMapper;
import com.fs.chat.application.chatapplication.models.response.ChatRoomMessagePageResponse;
import com.fs.chat.application.chatapplication.models.response.SendMessageResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/app/chat-room/chat-messages")
public class ChatRoomMessageController {

    private final ChatRoomMessageMapper chatRoomMessageMapper;

    ChatRoomMessageController(ChatRoomMessageMapper chatRoomMessageMapper){
        this.chatRoomMessageMapper = chatRoomMessageMapper;
    }

    @GetMapping()
    ChatRoomMessagePageResponse fetchMessagesPageable(@RequestParam(name = "pageSize") Integer pageSize,
                                                      @RequestParam(name = "chatRoomId") Long chatRoomId,
                                                      @RequestParam(name = "pagingState", required = false) String pagingState){
        return this.chatRoomMessageMapper.fetchMessagesPageable(pageSize, chatRoomId, Optional.ofNullable(pagingState));
    }

    @GetMapping("/archived")
    List<SendMessageResponse> fetchChatRoomMessagesBeforeDate(@RequestParam(name = "beforeDate")
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                              LocalDateTime beforeDate,
                                                              @RequestParam(name = "chatRoomId") Long chatRoomId){
        return chatRoomMessageMapper.fetchChatRoomMessagesBefore(chatRoomId, beforeDate);
    }
}
