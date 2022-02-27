package com.fs.chat.application.chatapplication.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendMessageResponse{
    private String senderName;
    private String messageBody;
    private String messageType;
    private String dateCreated;
    private String senderId;
}
