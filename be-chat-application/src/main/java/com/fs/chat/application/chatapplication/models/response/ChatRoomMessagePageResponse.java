package com.fs.chat.application.chatapplication.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRoomMessagePageResponse {
    private Integer page;
    private Integer pageSize;
    private List<SendMessageResponse> chatMessages;
    private Boolean hasNext;
    private String pagingState;
}
