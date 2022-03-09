package com.fs.chat.application.chatapplication.controller;

import com.fs.chat.application.chatapplication.mapper.ChatRoomMessageMapper;
import com.fs.chat.application.chatapplication.models.response.ChatRoomMessagePageResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/app/chat-room/chat-messages")
public class ChatRoomMessageController {

    private final ChatRoomMessageMapper chatRoomMessageMapper;

    ChatRoomMessageController(ChatRoomMessageMapper chatRoomMessageMapper) {
        this.chatRoomMessageMapper = chatRoomMessageMapper;
    }

    @GetMapping("/paged")
    ChatRoomMessagePageResponse getChatMessagesByPage(@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                      @RequestParam(name = "chatRoomId") Long chatRoomId,
                                                      @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                      @RequestParam("boundedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                              LocalDateTime boundedDate) {
        return this.chatRoomMessageMapper.getChatRoomMessagesByPage(pageSize, page, chatRoomId, boundedDate);
    }

}
